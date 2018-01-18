package reactnative.ng.smc.rxjava2demo.material_design;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.aesthetic.AestheticActivity;

import reactnative.ng.smc.rxjava2demo.R;

public class ShareElementsActivity extends AestheticActivity {
  private   View  view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 代码设置动画
         */
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Fade fade = new Fade();
        fade.setDuration(500);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        setContentView(R.layout.activity_share_elements);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("共享控件");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageView=(ImageView)findViewById(R.id.img);
        CardView cardView = (CardView) findViewById(R.id.cardView);
        TextView info_tv = (TextView) findViewById(R.id.info_tv);
      int flag=  getIntent().getIntExtra("flag",100);
      if (flag==1){
          byte [] bis=getIntent().getByteArrayExtra("bitmap");
          Bitmap bitmap= BitmapFactory.decodeByteArray(bis, 0, bis.length);
          imageView.setImageBitmap(bitmap);
          view=cardView;
      }else if (flag==0){
          imageView.setImageResource(getIntent().getIntExtra("resId",0));
          view=imageView;
      }

         String str= getIntent().getStringExtra("related_info");
        info_tv.setText(str);
        Log.e("信息","--->"+info_tv.getText().toString());
        ViewCompat.setTransitionName(view, "shareName");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAfterTransition(ShareElementsActivity.this);
            }
        });
    }
}
