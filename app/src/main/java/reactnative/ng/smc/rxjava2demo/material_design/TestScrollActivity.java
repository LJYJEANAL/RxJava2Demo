package reactnative.ng.smc.rxjava2demo.material_design;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.aesthetic.Aesthetic;
import com.afollestad.aesthetic.AestheticKeyProvider;

import reactnative.ng.smc.rxjava2demo.R;

public class TestScrollActivity extends AppCompatActivity implements AestheticKeyProvider {
    private float totalHeight;      //总高度
    private float toolBarHeight;    //toolBar高度
    private float offSetHeight;     //总高度 -  toolBar高度  布局位移值
    private float llHeight;         //搜索框高度

    private float llHeightOffScale;     //高度差比值
    private float llOffDistance;        //距离差
    private float llOffDistanceScale;   //距离差比值
    private FrameLayout.LayoutParams params;

    Toolbar toolbar;
    CollapsingToolbarLayout toolbarLayout;
    AppBarLayout appBar;
    EditText fab;
    RelativeLayout ll;
    TextView bac;
    FrameLayout fl;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Aesthetic.attach(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scroll);
        // 经测试在代码里直接声明透明状态栏更有效 可是顶部需要预留出状态栏的高度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            }
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        appBar = (AppBarLayout) findViewById(R.id.app_bar);
        fab = (EditText) findViewById(R.id.fab);
        ll = (RelativeLayout) findViewById(R.id.ll);
        bac = (TextView) findViewById(R.id.bac);
        fl = (FrameLayout) findViewById(R.id.fl);
        image = (ImageView) findViewById(R.id.image);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        bac.setAlpha(0f);
        totalHeight = getResources().getDimension(R.dimen.app_bar_height);
        toolBarHeight = getResources().getDimension(R.dimen.tool_bar_height);
        offSetHeight = totalHeight - toolBarHeight;
        /**
         *   移动效果值／最终效果值 =  移动距离／ 能移动总距离（确定）
         */
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                //第一次进入获取高度，以及差值 高度差比值
                if (llHeight == 0){
                    llHeight = ll.getMeasuredHeight();
                    params = (FrameLayout.LayoutParams) ll.getLayoutParams();

                    //算出高度偏移量比值  相对与llHeight
                    llHeightOffScale = 1.0f - (toolBarHeight / llHeight);

                    //得到滑动差值 就是布局marginTop
                    llOffDistance = params.topMargin;
                    //得到滑动比值
                    llOffDistanceScale = llOffDistance / offSetHeight;
                }

                //滑动一次 得到渐变缩放值
                float alphaScale = (-verticalOffset) / offSetHeight;

                //获取高度缩放值
                float llHeightScale = 1.0f-(llHeightOffScale*((-verticalOffset)/offSetHeight));
                //计算maigintop值
                float distance = llOffDistance - (-verticalOffset)*llOffDistanceScale;
                Log.i("信息","布局位移值offSetHeight:"+offSetHeight+" 搜索框高度 llHeight:"+llHeight+
                        " 高度偏移量比值 llHeightOffScale:"+llHeightOffScale+"  滑动差值 就是布局marginTop  llOffDistance:"+llOffDistance
                +"//得到滑动比值llOffDistanceScale :"+llOffDistanceScale+" 滑动一次 得到渐变缩放值 alphaScale"+alphaScale+
                        "   verticalOffset:"+verticalOffset+" marginTop:"+distance);
                image.setAlpha(1.0f-alphaScale);
                bac.setAlpha(alphaScale);
                params.height = (int)(llHeight* llHeightScale);
                params.setMargins(0,(int)distance,0,0);

                fl.requestLayout();



            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Aesthetic.resume(this);
    }

    @Override
    protected void onPause() {
        Aesthetic.pause(this);
        super.onPause();
    }
    @Nullable
    @Override
    public String key() {
        return null;
    }
}
