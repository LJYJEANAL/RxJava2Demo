package reactnative.ng.smc.rxjava2demo.material_design;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.aesthetic.AestheticActivity;

import reactnative.ng.smc.rxjava2demo.R;

public class ShareImgActivity extends AestheticActivity {

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 代码设置动画
         */
       /* getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Fade fade = new Fade();
        fade.setDuration(500);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade)*/;
        view = View.inflate(ShareImgActivity.this, R.layout.activity_share_img, null);
        setContentView(view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("共享图片");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imageView = (ImageView) findViewById(R.id.img);
        TextView related_info = (TextView) findViewById(R.id.related_info);

        Bitmap bitmap = getIntent().getParcelableExtra("bitmap");
        imageView.setImageBitmap(bitmap);

        related_info.setText(getIntent().getStringExtra("sharedTv"));
        ViewCompat.setTransitionName(imageView, "sharedImage");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.finishAfterTransition(ShareImgActivity.this);
            }
        });
    }
}
