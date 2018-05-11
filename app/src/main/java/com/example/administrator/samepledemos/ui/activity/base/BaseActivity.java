package com.example.administrator.samepledemos.ui.activity.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.samepledemos.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hfxief.ui.LibBaseActivity;
import com.lufficc.stateLayout.StateLayout;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import rx.Subscription;

/**
 * @Title: BaseActivity
 * @Description: 描述
 * @date 2017/5/12 17:07
 * @auther xie
 */
public abstract class BaseActivity extends LibBaseActivity {

    private FrameLayout content;
    private SystemBarTintManager tintManager;
    private ViewStub stub;
    private ImageView leftImg, rightImg, titleImg, imgFind;
    private SimpleDraweeView titleHead;
    private TextView leftTv, rightTv;
    private TextView tittle_text;
    private LinearLayout ll_tltle_left, tittle_bar;
    protected StateLayout stateLayout;

    protected abstract int getContentResource();

    @Override
    protected void beforWork() {

    }

    public void onRetryListener() {
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_base;
    }

    @Override
    protected void setContentResource() {
//       6.0以上透明状态栏
//        StatusBarCompat.translucentStatusBar(this, true);
        content = (FrameLayout) findViewById(R.id.content_frame);
        content.addView(LayoutInflater.from(this).inflate(getContentResource(), null));
        stateLayout = (StateLayout) findViewById(R.id.stateLayout);
        stateLayout.setErrorAndEmptyAction(v -> onRetryListener());
    }

    protected void setTittleText(int resId) {
        setTittleText(getString(resId));
    }

