package com.hfxief.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.hfxief.R;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * SVGDemo
 * com.iss.innoz.svgdemo
 *
 * @Author: xie
 * @Time: 2016/11/30 15:15
 * @Description:
 */


public abstract class MPermissionActivity extends AutoLayoutActivity {

    private int REQUEST_CODE_PERMISSION = 0x00099;

    public abstract void permissionSuccess(int requestCode);

    public abstract void permissionFail(int requestCode);

    public void requestPermission(int requestCode, String... permissions) {
        this.REQUEST_CODE_PERMISSION = requestCode;
        if (checkPermissions(permissions)) {
            permissionSuccess(REQUEST_CODE_PERMISSION);
        } else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()])
                    , REQUEST_CODE_PERMISSION);
        }
    }

    private List<String> getDeniedPermissions(String... permissions) {
        List<String> needPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needPermissions.add(permission);
            }
        }
        return needPermissions;
    }

    private boolean checkPermissions(String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (verifyPermissions(grantResults)) {
                permissionSuccess(requestCode);
            } else {
                permissionFail(requestCode);
                showTipsDialog();
            }
        }
    }

    private void showTipsDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_tip))
                .setMessage(getString(R.string.dialog_message))
                .setNegativeButton(getString(R.string.cancle), (dialog, which) -> {
                }).setPositiveButton(getString(R.string.ok), (dialog, which) -> startAppSetting()).show();
    }

    private void startAppSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    private boolean verifyPermissions(int... grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
