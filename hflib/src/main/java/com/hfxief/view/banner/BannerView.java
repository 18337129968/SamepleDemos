package com.hfxief.view.banner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hfxief.R;
import com.hfxief.bean.BannerBean;
import com.hfxief.bean.BannerIndicatorBean;
import com.hfxief.bean.IBean;
import com.hfxief.bean.PictureBean;
import com.hfxief.os.TimerHandler;
import com.hfxief.utils.DensityUtils;
import com.hfxief.utils.image.ImageUtil;
import com.hfxief.view.banner.adapter.UltraViewPagerAdapter;
import com.hfxief.view.structure.IInnerImageSetter;
import com.hfxief.view.structure.OnBannerItemClickLisenter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * 广告图BannerView
 * Created by xie on 2018/5/11.
 */
public class BannerView extends ViewGroup implements ViewPager.OnPageChangeListener, TimerHandler.TimerHandlerListener {
    public static final int GRAVITY_RIGHT = 0;
    public static final int GRAVITY_CENTER = 1;
    public static final int GRAVITY_LEFT = 2;
    private boolean init;
    private boolean isIndicatorOut;
    private int mIndicatorMarginTop;
    private int mIndicatorMargin;
    private int mIndicatorMarginBottom;
    private float xDown;
    private float yDown;
    private float itemWidthRatio = Float.NaN;
    private float pageRatio = Float.NaN;
    private int height = ViewGroup.LayoutParams.WRAP_CONTENT;
    private TimerHandler timer;
    private BannerIndicator mIndicator;
    private BannerAdapter mBannerAdapter;
    private IInnerImageSetter mImageSetter;
    private OnBannerItemClickLisenter onBannerItemClickLisenter;
    private BannerViewPager mUltraViewPager;
    private Constructor imageViewConstructor;
    private UltraViewPagerAdapter mBannerWrapper;
    private ScreenBroadcastReceiver mScreenBroadcastReceiver;
    private List<IBean> list = new ArrayList<>();
    private IntentFilter filter = new IntentFilter();

    private boolean scrollNextPage() {
//        direction = 1;
        boolean isChange = false;
        if (mUltraViewPager != null && mUltraViewPager.getAdapter() != null && mUltraViewPager.getAdapter().getCount() > 0) {
            final int curr = mUltraViewPager.getCurrentItemFake();
            int nextPage = 0;
            if (curr < mUltraViewPager.getAdapter().getCount() - 1) {
                nextPage = curr + 1;
                isChange = true;
            }
            mUltraViewPager.setCurrentItemFake(nextPage, true);
        }
        return isChange;
    }

    private int getNextItemIndex() {
        int nextIndex = mUltraViewPager.getNextItem();
        return nextIndex;
    }

    private void startTimer() {
        if (timer == null || mUltraViewPager == null || !timer.isStopped()) {
            return;
        }
        timer.setListener(this);
        timer.removeCallbacksAndMessages(null);
        timer.tick(0);
        timer.setStopped(false);
    }

    private void setAutoScroll(int intervalInMillis, SparseIntArray intervalArray) {
        if (0 == intervalInMillis) {
            return;
        }
        if (timer != null) {
            disableAutoScroll();
        }
        timer = new TimerHandler(intervalInMillis);
        timer.setSpecialInterval(intervalArray);
        startTimer();
    }

    private void stopTimer() {
        if (timer == null || mUltraViewPager == null || timer.isStopped()) {
            return;
        }
        timer.removeCallbacksAndMessages(null);
        timer.setListener(null);
        timer.setStopped(true);
    }

    private void disableAutoScroll() {
        stopTimer();
        timer = null;
    }

    private void setIndicatorMargin(float mIndicatorMarginTop, float mIndicatorMarginBottom, float mIndicatorMargin) {
        if (!Float.isNaN(mIndicatorMarginTop))
            this.mIndicatorMarginTop = DensityUtils.dp2px(getContext(), mIndicatorMarginTop);
        if (!Float.isNaN(mIndicatorMarginBottom))
            this.mIndicatorMarginBottom = DensityUtils.dp2px(getContext(), mIndicatorMarginBottom);
        if (!Float.isNaN(mIndicatorMargin))
            this.mIndicatorMargin = DensityUtils.dp2px(getContext(), mIndicatorMargin);
    }

