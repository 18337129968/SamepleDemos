package com.hfxief.utils.fastjson;

import com.alibaba.fastjson.JSON;

/**
 * @title GsonUtils
 * @description 描述：
 * @date 2018/6/26
 * @auther xie
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
