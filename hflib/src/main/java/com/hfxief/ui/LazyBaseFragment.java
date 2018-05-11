package com.hfxief.ui;

import android.os.Bundle;

/**
 * trunk
 * com.iss.innoz.ui.fragment.base
 *
 * @Author: xie
 * @Time: 2016/10/27 11:13
 * @Description:
 */


public abstract class LazyBaseFragment extends LibBaseFragment {

    protected abstract void initData();

    protected abstract void showData();


    @Override
    protected void startWork(Bundle savedInstanceState) {
        //第一个fragment会调用
        if (getUserVisibleHint())
            lazyLoad();
    }

    private void lazyLoad() {
        //如果没有加载过就加载，否则就不再加载了
        if (!isLoadData) {
            //加载数据操作
            isLoadData = true;
            initData();
        }
        showData();
    }

    //此方法在控件初始化前调用，所以不能在此方法中直接操作控件会出现空指针
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreateView) {
            lazyLoad();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isLoadData = false;
    }
}
