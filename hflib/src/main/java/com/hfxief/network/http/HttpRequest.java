package com.hfxief.network.http;

/**
 * trunk
 * com.iss.innoz.network.http
 *
 * @Author: xie
 * @Time: 2016/10/17 15:20
 * @Description:
 */


public abstract class HttpRequest<T> implements IRequest<T> {

    @Override
    public void onHttpStart() {
    }

    @Override
    public void onHttpFinish() {
    }

}
