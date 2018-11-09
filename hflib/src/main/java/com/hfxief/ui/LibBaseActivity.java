package com.hfxief.ui;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.WindowManager;

import com.hfxief.R;
import com.hfxief.app.BaseManagers;
import com.hfxief.app.RequestManager;
import com.hfxief.event.FEvent;
import com.hfxief.event.NetEvent;
import com.hfxief.event.StopEvent;
import com.hfxief.utils.BusProvider;
import com.hfxief.utils.Toastor;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

public abstract class LibBaseActivity extends MPermissionActivity implements RequestManager.OnRequestListener {
    private boolean mCheckNetWork;
    private ProgressDialog progressDialog;

    public Toastor toastor;

    protected FragmentManager fragmentManager;

    protected CompositeSubscription subscriptions = new CompositeSubscription();

    protected abstract int getContentView();

    protected abstract void setContentResource();

    protected abstract void startWork(Bundle savedInstanceState);

    protected abstract void stopWork();

    protected abstract void beforWork();

    protected abstract void checkNetWork(boolean isConnected);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        beforWork();
        fragmentManager = getSupportFragmentManager();
        BaseManagers.getActivitiesManager().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        setContentResource();
        ButterKnife.bind(this);
        BusProvider.register(this);
        BaseManagers.getRequestManager().addOnRequestListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus();
        }
        toastor = BaseManagers.getToastor();
        startWork(savedInstanceState);
    }

    private void setTranslucentStatus() {
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager == null) {
            BaseManagers.getActivitiesManager().finishActivity();
        } else {
            if (fragmentManager.getBackStackEntryCount() == 0) {
                BaseManagers.getActivitiesManager().finishActivity();
            } else {
                fragmentManager.popBackStack();
            }
        }
        super.onBackPressed();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        BaseManagers.getRequestManager().removeOnRequestListener(this);
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null && fragments.size() != 0) {
            for (Fragment fragment : fragments) {
                if (fragment != null) fragment.onDestroy();
                fragment = null;
            }
        }
        subscriptions.clear();
        BaseManagers.getActivitiesManager().removeActivity(this);
        stopWork();
        BusProvider.unregister(this);
        super.onDestroy();
    }

    protected void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(getString(R.string.process_dialog_message));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } else {
            if (progressDialog.isShowing()) progressDialog.cancel();
            progressDialog.show();
        }
    }

    protected void dissmissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onStop(StopEvent e) {
        dissmissProgressDialog();
    }

    @Override
    public void onError(FEvent e) {
        dissmissProgressDialog();
    }

    public void setCheckNetWork(boolean mCheckNetWork) {
        this.mCheckNetWork = mCheckNetWork;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetWorkCheck(NetEvent event) {
        if (mCheckNetWork) {
            checkNetWork(event.isConnected);
        }
    }

}
