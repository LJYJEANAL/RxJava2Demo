package reactnative.ng.smc.rxjava2demo.mvp.view;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import io.reactivex.disposables.Disposable;
import reactnative.ng.smc.rxjava2demo.R;
import reactnative.ng.smc.rxjava2demo.mvp.presenter.IUserPrestener;
import reactnative.ng.smc.rxjava2demo.synch.Ticket;

/**
 * 子类实现接口不一定要实现接口的所有方法
 * 如果该子类是抽象类（abstract 修饰的）则不一定是实现接口的所有方法
 * 如果不是抽象类则必须实现接口的所有方法
 */
public   class MvpActivity extends AppCompatActivity implements IUserView {

    private SpinKitView spinKitView;
    private TextView text;
    private RelativeLayout rlContain;
    private ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        initViews();
    }

    private void initViews() {
        spinKitView = (SpinKitView) findViewById(R.id.spin_kit);
        text = (TextView) findViewById(R.id.text);
        final IUserPrestener iuserPrestener=new IUserPrestener(this);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iuserPrestener.getData();
            }
        });
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ticket t = new Ticket();//创建一个线程任务对象。
                //创建4个线程同时卖票
                Thread t1 = new Thread(t);
                Thread t2 = new Thread(t);
                Thread t3 = new Thread(t);
                Thread t4 = new Thread(t);
                //启动线程
                t1.start();
                t2.start();
                t3.start();
                t4.start();
            }
        });
        rlContain = (RelativeLayout) findViewById(R.id.rl_container);
        icon = (ImageView) findViewById(R.id.icon);
        icon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ClipData.Item item=new ClipData.Item("到了");
                ClipData data=new ClipData(null,new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},item);
                v.startDrag(data,new View.DragShadowBuilder(v),null,0);
                return true;
            }
        });

      /*  //目标区域设置拖拽事件监听
        rlContain.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v,final DragEvent event) {
               int action= event.getAction();
                switch (action){
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.i("信息","拖拽开始");
                        icon.setVisibility(View.GONE);
                        v.invalidate();
                        return event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.i("信息","被拖拽的view进去目标区域");
                        v.invalidate();
                        return true;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.i("信息","被拖拽的view在目标区域移动"+"--X:"+event.getX()+"-->Y:"+event.getY());

                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        v.invalidate();
                        Log.i("信息","被拖拽的view离开目标区域");
                        return true;
                    case DragEvent.ACTION_DROP:
                        v.invalidate();
                        Log.i("信息","放开被拖拽的view");
                        ClipData.Item item=event.getClipData().getItemAt(0);
                        String content=item.getText().toString();
                        Toast.makeText(MvpActivity.this,content,Toast.LENGTH_SHORT).show();
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        v.invalidate();
                        Log.i("信息","拖拽完成");
                        icon.setVisibility(View.VISIBLE);
                        return true;
                }
                return false;
            }
        });
*/
    }

   private Disposable disposable;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable!=null)
        disposable.dispose();
    }

    /**
     * 实现view接口的方法
     */
    @Override
    public void showLoading() {
        spinKitView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        spinKitView.setVisibility(View.GONE);

    }

    @Override
    public void showFailedError() {
        Toast.makeText(this, "showFailedError", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showData(String data) {
        text.setText(data);
    }

    @Override
    public void clearAll() {
        text.setText("");
    }

    @Override
    public void disPosable(Disposable disposable) {
        this.disposable=disposable;
    }
}
