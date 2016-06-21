package cn.xmrk.weather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import cn.xmrk.rkandroid.utils.CommonUtil;
import cn.xmrk.weather.R;

/**
 * Created by Administrator on 2016/6/18.
 */
public class AqiView extends View {

    /**
     * 圆的外部矩形框
     **/
    private RectF mRectF;

    /**
     * 圆的半径
     **/
    private float radiu;

    /**
     * 画图的画笔
     **/
    private Paint mPaint;
    /**
     * 画图的画笔
     **/
    private Paint mCiclePaint;

    private float mWidth;
    private float mHeight;
    private float topBottomPadding;

    private int paintWidth;

    /**
     * 当前值和当前的角度
     **/
    private int nowAqi;
    private int nowRang;


    /**
     * 最大值和最大的角度
     **/
    private int maxAqi = 200;
    private int totalRang = 300;

    private int startRang = -240;

    public AqiView(Context context) {
        super(context);
        init();
    }

    public AqiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintWidth = CommonUtil.dip2px(5);
        topBottomPadding = CommonUtil.dip2px(15);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先画外部的圆弧
        canvas.drawArc(mRectF, startRang, totalRang, false, mPaint);
        //再画里面的圆弧
        canvas.drawArc(mRectF, startRang, nowRang, false, mCiclePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getWidth();
        mHeight = getHeight();
        //半径是高度减去上下流出的距离，然后一半
        radiu = (mHeight - topBottomPadding * 2) / 2;
        initRectF();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        //设置笔刷为圆形样式
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(getResources().getColor(R.color.bg_grey));
        mPaint.setStrokeWidth(paintWidth);

//-----------------------------------------------
        mCiclePaint = new Paint();
        mCiclePaint.setStyle(Paint.Style.STROKE);
        mCiclePaint.setAntiAlias(true);
        mPaint.setDither(true);
        //设置笔刷为圆形样式
        mCiclePaint.setStrokeCap(Paint.Cap.ROUND);
        mCiclePaint.setColor(getResources().getColor(R.color.bg_title_bar));
        mCiclePaint.setStrokeWidth(paintWidth);

        //关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    private void initRectF() {
        mRectF = new RectF();
        mRectF.set(mWidth / 2 - radiu, topBottomPadding, mWidth / 2 + radiu, mHeight - topBottomPadding);
    }

    /**
     * 计算以后再重新绘制
     **/
    public void setRang(int nowAqi) {
        this.nowAqi = nowAqi;
        nowRang = (int) (((float) nowAqi / maxAqi) * totalRang);
        invalidate();
    }
}
