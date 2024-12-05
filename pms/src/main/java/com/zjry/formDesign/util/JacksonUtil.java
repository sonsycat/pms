package com.zjry.formDesign.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author huawei
 * @description: json解析和生成的工具类
 */
public class JacksonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(JacksonUtil.class);

    static {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"));
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }


    public static ObjectMapper getInstance() {
        return mapper;
    }

    @Nullable
    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("json序列化出错：" + obj, e);
            return null;
        }
    }

    @Nullable
    public static <T> T toBean(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    /**
     * vova后台返回带size带中文的字符转义
     *
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    @Nullable
    public static <T> T toBeanWithCnChar(String json, Class<T> tClass) {
        try {
            json = json.replace("\\\"", "'");
            json = json.replace("\\", "");
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <T> T toBeanNoExce(String json, Class<T> tClass) throws IOException {
        return mapper.readValue(json, tClass);
    }

    @Nullable
    public static <E> List<E> toList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static Object toMap2(String json) {
        try {
            return mapper.readValue(json, Map.class);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static Map<String, Object> toMapStrObj(Object obj) {
        if (obj instanceof String) {
            return JacksonUtil.toMap(String.valueOf(obj), String.class, Object.class);
        } else {
            String json = JacksonUtil.toString(obj);
            return JacksonUtil.toMap(json, String.class, Object.class);
        }
    }

    @Nullable
    public static Map<String, String> toMapStr(Object obj) {
        if (obj instanceof String) {
            return JacksonUtil.toMap(String.valueOf(obj), String.class, String.class);
        } else {
            String json = JacksonUtil.toString(obj);
            return JacksonUtil.toMap(json, String.class, String.class);
        }
    }
}


