package com.game.common.util;

import org.codehaus.jackson.map.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class JsonUtils {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static byte[] toNoCompressBytes(Object object){
        byte[] data = object2Bytes(object);
        ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
        try {
            out.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    private static byte[] object2Bytes(Object object) {
        try {
            return mapper.writeValueAsBytes(object);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static <M> M toObjectWithNoCompress(byte[] data, Class<M> clazz){
        if (data == null || data.length == 0){
            return null;
        }
        return bytes2Object(data, 1, data.length -1, clazz);
    }

    private static <M> M bytes2Object(byte[] content, int offset, int len, Class<M> clazz) {
        try {
            return mapper.readValue(content, offset, len, clazz);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
