package cn.xmrk.weather.view;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Au61 on 2016/6/17.
 */
public class ChildViewPager extends ViewPager {

    /**
     * 触摸时按下的点
     **/
    private PointF downP = new PointF();
    /**
     * 触摸时当前的点
     **/
    private PointF curP = new PointF();

    private boolean isScrollEnable = true;

    public void setScrollEnable(boolean enable) {
        this.isScrollEnable = enable;
    }

    public boolean isScrollEnable() {
        return isScrollEnable;
    }


    public ChildViewPager(Context context) {
        super(context);
    }

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //说明将onTouch拦截在此控件，进而执行此控件的onTouchEvent
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        //每次进行onTouch事件都记录当前的按下的坐标
        curP.x = arg0.getX();
        curP.y = arg0.getY();

        if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
            //记录按下时候的坐标
            //切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
            downP.x = arg0.getX();
            downP.y = arg0.getY();
            //此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        if (arg0.getAction() == MotionEvent.ACTION_MOVE && isScrollEnable) {
            // 纵向滑动距离大，就是竖滑，竖滑父控件处理
            int distanceX = (int) Math.abs(downP.x - curP.x);
            int distanceY = (int) Math.abs(downP.y - curP.y);
            if (distanceY > distanceX) {
                getParent().requestDisallowInterceptTouchEvent(false);
            } else {
                //此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        }
        if (arg0.getAction() == MotionEvent.ACTION_UP) {
            // if(downP.x==curP.x && downP.y==curP.y){
            if (Math.abs(downP.x - curP.x) < 5 && Math.abs(downP.y - curP.y) < 5) {//做了小小的修改
                return true;
            }
        }
        if (isScrollEnable) {
            return super.onTouchEvent(arg0);
        } else {
            int distanceX = (int) Math.abs(downP.x - curP.x);
            int distanceY = (int) Math.abs(downP.y - curP.y);
            if (distanceY > distanceX) {// 纵向滑动距离大，就是竖滑，竖滑父控件处理
                getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            } else {
                return true;
            }
        }
    }

}