    private void setIndicatorGravity(int gravity) {
        if (mIndicator != null) mIndicator.setGravity(gravity);
    }

    private void updateIndicators(BannerIndicatorBean indicator) {
        this.isIndicatorOut = indicator.isIndicatorOut();
        setIndicatorMargin(indicator.getmIndicatorMarginTop(), indicator.getmIndicatorMarginBottom(), indicator.getmIndicatorMargin());
        switch (indicator.getIndicatorGravity()) {
            case GRAVITY_LEFT:
                setIndicatorGravity(Gravity.LEFT);
                break;
            case GRAVITY_CENTER:
                setIndicatorGravity(Gravity.CENTER_HORIZONTAL);
                break;
            case GRAVITY_RIGHT:
                setIndicatorGravity(Gravity.RIGHT);
                break;
        }
        int padding = DensityUtils.dp2px(getContext(), indicator.getIndicatorPadding());
        mIndicator.setPadding(padding, 0, padding, 0);
        mIndicator.updateIndicators(indicator);
        mIndicator.setCurrItem(mUltraViewPager.getCurrentItem());
    }

    private void init() {
        mUltraViewPager = new BannerViewPager(getContext());
        mUltraViewPager.setId(R.id.TANGRAM_BANNER_ID);
        mIndicator = new BannerIndicator(getContext());
        addView(mUltraViewPager);
        addView(mIndicator);
        mIndicatorMargin = DensityUtils.dp2px(getContext(), 2);
        mIndicatorMarginTop = DensityUtils.dp2px(getContext(), 5);
        mIndicatorMarginBottom = DensityUtils.dp2px(getContext(), 5);
        mScreenBroadcastReceiver = new ScreenBroadcastReceiver(this);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
    }

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void setAdapter(PagerAdapter adapter) {
        mUltraViewPager.setAdapter(adapter);
        disableAutoScroll();//reset timer when reuse
        mUltraViewPager.removeOnPageChangeListener(this);
        mUltraViewPager.addOnPageChangeListener(this);
    }

