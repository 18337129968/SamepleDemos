package com.hfxief.network.http;

import android.text.TextUtils;
import android.webkit.URLUtil;

import com.alibaba.fastjson.JSONException;
import com.hfxief.R;
import com.hfxief.app.BaseApplication;
import com.hfxief.event.FEvent;
import com.hfxief.event.StopEvent;
import com.hfxief.utils.BusProvider;
import com.hfxief.utils.RetrofitUtil;
import com.hfxief.utils.fastjson.FastJsonConverterFactory;
import com.orhanobut.logger.Logger;

import java.io.EOFException;
import java.io.File;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.net.sip.SipErrorCode.CLIENT_ERROR;
import static android.net.sip.SipErrorCode.SERVER_ERROR;
import static com.hfxief.app.HFConstants.HTTP_CACHFILENAME;
import static com.hfxief.app.HFConstants.HTTP_CACHSIZE;
import static com.hfxief.app.HFConstants.HTTP_CONNECTTIME;
import static com.hfxief.app.HFConstants.HTTP_DEBUG;
import static com.hfxief.app.HFConstants.MULTIPART;
import static com.hfxief.app.HFConstants.PLAIN;
import static com.hfxief.app.HFConstants.PULL_IMG_PO;
import static com.hfxief.app.HFConstants.PULL_IMG_PR;
import static com.hfxief.utils.MaitianErrorHandler.BAD_REQUEST;
import static com.hfxief.utils.MaitianErrorHandler.CHECK_NET;
import static com.hfxief.utils.MaitianErrorHandler.CLIENT_TIMEOUT;
import static com.hfxief.utils.MaitianErrorHandler.CONNECT_ERROR;
import static com.hfxief.utils.MaitianErrorHandler.CONN_TIME_OUT;
import static com.hfxief.utils.MaitianErrorHandler.EMS;
import static com.hfxief.utils.MaitianErrorHandler.NET_EOF;
import static com.hfxief.utils.MaitianErrorHandler.NET_OFF;
import static com.hfxief.utils.MaitianErrorHandler.NONE_ERROR;
import static com.hfxief.utils.MaitianErrorHandler.PERMISSION_DENY;
import static com.hfxief.utils.MaitianErrorHandler.SOCKET_TIME_OUT;
import static com.hfxief.utils.MaitianErrorHandler.SYNTAX_ERROR;
import static com.hfxief.utils.MaitianErrorHandler.UNKNOWNS_HOST;

/**
 * @Title: BaseHttp
 * @Description: 描述
 * @date 2017/5/16 11:18
 * @auther xie
 */
public class BaseHttp {

    private Retrofit.Builder rBuilder;
    private OkHttpClient cacheClient;
    private OkHttpClient noCacheClient;

    protected <T> T createService(Class<T> clazz, Map<String, String> headers, String baseURL, boolean isCach) {
        if (TextUtils.isEmpty(baseURL)) {
            throw new IllegalArgumentException("BaseHttp-->createService >>> baseURL can not be empty");
        }

        if (!URLUtil.isHttpUrl(baseURL)) {
            throw new IllegalArgumentException("BaseHttp-->createService >>> baseURL is invalid");
        }

        if (clazz == null) {
            throw new IllegalArgumentException("BaseHttp-->createService >>> clazz can not pass null");
        }

        if (rBuilder == null)
            rBuilder = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(FastJsonConverterFactory.create());
        rBuilder.baseUrl(baseURL).client(getClient(headers, isCach, baseURL));
        return rBuilder.build().create(clazz);
    }

    private OkHttpClient getClient(Map<String, String> headers, boolean isCach, String baseURL) {
        if (isCach) {
            if (cacheClient == null) cacheClient = _createClient(headers, true, baseURL);
            return cacheClient;
        } else {
            if (noCacheClient == null) noCacheClient = _createClient(headers, false, baseURL);
            return noCacheClient;
        }
    }