    /**
     * @return void
     * @Title: setTittleText
     * @Description:
     * @params [1、null 只显示StatusBar；2、显示整体Title]
     */
    protected void setTittleText(String title) {
        if (tittle_text == null) {
            headerInit();
            tittle_text = (TextView) findViewById(R.id.tittle_text);
        }
        if (title == null) {
            stub.setVisibility(View.GONE);
            if (tintManager == null)
                tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);
        } else {
            if (tintManager != null)
                tintManager.setStatusBarTintEnabled(false);
            stub.setVisibility(View.VISIBLE);
            tittle_text.setText(title);
        }
    }

    protected void hidStatusBar() {
        if (tintManager != null)
            tintManager.setStatusBarTintEnabled(false);
    }

    public View getStubView() {
        if (tittle_bar == null) {
            tittle_bar = (LinearLayout) findViewById(R.id.tittle_bar);
        }
        return tittle_bar;
    }

    /**
     * @return void
     * @see: headerInit
     * @Description: (初始化title)
     * @params []
     * @since 2.0
     */
    private void headerInit() {
        if (stub == null) {
            stub = ((ViewStub) findViewById(R.id.vs_title));
            stub.inflate();
        }
    }

    /**
     * @return void
     * @see: setLeftView
     * @Description: (设置title左边按钮)
     * @params [visibility 显示状态]
     * @since 2.0
     */
    protected void setLeftView(int visibility) {
        setLeftView(visibility, v -> onBackPressed());
    }

    protected void setLeftView(int visibility, View.OnClickListener leftClick) {
        if (leftImg == null) {
            headerInit();
            leftImg = (ImageView) findViewById(R.id.tittle_logo_img);
            leftTv = (TextView) findViewById(R.id.tittle_left_text);
            ll_tltle_left = (LinearLayout) findViewById(R.id.ll_tltle_left);
        }
        if (View.VISIBLE == visibility) {
            leftImg.setImageResource(R.mipmap.back);
            leftTv.setText(R.string.title_back);
            leftTv.setVisibility(View.GONE);
        } else {
            leftImg.setVisibility(visibility);
            leftTv.setVisibility(visibility);
        }
        ll_tltle_left.setOnClickListener(leftClick);
    }

    protected void setLeftView(int visibility, String name) {
        setLeftView(visibility, name, v -> onBackPressed());
    }

    protected void setLeftView(int visibility, String name, View.OnClickListener leftClick) {
        if (leftImg == null) {
            headerInit();
            leftImg = (ImageView) findViewById(R.id.tittle_logo_img);
            leftTv = (TextView) findViewById(R.id.tittle_left_text);
            ll_tltle_left = (LinearLayout) findViewById(R.id.ll_tltle_left);
        }
        if (View.VISIBLE == visibility) {
            leftImg.setImageResource(R.mipmap.back);
            leftTv.setText(name);
            leftTv.setVisibility(View.VISIBLE);
        } else {
            leftImg.setVisibility(visibility);
            leftTv.setVisibility(visibility);
        }
        ll_tltle_left.setOnClickListener(leftClick);
    }

    /**
     * @return void
     * @see: setLetfText
     * @Description: (设置左边按钮旁边文字内容)
     * @params [name 内容, clickListener 事件]
     * @since 2.0
     */
    public void setLetfText(String name, View.OnClickListener clickListener) {
        if (leftTv == null) {
            headerInit();
            leftTv = (TextView) findViewById(R.id.tittle_left_text);
        }
        leftTv.setVisibility(View.VISIBLE);
        leftTv.setText(name);
        leftTv.setOnClickListener(clickListener);
    }

    /**
     * @return void
     * @see: setTitleHead
     * @Description: (设置title中间图片，文字+图片情况)
     * @params [visibility 显示状态, url 地址]
     * @since 2.0
     */
    public void setTitleHead(int visibility, String url) {
        if (titleHead == null) {
            headerInit();
            titleHead = (SimpleDraweeView) findViewById(R.id.sdv_title_head);
        }
        titleHead.setVisibility(visibility);
        titleHead.setImageURI(url);
    }

    /**
     * @return void
     * @see: setTitleImg
     * @Description: (设置纯图情况，中间图片)
     * @params [visibility 显示状态, id 图片样式, onClickListener 事件]
     * @since 2.0
     */
    public void setTitleImg(int visibility, int id, View.OnClickListener onClickListener) {
        if (titleImg == null) {
            headerInit();
            titleImg = (ImageView) findViewById(R.id.img_title_right);
        }
        titleImg.setVisibility(visibility);
        titleImg.setImageResource(id);
        titleImg.setOnClickListener(onClickListener);
    }

    /**
     * @return void
     * @see: setImgFind
     * @Description: (设置右侧中间的图片)
     * @params [visibility 显示状态, id 图片样式, onClickListener 事件]
     * @since 2.0
     */
    public void setImgFind(int visibility, int id, View.OnClickListener onClickListener) {
        if (imgFind == null) {
            headerInit();
            imgFind = (ImageView) findViewById(R.id.tittle_img_find);
        }
        imgFind.setVisibility(visibility);
        imgFind.setImageResource(id);
        imgFind.setOnClickListener(onClickListener);
    }

    private void initRightView() {
        rightImg = (ImageView) findViewById(R.id.tittle_img_drawer);
        rightTv = (TextView) findViewById(R.id.tittle_login_text);
    }

    private void initRigntWithLeftView() {
        rightImg = (ImageView) findViewById(R.id.tittle_img_find);
        rightTv = (TextView) findViewById(R.id.tittle_login_text);
    }

    private void initRight2View() {
        imgFind = (ImageView) findViewById(R.id.tittle_img_drawer);
        rightImg = (ImageView) findViewById(R.id.tittle_img_find);
    }

    /**
     * @return void
     * @see: setRight2Img
     * @Description: (适合右边两张图片)
     * @params [rightId, right2Id, onClickRight, onClickRight2]
     * @since 2.0
     */
    protected void setRight2Img(int rightId, int right2Id, View.OnClickListener onClickRight, View.OnClickListener onClickRight2) {
        if (rightImg == null) {
            headerInit();
            initRight2View();
        }
        rightImg.setImageResource(rightId);
        rightImg.setOnClickListener(onClickRight);
        imgFind.setImageResource(right2Id);
        imgFind.setOnClickListener(onClickRight2);
    }

    /**
     * @return void
     * @see: setRightView
     * @Description: (设置右边图片及文字)
     * @params [rightId 图片样式, name 内容, onClickListener 事件]
     * @since 2.0
     */
    protected void setRightView(int rightId, String name, View.OnClickListener onClickListener) {
        if (rightImg == null) {
            headerInit();
            initRightView();
        }
        rightImg.setImageResource(rightId);
        rightImg.setOnClickListener(onClickListener);
        rightTv.setText(name);
        rightTv.setOnClickListener(onClickListener);
    }

    /**
     * @return void
     * @see: setRightView
     * @Description: (设置左边边图片及右文字)
     * @params [rightId 图片样式, name 内容, onClickListener 事件]
     * @since 2.0
     */
    protected void setRightWithLeftView(int rightId, String name, View.OnClickListener onImgClick, View.OnClickListener onTvClick) {
        if (rightImg == null) {
            headerInit();
            initRigntWithLeftView();
        }
        rightImg.setImageResource(rightId);
        rightImg.setOnClickListener(onImgClick);
        rightTv.setText(name);
        rightTv.setOnClickListener(onTvClick);
    }

    /**
     * @return android.view.View
     * @see: getRightView
     * @Description: (获取右侧imgview)
     * @params []
     * @since 2.0
     */
    protected View getRightView() {
        if (rightImg == null) {
            headerInit();
            initRightView();
        }
        return rightImg;
    }

    /**
     * @return android.view.View
     * @see: getRightTv
     * @Description: (获取右侧Textview)
     * @params []
     * @since 2.0
     */
    protected View getRightTv() {
        if (rightTv == null) {
            headerInit();
            initRightView();
        }
        return rightTv;
    }

    /**
     * @return void
     * @see: htttpRequest
     * @Description: (默认网络请求, 带dialog)
     * @params [subscription]
     * @since 2.0
     */
    public void htttpRequest(Subscription subscription) {
        showProgressDialog();
        if (subscription != null) {
            subscriptions.add(subscription);
        }
    }

    /**
     * @return void
     * @see: htttpRequest
     * @Description: (可设置dialog开关网络请求)
     * @params [showDialog 是否显示dialog, subscription 网络请求接口]
     * @since 2.0
     */
    public void htttpRequest(boolean showDialog, Subscription subscription) {
        if (showDialog)
            showProgressDialog();
        if (subscription != null) {
            subscriptions.add(subscription);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    public void permissionFail(int requestCode) {

    }

    @Override
    public void permissionSuccess(int requestCode) {

    }
}
