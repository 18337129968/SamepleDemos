package com.hfxief.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfxief.app.BaseManagers;
import com.hfxief.utils.BusProvider;
import com.hfxief.utils.Toastor;

import butterknife.ButterKnife;

/**
 * Created by IntelliJ IDEA in MaitianCommonLibrary
 * cn.maitian.app.library.ui.fragment
 *
 * @Author: xie
 * @Time: 2016/4/20 10:28
 * @Description:
 */
public abstract class LibBaseFragment extends Fragment {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    public Toastor toastor;
    //控件是否已经初始化
    protected boolean isCreateView = false;
    //是否已经加载过数据
    protected boolean isLoadData = false;

    /**
     * This method will initialize when #{android.support.v4.app.Fragment} call onCreate();
     *
     * @param savedInstanceState data bundle.
     */
    protected abstract void beforeWork(Bundle savedInstanceState);

    protected abstract void initView();

    /**
     * This method will initialize when #{android.support.v4.app.Fragment} call onActivityCreated();
     *
     * @param savedInstanceState data bundle.
     */
    protected abstract void startWork(Bundle savedInstanceState);

    /**
     * This method will initialize when #{android.support.v4.app.Fragment} call onDestroy();
     */
    protected abstract void stopWork();

    /**
     * Return the Fragment's view
     *
     * @return the view of fragment
     */
    protected abstract View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void setContentResource();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        BusProvider.register(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
        beforeWork(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = getContentView(inflater, container, savedInstanceState);
        setContentResource();
        ButterKnife.bind(this, contentView);
        toastor = BaseManagers.getToastor();
        initView();
        isCreateView = true;
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startWork(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        stopWork();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        BusProvider.unregister(this);
    }

}
