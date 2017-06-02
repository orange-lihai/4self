package biz.churen.self.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class DiscardHttpServer {
  @Autowired private Properties httpProps;
  private static boolean IS_RUNNING = false;
  private static int HTTP_PORT;

  private synchronized void beforeRun() throws Exception {
    if (null == httpProps) {
      throw new Exception("DiscardHttpServer is running on port: " + HTTP_PORT);
    }
    if (IS_RUNNING) {
      throw new Exception("DiscardHttpServer is running on port: " + HTTP_PORT);
    }
    HTTP_PORT = Integer.parseInt(httpProps.getProperty("http.port", "8080"));
    IS_RUNNING = true;
    System.out.println("DiscardHttpServer is running on port: " + HTTP_PORT);
  }

  public synchronized void run() throws Exception {
    beforeRun();

    EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap(); // (2)
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class) // (3)
          .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
            @Override public void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline().addLast(new HttpServerCodec());
              ch.pipeline().addLast(new HttpObjectAggregator(65536));
              ch.pipeline().addLast(new DiscardServerHandler());
            }
          })
          .option(ChannelOption.SO_BACKLOG, 128)          // (5)
          .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

      // Bind and start to accept incoming connections.
      ChannelFuture f = b.bind(HTTP_PORT).sync(); // (7)

      // Wait until the server socket is closed.
      // In this example, this does not happen, but you can do that to gracefully
      // shut down your server.
      f.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }
}
