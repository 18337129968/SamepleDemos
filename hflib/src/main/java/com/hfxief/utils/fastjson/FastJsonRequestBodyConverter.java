package com.hfxief.utils.fastjson;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/** 
*@Title: FastJsonRequestBodyConverter
*@Description:  描述
*@date 2016/10/14 17:32
*@auther xie
*/
public class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

  private Type type;
  private Charset charset;
  private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

  public FastJsonRequestBodyConverter(Type type, Charset charset) {
    this.type = type;
    this.charset = charset;
  }

  @Override
  public RequestBody convert(T value) throws IOException {
    return RequestBody.create(MEDIA_TYPE, JSON.toJSONString(value).getBytes(charset));
  }
}
