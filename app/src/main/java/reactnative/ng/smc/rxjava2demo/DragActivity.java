package reactnative.ng.smc.rxjava2demo;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import reactnative.ng.smc.rxjava2demo.util.SysInfoUtils;

public class DragActivity extends AppCompatActivity {
    ImageView imageView;
    ImageView imageView2;
    RelativeLayout topContainer;
    WindowManager mWindowManager;
    private static final String IMAGEVIEW_TAG = "icon bitmap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag2);

        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;
        Log.e("信息","服务商名称:"+SysInfoUtils.getSimPrivatorName(this)+"--获取运营商信息:"+ SysInfoUtils.getCarrier(this)+
                "-->读取设置的应用名称:"+SysInfoUtils.getDeviceName()+"-->获得手机号:"+SysInfoUtils.getPhoneNumber(this));
        imageView = (ImageView) findViewById(R.id.img);
        imageView2 = (ImageView) findViewById(R.id.img2);
        topContainer = (RelativeLayout) findViewById(R.id.topContainer);
        imageView.setTag(IMAGEVIEW_TAG);
        imageView2.setOnTouchListener(new ImageTouchListener());
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item =new ClipData.Item((String)v.getTag());
                ClipData dragData =new ClipData((String)v.getTag(),new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN },item);
                v.startDrag(dragData, new View.DragShadowBuilder(v), imageView, 0);
                return true;
            }
        });

        //目标区域设置拖拽事件监听
        topContainer.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, final DragEvent event) {
                int action = event.getAction();
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.i("信息", "拖拽开始");
                        imageView.setVisibility(View.GONE);
                        v.invalidate();
                        return event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.i("信息", "被拖拽的view进去目标区域");
                        v.invalidate();
                        return true;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.i("信息", "被拖拽的view在目标区域移动" + "--X:" + event.getX() + "-->Y:" + event.getY());

                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        v.invalidate();
                        Log.i("信息", "被拖拽的view离开目标区域");
                        return true;
                    case DragEvent.ACTION_DROP:
                        v.invalidate();
                        Log.i("信息", topContainer.getChildCount() + "放开被拖拽的view" + "--X:" + event.getX() + "-->Y:" + event.getY());
                        ClipData.Item item = event.getClipData().getItemAt(0);
                        String content = item.getText().toString();
                        Toast.makeText(DragActivity.this, content, Toast.LENGTH_SHORT).show();
                        imageView.setTranslationX(event.getX()-150);
                        imageView.setTranslationY(event.getY()-150);
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
//                        v.invalidate();
                        Log.i("信息", "拖拽完成");
                        imageView.setVisibility(View.VISIBLE);
                        return true;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    int screenWidth;
    int screenHeight;
    int lastX;
    int lastY;


    private class ImageTouchListener2 implements View.OnTouchListener {

        @Override
        public boolean onTouch(final View v, final MotionEvent event) {
            int action = event.getAction();
            Log.i("信息", "Touch:" + action);
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    break;
                /**
                 * layout(l,t,r,b)
                 * l  Left position, relative to parent
                 t  Top position, relative to parent
                 r  Right position, relative to parent
                 b  Bottom position, relative to parent
                 * */
                case MotionEvent.ACTION_MOVE:

                    int dx = (int) event.getRawX() - lastX;
                    int dy = (int) event.getRawY() - lastY;

                    int left = v.getLeft() + dx;
                    int top = v.getTop() + dy;
                    int right = v.getRight() + dx;
                    int bottom = v.getBottom() + dy;
                    Log.i("信息", "MotionEvent.ACTION_MOVE: event.getRawX()" + (int) event.getRawX() + "  ->lastX:" + lastX +
                            ",v.getLeft(): " + v.getLeft() + ", left:" + left);
                    if (left < 0) {
                        left = 0;
                        right = left + v.getWidth();
                    }
                    if (right > screenWidth) {
                        right = screenWidth;
                        left = right - v.getWidth();
                    }
                    if (top < 0) {
                        top = 0;
                        bottom = top + v.getHeight();
                    }
                    if (bottom > screenHeight) {
                        bottom = screenHeight;
                        top = bottom - v.getHeight();
                    }
                    v.layout(left, top, right, bottom);
                    Log.e("信息", "position" + left + ", " + top + ", " + right + ", " + bottom);
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return true;
        }
    }

    private class ImageTouchListener implements View.OnTouchListener {

        //声明一个坐标点
        private PointF startPoint;
        //声明并实例化一个Matrix来控制图片
        private Matrix matrix = new Matrix();
        //声明并实例化当前图片的Matrix
        private Matrix mCurrentMatrix = new Matrix();


        //缩放时初始的距离
        private float startDistance;
        //拖拉的标记
        private static final int DRAG = 1;
        //缩放的标记
        private static final int ZOOM = 2;
        //标识记录
        private int mode;
        //缩放的中间点
        private PointF midPoint;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    System.out.println("ACTION_DOWN");
                    Log.w("信息", "ACTION_DOWN");
                    //此时处于拖拉方式下
                    mode = DRAG;
                    //获得当前按下点的坐标
                    startPoint = new PointF(event.getX(), event.getY());
                    //把当前图片的Matrix设置为按下图片的Matrix
                    mCurrentMatrix.set(imageView.getImageMatrix());
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.w("信息", "ACTION_MOVE");
                    //根据不同的模式执行相应的缩放或者拖拉操作
                    switch (mode) {
                        case DRAG:
                            //移动的x坐标的距离
                            float dx = event.getX() - startPoint.x;
                            //移动的y坐标的距离
                            float dy = event.getY() - startPoint.y;
                            //设置Matrix当前的matrix
                            matrix.set(mCurrentMatrix);
                            //告诉matrix要移动的x轴和Y轴的距离
                            matrix.postTranslate(dx, dy);
                            break;
                        case ZOOM:
                            //计算缩放的距离
                            float endDistance = distance(event);
                            //计算缩放比率
                            float scale = endDistance / startDistance;
                            //设置当前的Matrix
                            matrix.set(mCurrentMatrix);
                            //设置缩放的参数
                            matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                            break;
                        default:
                            break;
                    }

                    break;
                //已经有一个手指按住屏幕，再有一个手指按下屏幕就会触发该事件
                case MotionEvent.ACTION_POINTER_DOWN:
                    Log.w("信息", "ACTION_POINTER_DOWN");
                    //此时为缩放模式
                    mode = ZOOM;
                    //计算开始时两个点的距离
                    startDistance = distance(event);
                    //当两个点的距离大于10时才进行缩放操作
                    if (startDistance > 10) {
                        //计算中间点
                        midPoint = mid(event);
                        //得到进行缩放操作之前，照片的绽放倍数
                        mCurrentMatrix.set(imageView.getImageMatrix());
                    }
                    break;
                //已经有一个手指离开屏幕，还有手指在屏幕上时就会触发该事件
                case MotionEvent.ACTION_POINTER_UP:
                    Log.w("信息", "ACTION_POINTER_UP");
                    mode = 0;
                    break;
                case MotionEvent.ACTION_UP:
                    Log.w("信息", "ACTION_UP");
                    mode = 0;
                    break;
                default:
                    break;
            }
            //按照Matrix的要求移动图片到某一个位置
            imageView.setImageMatrix(matrix);
            //返回true表明我们会消费该动作，不需要父控件进行进一步的处理
            return true;
        }


    }

    public static float distance(MotionEvent event) {
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);

        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public static PointF mid(MotionEvent event) {
        float x = (event.getX(1) - event.getX(0)) / 2;
        float y = (event.getY(1) - event.getY(0)) / 2;
        return new PointF(x, y);
    }
}
