package com.example.itp4501assignment.widget;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.itp4501assignment.R;
import com.example.itp4501assignment.entity.BarChartEntity;
import com.example.itp4501assignment.utils.CalculateUtil;
import com.example.itp4501assignment.utils.DensityUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * using canvas and customize view to display bar chart
 *
 */
public class BarChart extends View {
    private Context mContext;

    // size of the bar chart
    private int mTotalWidth, mTotalHeight, mMaxHeight;
    private int mPaddingRight, mPaddingBottom, mPaddingTop;

    private int[] mBarColors; // color of the bar chart
    private int mBottomMargin;
    private int mTopMargin;
    private int mRightMargin;
    private int mLeftMargin;

    private Paint mAxisPaint, mTextPaint, mBarPaint, mBorderPaint, mUnitPaint;
    private List<BarChartEntity> mData;

    private float mMaxYValue; // max value of y axis

    private float mMaxYDivisionValue; // max value of y axis unit

    private Rect mBarRect, mBarRectClick;

    private RectF mDrawArea; // draw area

    private int mBarWidth; // width of the bar

    private int mBarSpace; // space between two bar

    private float mLeftMoving;

    private float mLastPointX;

    private float mMovingThisTime = 0.0f;

    private int mMaxRight, mMinRight;

    private float mStartX;
    private int mStartY;

    private List<Integer> mBarLeftXPoints = new ArrayList<>();
    private List<Integer> mBarRightXPoints = new ArrayList<>();

    // user click the invalid position
    public static final int INVALID_POSITION = -1;
    private GestureDetector mGestureListener;

    private boolean isDrawBorder;

    private int mClickPosition; // click position

    //滑动速度相关
    private VelocityTracker mVelocityTracker;
    private Scroller mScroller;

    private int mMaxVelocity;
    private String mUnitX;
    private String mUnitY;



    public BarChart(Context context) {
        super(context);
        init(context);
    }

    public BarChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BarChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mBarWidth = DensityUtil.dip2px(getContext(), 20);
        mBarSpace = DensityUtil.dip2px(getContext(), 20);
        mTopMargin = DensityUtil.dip2px(getContext(), 20);
        mBottomMargin = DensityUtil.dip2px(getContext(), 30);
        mRightMargin = DensityUtil.dip2px(getContext(), 40);
        mLeftMargin = DensityUtil.dip2px(getContext(), 10);

        mScroller = new Scroller(context);
        mMaxVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();

        mAxisPaint = new Paint();
        mAxisPaint.setColor(ContextCompat.getColor(mContext, R.color.axis));
        mAxisPaint.setStrokeWidth(1);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(DensityUtil.dip2px(getContext(), 10));

        mUnitPaint = new Paint();
        mUnitPaint.setAntiAlias(true);
        Typeface typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
        mUnitPaint.setTypeface(typeface);
        mUnitPaint.setTextSize(DensityUtil.dip2px(getContext(), 10));

