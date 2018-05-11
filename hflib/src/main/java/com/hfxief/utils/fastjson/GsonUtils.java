package com.hfxief.utils.fastjson;

import com.alibaba.fastjson.JSON;

/**
 * @Author mxl
 * @Date on 2017/5/29
 * @Description:
 */

public class GsonUtils {

    public static String toJSONString(Object obj) {
        String json = JSON.toJSONString(obj);
        return json;
    }

    public static Object getObject(String json, Class<?> cls) {
        Object o = JSON.parseObject(json, cls);
        return o;
    }
}