    public void setImageSetter(@NonNull IInnerImageSetter imageSetter, @NonNull Class<? extends ImageView> sImageClass) {
        this.mImageSetter = imageSetter;
        try {
            imageViewConstructor = sImageClass.getConstructor(Context.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void setOnBannerItemClickLisenter(@NonNull OnBannerItemClickLisenter onBannerItemClickLisenter) {
        this.onBannerItemClickLisenter = onBannerItemClickLisenter;
    }

    public void postBindView(IBean iBean) {
        BannerBean bean = (BannerBean) iBean;
        this.init = true;
        this.pageRatio = bean.getPageRatio();
        this.itemWidthRatio = bean.getItemWidthRatio();
        this.list = bean.getPictures();
        if (mBannerWrapper == null) {
            mBannerAdapter = new BannerAdapter();
            mBannerWrapper = new UltraViewPagerAdapter(mBannerAdapter);
//            mBannerWrapper = new UltraViewPagerAdapter(bean.getPagerApapter());
        }
        if (mImageSetter != null) {
            setAdapter(mBannerWrapper);
        } else {
            throw new NullPointerException(String.valueOf("mImageSetter is null,please bannerView.setImageSetter()"));
        }
        if (Float.isNaN(pageRatio)) {
            mUltraViewPager.setAutoMeasureHeight(true);
        } else {
            mUltraViewPager.setAutoMeasureHeight(false);
        }
        if (!Float.isNaN(itemWidthRatio)) {
            mUltraViewPager.setClipToPadding(false);
            mUltraViewPager.setClipChildren(false);
        } else {
            mUltraViewPager.setClipToPadding(true);
            mUltraViewPager.setClipChildren(true);
        }
        if (bean.isCenterPage() && itemWidthRatio != 1) {
            int sum = getResources().getDisplayMetrics().widthPixels;
            int padding = (int) ((1 - itemWidthRatio) * sum / (2 - itemWidthRatio));
            mUltraViewPager.setPadding(padding, 0, 0, 0);
        } else {
            int paddingLeft = DensityUtils.dp2px(getContext(), bean.getPaddingLeft());
            int paddingRight = DensityUtils.dp2px(getContext(), bean.getPaddingRight());
            mUltraViewPager.setPadding(paddingLeft, 0, paddingRight, 0);
        }
        if (bean.isEnableLoop()) {
            mUltraViewPager.setEnableLoop(bean.isEnableLoop());
            mUltraViewPager.setOffscreenPageLimit(3);
        }
        mUltraViewPager.setItemRatio(bean.getItemRatio());
        mUltraViewPager.setCurrentItem(bean.getCurrentItem());
        mUltraViewPager.setPageMargin(DensityUtils.dp2px(getContext(), bean.getPageMargin()));
        setBackgroundColor(getResources().getColor(bean.getBackgroundColor()));
        setAutoScroll(bean.getAutoScrollMillis(), bean.getIntervalArray());
        if (bean.getIndicatorBean() != null) updateIndicators(bean.getIndicatorBean());
    }

    public int getCurrentItem() {
        return mUltraViewPager.getCurrentItem();
    }

    @Override
    public int getNextItem() {
        return getNextItemIndex();
    }

    @Override
    public void callBack() {
        scrollNextPage();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mIndicator.setCurrItem(mUltraViewPager.getCurrentItem());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            startTimer();
        } else {
            stopTimer();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startTimer();
        getContext().registerReceiver(mScreenBroadcastReceiver, filter);
    }

    @Override
    protected void onDetachedFromWindow() {
        stopTimer();
        getContext().unregisterReceiver(mScreenBroadcastReceiver);
        super.onDetachedFromWindow();
    }

    @Override
    public void onStartTemporaryDetach() {
        stopTimer();
        super.onStartTemporaryDetach();
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        startTimer();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (timer != null) {
            final int action = ev.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                stopTimer();
            }
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                startTimer();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float x = ev.getRawX();
        float y = ev.getRawY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                xDown = x;
                yDown = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int xDiff = (int) (x - xDown);
                int yDiff = (int) (y - yDown);
                if (Math.abs(xDiff) >= Math.abs(yDiff)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!Float.isNaN(pageRatio)) {
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (widthSize * pageRatio), MeasureSpec.EXACTLY);
        } else if (height > 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        mUltraViewPager.measure(widthMeasureSpec, heightMeasureSpec);
        mIndicator.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int measureWidth = mUltraViewPager.getMeasuredWidth();
        int measureHeight = mUltraViewPager.getMeasuredHeight();
        if (isIndicatorOut) {
            int indicatorHeight = mIndicator.getMeasuredHeight();
            setMeasuredDimension(measureWidth, measureHeight + indicatorHeight);
        } else {
            setMeasuredDimension(measureWidth, measureHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int measureWidth = mUltraViewPager.getMeasuredWidth();
        int measureHeight = mUltraViewPager.getMeasuredHeight();
        int indicatorHeight = mIndicator.getMeasuredHeight();
        int top = getPaddingTop();
        int left = getPaddingLeft();
        mUltraViewPager.layout(left, top, measureWidth, top + measureHeight);
        top += measureHeight;
        if (isIndicatorOut) {
            mIndicator.layout(left, top, measureWidth, top + measureHeight + indicatorHeight);
        } else {
            mIndicator.layout(left, top - indicatorHeight, measureWidth, top);
        }
    }

    private class ScreenBroadcastReceiver extends BroadcastReceiver {

        private String action = null;
        private BannerView mBannerView = null;

        public ScreenBroadcastReceiver(BannerView bannerView) {
            mBannerView = bannerView;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                mBannerView.startTimer();
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                mBannerView.stopTimer();
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                mBannerView.startTimer();
            }
        }
    }

    private class BannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = ImageUtil.createImageInstance(getContext(), imageViewConstructor);
            if (imageView != null) {
                container.addView(imageView, new ViewPager.LayoutParams());
                IBean iBean = list.get(position);
                mImageSetter.doLoadImageUrl(imageView, iBean);
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onBannerItemClickLisenter != null)
                            onBannerItemClickLisenter.onBannerItemClick(v, iBean, position);
                    }
                });
                return imageView;
            } else {
                return container;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (object instanceof ImageView) {
                ImageView imageView = (ImageView) object;
                container.removeView(imageView);
            }
        }

