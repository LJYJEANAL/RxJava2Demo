package reactnative.ng.smc.rxjava2demo.material_design;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import com.afollestad.aesthetic.AestheticActivity;

import reactnative.ng.smc.rxjava2demo.R;

/**
 *
 可以通过如下代码设置波纹的背景：

 android:background=?android:attr/selectableItemBackground波纹有边界

 android:background=?android:attr/selectableItemBackgroundBorderless波纹超出边界
 我们也可以通过设置xml属性来调节动画颜色，从而可以适应不同的主题：

 android:colorControlHighlight：设置波纹颜色

 android:colorAccent：设置checkbox等控件的选中颜色
 */
public class MetrialAnimActivity extends AestheticActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 手动设置进退动画
         */
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        int flag = getIntent().getExtras().getInt("flag");
        // 设置不同的动画效果
        switch (flag) {
            case 0:
                Explode explode = new Explode();
                explode.setDuration(500);
                getWindow().setEnterTransition(explode);
                getWindow().setExitTransition(explode);
                break;
            case 1:
                Slide slide = new Slide();
                slide.setDuration(500);
                getWindow().setEnterTransition(slide);
                getWindow().setExitTransition(slide);

                break;
            case 2:
                Fade fade = new Fade();
                fade.setDuration(500);
                getWindow().setEnterTransition(fade);
                getWindow().setExitTransition(fade);
                break;
        }
        setContentView(R.layout.activity_metrial_anim);
        final View oval = this.findViewById(R.id.oval);
        oval.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Animator animator = ViewAnimationUtils.createCircularReveal(
                        oval,
                        oval.getWidth()/2,
                        oval.getHeight()/2,
                        oval.getWidth(),
                        0);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(2000);
                animator.start();
            }
        });

        final View rect = this.findViewById(R.id.rect);

        rect.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Animator animator = ViewAnimationUtils.createCircularReveal(
                        rect,
                        0,
                        0,
                        0,
                        (float) Math.hypot(rect.getWidth(), rect.getHeight()));
                animator.setInterpolator(new AccelerateInterpolator());
                animator.setDuration(2000);
                animator.start();
            }
        });


    }
    
}
