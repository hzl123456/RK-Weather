package cn.xmrk.weather.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bigkoo.quicksidebar.listener.OnQuickSideBarTouchListener;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Au61 on 2016/6/15.
 * 这个库的QuickSideBarView感觉有点问题就自己写了个
 */
public class MyQuickSideBarView extends View {
    private OnQuickSideBarTouchListener listener;
    private List<String> mLetters;
    private int mChoose = -1;
    private Paint mPaint = new Paint();
    private float mTextSize;
    private float mTextSizeChoose;
    private int mTextColor;
    private int mTextColorChoose;
    private int mWidth;
    private int mHeight;
    private int mItemHeight;
    private int mPaddingTop;//顶部留着间距

    public MyQuickSideBarView(Context context) {
        this(context, null);
    }

    public MyQuickSideBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyQuickSideBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mLetters = Arrays.asList(context.getResources().getStringArray(com.bigkoo.quicksidebar.R.array.quickSideBarLetters));

        mTextColor = context.getResources().getColor(android.R.color.black);
        mTextColorChoose = context.getResources().getColor(android.R.color.black);
        mTextSize = context.getResources().getDimensionPixelSize(com.bigkoo.quicksidebar.R.dimen.textSize_quicksidebar);
        mPaddingTop = context.getResources().getDimensionPixelSize(com.bigkoo.quicksidebar.R.dimen.height_quicksidebartips);
        mTextSizeChoose = context.getResources().getDimensionPixelSize(com.bigkoo.quicksidebar.R.dimen.textSize_quicksidebar_choose);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, com.bigkoo.quicksidebar.R.styleable.QuickSideBarView);

            mTextColor = a.getColor(com.bigkoo.quicksidebar.R.styleable.QuickSideBarView_sidebarTextColor, mTextColor);
            mTextColorChoose = a.getColor(com.bigkoo.quicksidebar.R.styleable.QuickSideBarView_sidebarTextColorChoose, mTextColorChoose);
            mTextSize = a.getFloat(com.bigkoo.quicksidebar.R.styleable.QuickSideBarView_sidebarTextSize, mTextSize);
            mTextSizeChoose = a.getFloat(com.bigkoo.quicksidebar.R.styleable.QuickSideBarView_sidebarTextSizeChoose, mTextSizeChoose);
            a.recycle();
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mLetters.size(); i++) {
            mPaint.setColor(mTextColor);

            mPaint.setAntiAlias(true);
            mPaint.setTextSize(mTextSize);
            if (i == mChoose) {
                mPaint.setColor(mTextColorChoose);
                mPaint.setFakeBoldText(true);
                mPaint.setTypeface(Typeface.DEFAULT_BOLD);
                mPaint.setTextSize(mTextSizeChoose);
            }


            //计算位置
            Rect rect = new Rect();
            mPaint.getTextBounds(mLetters.get(i), 0, mLetters.get(i).length(), rect);
            float xPos = (int) ((mWidth - rect.width()) * 0.5);
            float yPos = mItemHeight * i + (int) ((mItemHeight - rect.height()) * 0.5) + mPaddingTop;


            canvas.drawText(mLetters.get(i), xPos, yPos, mPaint);
            mPaint.reset();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getHeight() - mPaddingTop;
        mWidth = getWidth();
        mItemHeight = mHeight / (mLetters.size() == 0 ? 1 : mLetters.size());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = mChoose;
        final int newChoose = (int) (y / (mHeight + mPaddingTop) * mLetters.size());

        switch (action) {
            case MotionEvent.ACTION_UP:
                mChoose = -1;
                if (listener != null) {
                    listener.onLetterTouching(false);
                }
                invalidate();
                break;
            default:
                if (oldChoose != newChoose) {
                    if (newChoose >= 0 && newChoose < mLetters.size()) {
                        mChoose = newChoose;
                        if (listener != null) {
                            listener.onLetterChanged(mLetters.get(newChoose), mChoose, mItemHeight);
                        }
                    }
                    invalidate();
                }
                //如果是cancel也要调用onLetterUpListener 通知
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    if (listener != null) {
                        listener.onLetterTouching(false);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {//按下调用 onLetterDownListener
                    if (listener != null) {
                        listener.onLetterTouching(true);
                    }
                }

                break;
        }
        return true;
    }

    public OnQuickSideBarTouchListener getListener() {
        return listener;
    }

    public void setOnQuickSideBarTouchListener(OnQuickSideBarTouchListener listener) {
        this.listener = listener;
    }

    public List<String> getLetters() {
        return mLetters;
    }

    public void setLetters(List<String> letters) {
        this.mLetters = letters;
        invalidate();
    }
}