    private OkHttpClient _createClient(Map<String, String> headers, boolean isCach, String baseURL) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (HTTP_DEBUG) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//                if (HTTP_STETHO)builder.addNetworkInterceptor(new StethoInterceptor());
            builder.addInterceptor(logInterceptor);
        }
        if (baseURL.startsWith("https://")) {
            SSLSocketFactory sslSocketFactory = RetrofitUtil
                    .getSSLSocketFactory(BaseApplication.getInstance(), new int[]{R.raw.xmarket});
            builder.sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(RetrofitUtil.getHostnameVerifier(new String[]{baseURL}));
        }
        builder.connectTimeout(HTTP_CONNECTTIME, TimeUnit.SECONDS);
        builder.readTimeout(HTTP_CONNECTTIME, TimeUnit.SECONDS);
        if (isCach) {
            HttpCachInterceptor httpCachInterceptor = new HttpCachInterceptor(BaseApplication.getInstance());
            builder.addInterceptor(httpCachInterceptor);
            File cacheFile = new File(BaseApplication.getInstance().getCacheDir(), HTTP_CACHFILENAME);
            Cache cache = new Cache(cacheFile, HTTP_CACHSIZE);
            builder.cache(cache);
        } else {
            HttpInterceptor httpInterceptor = new HttpInterceptor(headers);
            builder.addInterceptor(httpInterceptor);
        }
        return builder.build();
    }

    protected Subscription dispach(Observable observable, HttpRequest httpRequest) {
        return dispachHttp(observable, httpRequest);
    }

    private <T> Subscription httpResult(Observable<T> observable, final Subscriber<T> subscriber) {
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    protected <T> Subscription dispachHttp(Observable<T> observable,
                                           final HttpRequest<T> httpRequest) {

        return httpResult(observable, new Subscriber<T>() {
            @Override
            public void onStart() {
                super.onStart();
                httpRequest.onHttpStart();
            }

            @Override
            public void onCompleted() {
                unsubscribe();
                BusProvider.post(new StopEvent());
                httpRequest.onHttpFinish();
            }

            @Override
            public void onError(Throwable e) {
                unsubscribe();
                ThrowErrorInfo(e);
                httpRequest.onHttpError();
                Logger.e("OkHttp--->onError=" + e.getMessage()+"====="+e.getClass().getName());
            }

            @Override
            public void onNext(T result) {
                httpRequest.onHttpSuccess(result);
            }
        });
    }

    private void postEvent(String o) {
        BusProvider.post(new FEvent(o));
    }

    private void ThrowErrorInfo(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int statusCode = httpException.code();
            switch (statusCode) {
                case HttpURLConnection.HTTP_BAD_REQUEST:
                    postEvent(EMS.get(BAD_REQUEST));
                    break;
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                case HttpURLConnection.HTTP_FORBIDDEN:
                    postEvent(EMS.get(PERMISSION_DENY));
                    break;
                case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
                    postEvent(EMS.get(CLIENT_TIMEOUT));
                    break;
                case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                    postEvent(EMS.get(NET_OFF));
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                case HttpURLConnection.HTTP_BAD_GATEWAY:
                case HttpURLConnection.HTTP_UNAVAILABLE:
                    postEvent(EMS.get(BAD_REQUEST));
                    break;
                default:
                    if (statusCode >= 300 && statusCode <= 499) {
                        postEvent(EMS.get(CLIENT_ERROR));
                    } else if (statusCode >= 500 && statusCode <= 599) {
                        postEvent(EMS.get(SERVER_ERROR));
                    } else {
                        postEvent(EMS.get(NONE_ERROR));
                    }
                    break;
            }
        } else if (e instanceof JSONException) {
            postEvent(EMS.get(SYNTAX_ERROR));
        } else if (e instanceof UnknownHostException) {
            postEvent(EMS.get(UNKNOWNS_HOST));
        } else if (e instanceof EOFException) {
            postEvent(EMS.get(NET_EOF));
        } else if (e instanceof SocketTimeoutException) {
            postEvent(EMS.get(CONN_TIME_OUT));
        } else if (e instanceof InterruptedIOException) {
            postEvent(EMS.get(SOCKET_TIME_OUT));
        } else if (e instanceof ConnectException) {
            postEvent(EMS.get(CONNECT_ERROR));
        } else if (e instanceof Error) {
            postEvent(e.getMessage());
        } else {
            postEvent(EMS.get(CHECK_NET));
//            throw new RuntimeException(e);
        }
    }

    public RequestBody getBody(String body) {
        if (!TextUtils.isEmpty(body))
            return RequestBody.create(MediaType.parse(PLAIN), body);
        return null;
    }

    public RequestBody getBody(File body) {
        return RequestBody.create(MediaType.parse(MULTIPART), body);
    }

    protected Map<String, RequestBody> getPhotos(List<File> photoList) {
        Map<String, RequestBody> photos = new LinkedHashMap<>();
        List<File> files = photoList;
        if (files != null && files.size() > 0) {
            for (File file : files) {
                RequestBody photo = getBody(file);
                photos.put(PULL_IMG_PR + files.indexOf(photo) + PULL_IMG_PO + file.getName(), photo);
            }
        }
        return photos;
    }

}
