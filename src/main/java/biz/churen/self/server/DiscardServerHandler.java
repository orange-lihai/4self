package biz.churen.self.server;

import biz.churen.self.SelfApplication;
import biz.churen.self.server.strategy.SelfMethod;
import biz.churen.self.util.SUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.ReferenceCountUtil;
import jdk.nashorn.internal.objects.NativeJSON;
import org.apache.commons.lang3.StringUtils;
import sun.net.util.URLUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by lihai5 on 2017/5/31.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
  private FullHttpRequest request;
  private SelfMethod selfMethod;
  private Object[] args;

  // extract params from request
  private Map<String, String> extractQueryParams2Map() throws Exception {
    Map<String, String> r = new HashMap<>();
    QueryStringDecoder decoderQuery = new QueryStringDecoder(request.uri());
    decoderQuery.parameters().entrySet().forEach(
        entry -> r.put(entry.getKey(), entry.getValue().get(0))
    );
    return r;
  }

  private String extractBodyParams2Json(FullHttpRequest req) throws UnsupportedEncodingException {
    ByteBuf buf = req.content();
    String bodyData = buf.toString(StandardCharsets.UTF_8);
    buf.release();
    return bodyData;
  }

  private Map<String, String> extractBodyParams2Map() throws IOException {
    Map<String, String> r = new HashMap<>();
    HttpMethod httpMethod = request.method();
    if (HttpMethod.POST == httpMethod) {
      HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
      decoder.offer(request);
      List<InterfaceHttpData> paramList = decoder.getBodyHttpDatas();
      for (InterfaceHttpData p : paramList) {
        if (p.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
          Attribute a = (Attribute) p;
          r.put(a.getName(), a.getValue());
        }
      }
    }
    return r;
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg)
      throws Exception {
    try {
      if (msg instanceof HttpRequest) {
        request = (FullHttpRequest) msg;
        String path = request.uri().split("\\?")[0];
        selfMethod = SelfApplication.getUrlHandler(path);
        if (null == selfMethod) {
          throw new Exception("no SelfMapping found for "+ path +"!");
        }

        Map<String, String> urlParams = extractQueryParams2Map();
        args = selfMethod.matchSelfParams(args, urlParams);
        HttpMethod httpMethod = request.method();
        if (httpMethod == HttpMethod.GET) {

        } else if (httpMethod == HttpMethod.POST) {
          CharSequence contentType = HttpUtil.getMimeType(request);
          switch (contentType.toString()) {
            case "application/json":
              args = selfMethod.matchSelfBody(args, extractBodyParams2Json(request));
              break;
            case "application/x-www-from-urlencoded":
              args = selfMethod.matchSelfBody(args, extractBodyParams2Map());
              break;
            case "application/octet-stream":
              break;
            default:
              args = selfMethod.matchSelfBody(args, extractBodyParams2Map());
              break;
          }

        } else {
          throw new Exception(HttpResponseStatus.METHOD_NOT_ALLOWED.toString());
        }
      }
      if (msg instanceof HttpContent) {
        Object rs = selfMethod.invoke(args);
        FullHttpResponse response = new DefaultFullHttpResponse(
            HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(SUtil.toJSON(rs).getBytes("UTF-8")));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        if (HttpUtil.isKeepAlive(request)) {
          HttpUtil.setKeepAlive(response, true);
        }
        ctx.write(response);
        ctx.flush();
      }
    } finally {
      ReferenceCountUtil.release(msg);
    }
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    ctx.close();
  }

}
