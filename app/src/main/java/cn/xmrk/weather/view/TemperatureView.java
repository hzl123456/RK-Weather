package cn.xmrk.weather.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import cn.xmrk.rkandroid.utils.CommonUtil;
import cn.xmrk.weather.R;

/**
 * Created by Au61 on 2016/6/16.
 * 画温度折线图的view
 */
public class TemperatureView extends View {


    /**
     * 文字在上还是下，默认在下
     **/
    private boolean textDown;

    /**
     * 温度信息
     **/
    private int[] mTemperature;
    private int maxTemperature;
    private int minTemperature;

    private int mWidth;
    private int mHeight;

    private float mItemHeight;
    private float mItemWidth;
    /**
     * 上下留出的距离
     **/
    private int topBottomPadding;

    /**
     * 左右留出的距离
     **/
    private int leftRightPadding;

    /**
     * 画图的画笔
     **/
    private Paint mPaint;

    /**
     * 透明圆点的画笔
     **/
    private Paint roundPaint;
    /**
     * 文字的画笔
     **/
    private Paint textPaint;
    /**
     * 画笔的粗细
     **/
    private int paintWidth;


    public TemperatureView(Context context) {
        this(context, null);
    }

    public TemperatureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setmTemperature(int[] mTemperature, int width, int height) {
        this.mTemperature = mTemperature;
        this.mWidth = width;
        this.mHeight = height;
        chooseMaxMinTemperature();
        getWidthHeight();
        invalidate();
    }

    public TemperatureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
        initPaint();
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TemperatureView);
            paintWidth = a.getDimensionPixelSize(R.styleable.TemperatureView_TemperatureViewWidth, paintWidth);
            topBottomPadding = a.getDimensionPixelSize(R.styleable.TemperatureView_TemperatureViewPadding, topBottomPadding);
            leftRightPadding = a.getDimensionPixelSize(R.styleable.TemperatureView_TemperatureViewPaddingLR, leftRightPadding);
            textDown = a.getBoolean(R.styleable.TemperatureView_TemperatureViewTextDown, textDown);
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTemperature != null && mTemperature.length > 0) {
            Path mPath = new Path();
            float x;
            float y;
            //画圆点
            for (int i = 0; i < mTemperature.length; i++) {
                x = leftRightPadding + mItemWidth * i;
                y = topBottomPadding + mItemHeight * (Math.abs(mTemperature[i] - maxTemperature));
                //外部透明圆点
                canvas.drawCircle(x, y, paintWidth * 2f, roundPaint);
                //中间实心圆点
                canvas.drawCircle(x, y, paintWidth * 0.8f, mPaint);
                //写文字
                canvas.drawText(mTemperature[i] + "°", x, textDown ? y + topBottomPadding : y - topBottomPadding / 2, textPaint);
                if (i == 0) {
                    mPath.moveTo(x, y);
                } else {
                    mPath.lineTo(x, y);
                    mPath.moveTo(x, y);
                }
            }
            //把折线路径画上去
            canvas.drawPath(mPath, mPaint);
        }
    }

    /**
     * 计算高度
     **/
    private void getWidthHeight() {
        mItemHeight = ((float) (mHeight - topBottomPadding * 2)) / (maxTemperature - minTemperature == 0 ? 1 : maxTemperature - minTemperature);
        mItemWidth = (float) (mWidth - leftRightPadding * 2) / (mTemperature.length - 1);
    }

    /**
     * 选择出最大和最小的温度
     **/
    private void chooseMaxMinTemperature() {
        //赋予第一个值为最大或者最小
        maxTemperature = mTemperature[0];
        minTemperature = mTemperature[0];
        for (int i = 0; i < mTemperature.length; i++) {
            maxTemperature = Math.max(maxTemperature, mTemperature[i]);
            minTemperature = Math.min(minTemperature, mTemperature[i]);
        }
    }

    /**
     * 实例化画笔
     **/
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        //设置笔刷为圆形样式
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(getResources().getColor(R.color.bg_title_bar));
        mPaint.setStrokeWidth(paintWidth);
//---------------------------------------------------------------------------
        roundPaint = new Paint();
        roundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        roundPaint.setAntiAlias(true);
        //设置笔刷为圆形样式
        roundPaint.setStrokeCap(Paint.Cap.ROUND);
        roundPaint.setColor(getResources().getColor(R.color.bg_title_bar_ff));
        roundPaint.setStrokeWidth(paintWidth);
//---------------------------------------------------------------------------
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(getResources().getColor(R.color.bg_title_bar));
        textPaint.setTextSize(CommonUtil.dip2px(10));
        textPaint.setTextAlign(Paint.Align.CENTER);
    }
}