        mBarPaint = new Paint();
        mBarPaint.setColor(mBarColors != null && mBarColors.length > 0 ? mBarColors[0] : Color.parseColor("#6FC5F4"));

        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.FILL);
        mBorderPaint.setColor(Color.rgb(0, 0, 0));
        mBorderPaint.setAlpha(120);

        mBarRect = new Rect(0, 0, 0, 0);
        mBarRectClick = new Rect(0, 0, 0, 0);
        mDrawArea = new RectF(0, 0, 0, 0);
    }

    public void setData(List<BarChartEntity> list, int colors[], String mUnitX, String mUnitY) {
        this.mData = list;
        this.mBarColors = colors;
        this.mUnitX = mUnitX;
        this.mUnitY = mUnitY;
        this.mMaxYValue = 5;
        setmMaxYValue(mMaxYValue);
    }

    // set the Max Y axis Value
    private void setmMaxYValue(float mMaxYValue) {
        mMaxYDivisionValue = (float) (mMaxYValue);
        mStartX = CalculateUtil.getDivisionTextMaxWidth(mMaxYDivisionValue, mContext) + 20;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mMaxHeight = h - getPaddingTop() - getPaddingBottom() - mBottomMargin - mTopMargin;
        mPaddingBottom = getPaddingBottom();
        mPaddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        mPaddingRight = getPaddingRight();

    }


    private void getArea() {
        mMaxRight = (int) (mStartX + (mBarSpace + mBarWidth) * mData.size());
        mMinRight = mTotalWidth - mLeftMargin - mRightMargin;
        mStartY = mTotalHeight - mBottomMargin - mPaddingBottom;
        mDrawArea = new RectF(mStartX, mPaddingTop, mTotalWidth - mPaddingRight - mRightMargin, mTotalHeight - mPaddingBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mData == null || mData.isEmpty()) return;
        getArea();
        checkTheLeftMoving();
        // draw the scale line and unit of yaxis
        drawScaleLine(canvas);
        // set the unit on top of y axis and end of the x axis
        drawUnit(canvas);
        canvas.clipRect(mDrawArea.left, mDrawArea.top, mDrawArea.right, mDrawArea.bottom + mDrawArea.height());
        // draw bar
        drawBar(canvas);
        // draw x axis text
        drawXAxisText(canvas);
    }

    private void drawUnit(Canvas canvas) {
        String textLength = mMaxYDivisionValue % 5 == 0 ? String.valueOf((int) mMaxYDivisionValue) : String.valueOf(mMaxYDivisionValue);
        canvas.drawText(mUnitY, mStartX - mTextPaint.measureText(textLength), mTopMargin / 2, mUnitPaint);
        canvas.drawText(mUnitX, mTotalWidth - mRightMargin - mPaddingRight + 10, mTotalHeight - mBottomMargin / 2, mUnitPaint);
    }

    private void checkTheLeftMoving() {
        if (mLeftMoving > (mMaxRight - mMinRight)) {
            mLeftMoving = mMaxRight - mMinRight;
        }
        if (mLeftMoving < 0) {
            mLeftMoving = 0;
        }
    }

    private void drawXAxisText(Canvas canvas) {

        for (int i = 0; i < mData.size(); i++) {
            String text = mData.get(i).getxLabel();
            if (text.length() <= 3) {
                canvas.drawText(text, mBarLeftXPoints.get(i) - (mTextPaint.measureText(text) - mBarWidth) / 2, mTotalHeight - mBottomMargin * 2 / 3, mTextPaint);
            } else {
                String text1 = text.substring(0, 3);
                String text2 = text.substring(3, text.length());
                canvas.drawText(text1, mBarLeftXPoints.get(i) - (mTextPaint.measureText(text1) - mBarWidth) / 2, mTotalHeight - mBottomMargin * 2 / 3, mTextPaint);
                canvas.drawText(text2, mBarLeftXPoints.get(i) - (mTextPaint.measureText(text2) - mBarWidth) / 2, mTotalHeight - mBottomMargin / 3, mTextPaint);
            }
        }
    }

    private float percent = 1f;
    private TimeInterpolator pointInterpolator = new DecelerateInterpolator();

    public void startAnimation() {
        ValueAnimator mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setDuration(2000);
        mAnimator.setInterpolator(pointInterpolator);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                percent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnimator.start();
    }

    private void drawBar(Canvas canvas) {
        mBarLeftXPoints.clear();
        mBarRightXPoints.clear();
        mBarRect.bottom = mStartY;
        for (int i = 0; i < mData.size(); i++) {
            if (mBarColors.length == 1) {
                mBarRect.left = (int) (mStartX + mBarWidth * i + mBarSpace * (i + 1) - mLeftMoving);
                mBarRect.top = mStartY - (int) ((mMaxHeight * (mData.get(i).getyValue()[0] / mMaxYDivisionValue)) * percent);
                mBarRect.right = mBarRect.left + mBarWidth;
                canvas.drawRect(mBarRect, mBarPaint);
            } else {
                int eachHeight = 0;
                mBarRect.left = (int) (mStartX + mBarWidth * i + mBarSpace * (i + 1) - mLeftMoving);
                mBarRect.right = mBarRect.left + mBarWidth;
                for (int j = 0; j < mBarColors.length; j++) {
                    mBarPaint.setColor(mBarColors[j]);
                    mBarRect.bottom = (int) (mStartY - eachHeight * percent);
                    eachHeight += (int) ((mMaxHeight * (mData.get(i).getyValue()[j] / mMaxYDivisionValue)));
                    mBarRect.top = (int) (mBarRect.bottom - ((mMaxHeight * (mData.get(i).getyValue()[j] / mMaxYDivisionValue))) * percent);
                    canvas.drawRect(mBarRect, mBarPaint);
                }
            }
            mBarLeftXPoints.add(mBarRect.left);
            mBarRightXPoints.add(mBarRect.right);
        }
        if (isDrawBorder) {
            drawBorder(mClickPosition);
            canvas.drawRect(mBarRectClick, mBorderPaint);
        }
    }

    private void drawBorder(int position) {
        mBarRectClick.left = (int) (mStartX + mBarWidth * position + mBarSpace * (position + 1) - mLeftMoving);
        mBarRectClick.right = mBarRectClick.left + mBarWidth;
        mBarRectClick.bottom = mStartY;
        mBarRectClick.top = mStartY - (int) (mMaxHeight * (mData.get(position).getSum() / mMaxYDivisionValue));
    }


    private void drawScaleLine(Canvas canvas) {
        float eachHeight = (mMaxHeight / 5);
        float textValue = 0;
        if (mMaxYValue > 1) {
            for (int i = 0; i <= 5; i++) {
                float startY = mStartY - eachHeight * i;
                BigDecimal maxValue = new BigDecimal(mMaxYDivisionValue);
                BigDecimal fen = new BigDecimal(0.2 * i);
                String text = null;
                if (mMaxYDivisionValue % 5 != 0) {
                    text = String.valueOf(maxValue.multiply(fen).floatValue());
                } else {
                    text = String.valueOf(maxValue.multiply(fen).longValue());
                }
                canvas.drawText(text, mStartX - mTextPaint.measureText(text) - 5, startY + mTextPaint.measureText("0") / 2, mTextPaint);
                canvas.drawLine(mStartX, startY, mTotalWidth - mPaddingRight - mRightMargin, startY, mAxisPaint);
            }
        } else if (mMaxYValue > 0 && mMaxYValue <= 1) {
            for (int i = 0; i <= 5; i++) {
                float startY = mStartY - eachHeight * i;
                textValue = CalculateUtil.numMathMul(mMaxYDivisionValue, (float) (0.2 * i));
                String text = String.valueOf(textValue);
                canvas.drawText(text, mStartX - mTextPaint.measureText(text) - 5, startY + mTextPaint.measureText("0") / 2, mTextPaint);
                canvas.drawLine(mStartX, startY, mTotalWidth - mPaddingRight - mRightMargin, startY, mAxisPaint);
            }
        } else {
            for (int i = 0; i <= 5; i++) {
                float startY = mStartY - eachHeight * i;
                String text = String.valueOf(10 * i);
                canvas.drawText(text, mStartX - mTextPaint.measureText(text) - 5, startY + mTextPaint.measureText("0") / 2, mTextPaint);
                canvas.drawLine(mStartX, startY, mTotalWidth - mPaddingRight - mRightMargin, startY, mAxisPaint);
            }
        }
    }

    private void initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    // calculate the scrool
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mMovingThisTime = (mScroller.getCurrX() - mLastPointX);
            mLeftMoving = mLeftMoving + mMovingThisTime;
            mLastPointX = mScroller.getCurrX();
            postInvalidate();
        }
    }


    @Override
    // when user touch the screen
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastPointX = event.getX();
                mScroller.abortAnimation();// stop animation
                initOrResetVelocityTracker();
                mVelocityTracker.addMovement(event);// put user movement to tracker
                break;
            case MotionEvent.ACTION_MOVE:
                float movex = event.getX();
                mMovingThisTime = mLastPointX - movex;
                mLeftMoving = mLeftMoving + mMovingThisTime;
                mLastPointX = movex;
                invalidate();
                mVelocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.addMovement(event);
                mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                int initialVelocity = (int) mVelocityTracker.getXVelocity();
                mVelocityTracker.clear();
                mScroller.fling((int) event.getX(), (int) event.getY(), -initialVelocity / 2,
                        0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
                invalidate();
                mLastPointX = event.getX();
                break;
            case MotionEvent.ACTION_CANCEL:
                recycleVelocityTracker();
                break;
            default:
                return super.onTouchEvent(event);
        }
        if (mGestureListener != null) {
            mGestureListener.onTouchEvent(event);
        }
        return true;
    }

}