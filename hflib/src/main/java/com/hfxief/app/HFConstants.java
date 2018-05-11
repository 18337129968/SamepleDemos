package com.hfxief.app;

/**
 * HFSampleProject
 * com.hfxief.app
 *
 * @Author: xie
 * @Time: 2016/12/22 15:59
 * @Description:
 */


public class HFConstants {
    //-------debug和缓存---------
    public static final Boolean HTTP_DEBUG = true;
    public static final Boolean HTTP_STETHO = true;
    public static final int HTTP_CACHSIZE = 10 * 1024 * 1024;  //设置缓存 10M
    public static final String HTTP_CACHFILENAME = "httpCache";
    public static final int HTTP_CONNECTTIME = 60; //秒
    public static final String PLAIN = "text/plain";
    public static final String MULTIPART = "multipart/form-data";
    public static final String PULL_IMG_PR = "file";
    public static final String PULL_IMG_PO = "\"; filename=\"";
    public static final String IMG_NAME = "photo.png";

}
