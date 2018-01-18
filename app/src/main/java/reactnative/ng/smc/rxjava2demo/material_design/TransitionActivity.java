package reactnative.ng.smc.rxjava2demo.material_design;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;

import com.afollestad.aesthetic.AestheticActivity;

import reactnative.ng.smc.rxjava2demo.R;

public class TransitionActivity extends AestheticActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setEnterTransition(explode);
        setContentView(R.layout.activity_transition);
    }
    private Intent intent;
    // 设置不同动画效果
    public void explode(View view) {
        intent = new Intent(this, MetrialAnimActivity.class);
        intent.putExtra("flag", 0);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this)
                        .toBundle());
    }
    // 设置不同动画效果
    public void slide(View view) {
        intent = new Intent(this, MetrialAnimActivity.class);
        intent.putExtra("flag", 1);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this)
                        .toBundle());
    }
    // 设置不同动画效果
    public void fade(View view) {
        intent = new Intent(this, MetrialAnimActivity.class);
        intent.putExtra("flag", 2);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this)
                        .toBundle());
    }
}
