package reactnative.ng.smc.rxjava2demo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 可拖动的view
 */

public class CustomView extends LinearLayout{
    private ViewDragHelper mDragHelper;
    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        /**
         * @params ViewGroup forParent 必须是一个ViewGroup
         * @params float sensitivity 灵敏度
         * @params Callback cb 回调
         */
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragCallback());
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {
        /**
         * 尝试捕获子view，一定要返回true
         *   尝试捕获的view
         *  int pointerId 指示器id？
         * 这里可以决定哪个子view可以拖动
         *  会传递当前触摸区域下的子View实例作为参数，如果需要对当前触摸的子View进行拖拽移动就返回true，否则返回false。
         */
        @Override
        public boolean tryCaptureView(View view, int pointerId) {
//          return mCanDragView == view;
            return true;
        }

        /**
         * 处理水平方向上的拖动
         *  View child 被拖动到view
         *  int left 移动到达的x轴的距离
         *  int dx 建议的移动的x距离
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.i("信息","left = " + left + ", dx = " + dx);

            // 两个if主要是为了让viewViewGroup里
            if(getPaddingLeft() > left) {
                return getPaddingLeft();
            }
            if(getWidth() - child.getWidth() < left) {
                return getWidth() - child.getWidth();
            }

            return left;
        }

        /**
         *  处理竖直方向上的拖动
         *  View child 被拖动到view
         *  int top 移动到达的y轴的距离
         *  int dy 建议的移动的y距离
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            // 两个if主要是为了让viewViewGroup里
            if(getPaddingTop() > top) {
                return getPaddingTop();
            }

            if(getHeight() - child.getHeight() < top) {
                return getHeight() - child.getHeight();
            }

            return top;
        }

        /**
         * 当拖拽到状态改变时回调
         * @params 新的状态
         */
        @Override
        public void onViewDragStateChanged(int state) {
            switch (state) {
                case ViewDragHelper.STATE_DRAGGING:  // 正在被拖动
                    break;
                case ViewDragHelper.STATE_IDLE:  // view没有被拖拽或者 正在进行fling/snap
                    break;
                case ViewDragHelper.STATE_SETTLING: // fling完毕后被放置到一个位置
                    break;
            }
            super.onViewDragStateChanged(state);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_DOWN:
                mDragHelper.cancel(); // 相当于调用 processTouchEvent收到ACTION_CANCEL
                break;
        }

        /**
         * 检查是否可以拦截touch事件
         * 如果onInterceptTouchEvent可以return true 则这里return true
         */
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**
         * 处理拦截到的事件
         * 这个方法会在返回前分发事件
         */
        mDragHelper.processTouchEvent(event);
        return true;
    }
}
