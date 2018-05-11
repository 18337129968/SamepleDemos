package com.example.administrator.samepledemos.ui.activity.camera;

import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.administrator.samepledemos.R;
import com.example.administrator.samepledemos.ui.activity.base.BaseActivity;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;

public class CameraActivity extends BaseActivity {
    @BindView(R.id.img_arrow)
    ImageView imgArrow;
    private Animation anim, reAnima;
    private boolean isArrow = false;

    private Animation getAnimation() {
        Animation anim = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(500); // 设置动画时间
        anim.setInterpolator(new AccelerateInterpolator()); // 设置插入器
        return anim;
    }

    private Animation reAnimation() {
        Animation anim = new RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(500); // 设置动画时间
        anim.setInterpolator(new AccelerateInterpolator()); // 设置插入器
        return anim;
    }

    @Override
    protected void startWork(Bundle savedInstanceState) {
        setTittleText("Camera");
        anim = getAnimation();
        reAnima = reAnimation();
        RxView.clicks(imgArrow).map(aVoid -> isArrow= !isArrow).subscribe(aBoolean -> {
            toastor.showLongToast("aBoolean="+aBoolean);
            if (aBoolean) {
                imgArrow.startAnimation(anim);
            } else {
                imgArrow.startAnimation(reAnima);
            }
        });

    }

    @Override
    protected void stopWork() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        imgArrow.clearAnimation();
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_camera;
    }

}