        @Override
        public float getPageWidth(int position) {
            return Float.isNaN(itemWidthRatio) ? 1f : itemWidthRatio;
        }
    }

    class BannerIndicator extends LinearLayout {
        //样式：0 无样式 1 纯点阵样式 2 图片样式
        private final int STYLE_NONE = 0;
        private final int STYLE_DOT = 1;
        private final int STYLE_IMG = 2;
        private int norColor;
        private int focusColor;
        private int radius;
        private int style;
        private int width;
        private int height;
        private boolean isContinu;
        private ImageView[] mImageViews;
        private Pair<PictureBean, PictureBean> pictures;

        private GradientDrawable getGradientDrawable(int color, float radius) {
            GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{color, color});
            gradientDrawable.setShape(GradientDrawable.OVAL);
            gradientDrawable.setCornerRadius(radius);
            return gradientDrawable;
        }

        private ImageView setImageViewParams(ImageView imageView, boolean isNormol) {
            if (imageView != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                if (style == STYLE_IMG || style == STYLE_DOT) {
                    if (isNormol) {
                        layoutParams.setMargins(mIndicatorMargin, mIndicatorMarginTop, 0, mIndicatorMarginBottom);
                        if (width > 0) {
                            layoutParams.width = width;
                        }
                    } else {
                        layoutParams.setMargins(-mIndicatorMargin, mIndicatorMarginTop, 0, mIndicatorMarginBottom);
                        if (width > 0) {
                            layoutParams.width = width + 2 * mIndicatorMargin;
                        }
                    }
                    if (height > 0) {
                        layoutParams.height = height;
                    }
                    imageView.setLayoutParams(layoutParams);
                }
            }
            return imageView;
        }

        private void initIndicators() {
            if (mUltraViewPager.getWrapperAdapter() == null) {
                return;
            }
            /*初始化当前样式*/
            if (norColor != 0 && focusColor != 0 && radius > 0) {
                style = STYLE_DOT;
            } else if (pictures != null && pictures.first != null && pictures.second != null) {
                style = STYLE_IMG;
            } else {
                style = STYLE_NONE;
                setVisibility(INVISIBLE);
                return;
            }
            setVisibility(VISIBLE);
            /*初始化宽高*/
            if (style == STYLE_IMG) {
                Pair<Integer, Integer> norSize = ImageUtil.getImageSize(pictures.first.getUrl());
                Pair<Integer, Integer> focSize = ImageUtil.getImageSize(pictures.second.getUrl());
                if (norSize != null && focSize != null) {
                    width = Math.max(norSize.first, focSize.first);
                    height = Math.max(norSize.second, focSize.second);
                } else {
                    if (norSize != null) {
                        width = norSize.first;
                        height = norSize.second;
                    }
                    if (focSize != null) {
                        width = focSize.first;
                        height = focSize.second;
                    }
                }
            } else {
                width = 2 * radius;
                height = 2 * radius;
            }
            /*创建ImageView*/
            int count = mUltraViewPager.getWrapperAdapter().getCount();
            if (mImageViews == null) {
                mImageViews = new ImageView[count];
                for (int i = 0; i < mImageViews.length; i++) {
                    mImageViews[i] = ImageUtil.createImageInstance(getContext(), imageViewConstructor);
                    if (mImageViews[i] == null) break;
                    mImageViews[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    addView(mImageViews[i]);
                }
            } else if (mImageViews.length != count) {
                removeAllViews();
                ImageView[] old = mImageViews;
                mImageViews = new ImageView[count];
                System.arraycopy(old, 0, mImageViews, 0, Math.min(old.length, count));
                for (int i = 0; i < mImageViews.length; i++) {
                    if (mImageViews[i] == null) {
                        mImageViews[i] = ImageUtil.createImageInstance(getContext(), imageViewConstructor);
                        if (mImageViews[i] == null) continue;
                        mImageViews[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    }
                    addView(mImageViews[i]);
                }
            }
            /*配置ImageView参数*/
            int position = mUltraViewPager.getCurrentItem();
            for (int i = 0; i < mImageViews.length; i++) {
                ImageView imageView = setImageViewParams(mImageViews[i], true);
                if (imageView == null) continue;
                if (style == STYLE_DOT) {
                    imageView.setImageDrawable(getGradientDrawable(position == i ? focusColor : norColor, radius));
                } else {
                    if (init) {
                        mImageSetter.doLoadImageUrl(imageView, position == i ? pictures.first : pictures.second);
                        if (i == position) {
                            imageView.setTag(R.id.TANGRAM_BANNER_INDICATOR_POS, position);
                        }
                    }
                }
            }
            if (style == STYLE_IMG && !init) {
                for (int i = 0; i < mImageViews.length; i++) {
                    ImageView imageView = mImageViews[i];
                    if (imageView == null) continue;
                    if (imageView.getTag(R.id.TANGRAM_BANNER_INDICATOR_POS) != null) {
                        imageView.setTag(R.id.TANGRAM_BANNER_INDICATOR_POS, null);
                        mImageSetter.doLoadImageUrl(imageView, pictures.second);
                    }
                }
                if (mImageViews[position] != null) {
                    mImageViews[position].setTag(R.id.TANGRAM_BANNER_INDICATOR_POS, position);
                    mImageSetter.doLoadImageUrl(mImageViews[position], pictures.first);
                }
            }
        }

        public void updateIndicators(BannerIndicatorBean indicator) {
            this.norColor = indicator.getNorColor();
            this.focusColor = indicator.getFocusColor();
            this.radius = indicator.getRadius();
            this.pictures = indicator.getPictures();
            this.isContinu = indicator.isIndicatorContinu();
            initIndicators();
        }

        public BannerIndicator(Context context) {
            super(context);
        }

        public void setCurrItem(int position) {
            if (mImageViews != null) {
                if (isContinu) {
                    for (int i = 0; i <= position; i++) {
                        ImageView imageView = mImageViews[i];
                        if (i > 0) imageView = setImageViewParams(imageView, false);
                        if (imageView == null) continue;
                        if (style == STYLE_IMG && pictures != null) {
                            imageView.setTag(R.id.TANGRAM_BANNER_INDICATOR_POS, i);
                            mImageSetter.doLoadImageUrl(imageView, pictures.first);
                        } else if (style == STYLE_DOT) {
                            imageView.setImageDrawable(getGradientDrawable(position == i ? focusColor : norColor, radius));
                        }
                    }
                    for (int i = position + 1; i < mImageViews.length; i++) {
                        ImageView imageView = setImageViewParams(mImageViews[i], true);
                        if (imageView == null) continue;
                        if (style == STYLE_DOT) {
                            imageView.setImageDrawable(getGradientDrawable(position == i ? focusColor : norColor, radius));
                        } else if (style == STYLE_IMG && imageView.getTag(R.id.TANGRAM_BANNER_INDICATOR_POS) != null && pictures != null) {
                            imageView.setTag(R.id.TANGRAM_BANNER_INDICATOR_POS, null);
                            mImageSetter.doLoadImageUrl(imageView, pictures.second);
                        }
                    }
                } else {
                    for (int i = 0; i < mImageViews.length; i++) {
                        ImageView imageView = mImageViews[i];
                        if (imageView == null) continue;
                        if (style == STYLE_DOT) {
                            imageView.setImageDrawable(getGradientDrawable(position == i ? focusColor : norColor, radius));
                        } else if (style == STYLE_IMG && imageView.getTag(R.id.TANGRAM_BANNER_INDICATOR_POS) != null && pictures != null) {
                            imageView.setTag(R.id.TANGRAM_BANNER_INDICATOR_POS, null);
                            mImageSetter.doLoadImageUrl(imageView, pictures.second);
                        }
                    }
                    if (style == STYLE_IMG && mImageViews[position] != null && pictures != null) {
                        mImageViews[position].setTag(R.id.TANGRAM_BANNER_INDICATOR_POS, position);
                        mImageSetter.doLoadImageUrl(mImageViews[position], pictures.first);
                    }
                }

            }
        }
    }

}
