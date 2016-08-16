package cn.xmrk.weather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import cn.xmrk.rkandroid.utils.CommonUtil;
import cn.xmrk.weather.R;

/**
 * Created by Administrator on 2016/6/18.
 */
public class AqiView extends View {


    private CountDownTimer mCountDownTimer;

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
     * 进度的时间
     **/
    private int nowDrawRang;
    private long totalTime = 4000;
    private long perTime = 100;

    /**
     * 最大值和最大的角度
     **/
    private int maxAqi = 300;
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
        if (mRectF != null) {
            //先画外部的圆弧
            canvas.drawArc(mRectF, startRang, totalRang, false, mPaint);
            //在画进度的圆弧
            canvas.drawArc(mRectF, startRang, nowDrawRang, false, mCiclePaint);
        }
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


    private void setRoundColor() {
        int[] colors = {0xFF8B0000, 0xFF363636, 0xFF3CB775, 0xFFFC8447, 0xFFFF2213, 0xFFFF0101};
        SweepGradient mSweepGradient = new SweepGradient(mRectF.centerX(), mRectF.centerY(), colors, null);
        mCiclePaint.setShader(mSweepGradient);
    }

    /**
     * 计算以后再重新绘制
     **/
    public void setRang(int nowAqi, int width, int height) {
        this.nowAqi = nowAqi;
        this.mWidth = width;
        this.mHeight = height;
        //半径是高度减去上下流出的距离，然后一半
        radiu = (mHeight - topBottomPadding * 2) / 2;
        //确定rectf
        initRectF();
        //根据rectf确定颜色
        setRoundColor();
        //计算比例
        float size = ((float) nowAqi / maxAqi);
        if (size > 1f) {
            size = 1f;
        }
        //先设置为null
        nowDrawRang = 0;
        //设置totalTime,perTime为初始值
        totalTime = 4000;
        perTime = 100;
        //设置进度显示
        nowRang = (int) (size * totalRang);
        //设置显示的时间长度
        totalTime = (int) (size * totalTime);
        //最小不能小于1000毫秒
        if (totalTime < 1000) {
            totalTime = 1000;
        }
        //开始绘图
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        mCountDownTimer = new CountDownTimer(totalTime, perTime) {
            @Override
            public void onTick(long millisUntilFinished) {
                nowDrawRang = (int) (nowRang * ((double) (totalTime - millisUntilFinished) / totalTime));
                invalidate();
            }

            @Override
            public void onFinish() {
                nowDrawRang = nowRang;
                invalidate();
            }
        }.start();
    }
}
