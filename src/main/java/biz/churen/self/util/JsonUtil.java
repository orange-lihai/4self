package biz.churen.self.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class JsonUtil {
  public static ObjectMapper objectMapper = null;
  static {
    synchronized (JsonUtil.class) {
      if (objectMapper == null) {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
      }
    }
  }

  // basic util functions
  public static <S extends Map, T> T mapToObject(S source, Class<T> c) {
    if (Map.class.isAssignableFrom(c)) {
      return (T) source;
    } else {
      return JsonUtil.toObject(JsonUtil.toJSON(source), c);
    }
  }

  public static <S> Map objectToMap(S source) {
    if (Map.class.isAssignableFrom(source.getClass())) {
      return (Map) source;
    } else {
      return JsonUtil.toObject(JsonUtil.toJSON(source), HashMap.class);
    }
  }

  public static String toJSON(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T toObject(String json, Class<T> c) {
    try {
      return objectMapper.readValue(json, c);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T toObject(String json, TypeReference<T> type) {
    try {
      return objectMapper.readValue(json, type);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
