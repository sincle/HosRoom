package com.haieros.hosroom.widget.loading;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.haieros.hosroom.R;


/**
 * Created by Kang on 2017/10/24.
 */

public class RotateLoading extends View {

    private static final int DEFAULT_WIDTH = 4;
    private static final int DEFAULT_SHADOW_POSITION = 6;
    private static final int DEFAULT_DURATION = 1500;

    private int mWidth;
    private int shadowPosition;
    private Paint mPaint;
    private boolean isStart = false;
    private RectF shadowRectF;
    private RectF loadingRectF;
    private int mRadius;
    private ARC mARC;
    private ValueAnimator animator;
    private int mColor;
    private int mBackgroundColor;
    private int mDuration;

    public RotateLoading(Context context) {
        super(context);
        initView(context, null);
    }

    public RotateLoading(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public RotateLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        mARC = new ARC(0,10);
        mWidth = dpToPx(context, DEFAULT_WIDTH);
        shadowPosition = dpToPx(context, DEFAULT_SHADOW_POSITION);
        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RotateLoading);
            mColor = typedArray.getColor(R.styleable.RotateLoading_loading_color, Color.WHITE);
            mBackgroundColor = typedArray.getColor(R.styleable.RotateLoading_loading_background_color, Color.BLACK);
            mWidth = typedArray.getDimensionPixelSize(R.styleable.RotateLoading_loading_width, dpToPx(context, DEFAULT_WIDTH));
            mRadius = typedArray.getDimensionPixelSize(R.styleable.RotateLoading_loading_radius, dpToPx(context, DEFAULT_WIDTH));
            shadowPosition = typedArray.getDimensionPixelSize(R.styleable.RotateLoading_loading_shadow_position, DEFAULT_SHADOW_POSITION);
            mDuration = typedArray.getInt(R.styleable.RotateLoading_loading_duration, DEFAULT_DURATION);
            typedArray.recycle();
        }
        //本图
        loadingRectF = new RectF((mWidth +shadowPosition)/2,(mWidth +shadowPosition)/2, mRadius, mRadius);
        //阴影
        shadowRectF = new RectF((mWidth +shadowPosition)/2+shadowPosition,(mWidth +shadowPosition)/2+shadowPosition, mRadius+shadowPosition, mRadius+shadowPosition);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画阴影
        mPaint.setColor(mBackgroundColor);
        canvas.drawArc(shadowRectF, mARC.getStartDegree(), mARC.getTotalArc(), false, mPaint);
        canvas.drawArc(shadowRectF, mARC.getStartDegree()+180, mARC.getTotalArc(), false, mPaint);
        //画弧
        mPaint.setColor(mColor);
        canvas.drawArc(loadingRectF,  mARC.getStartDegree(), mARC.getTotalArc(), false, mPaint);
        canvas.drawArc(loadingRectF, mARC.getStartDegree()+180, mARC.getTotalArc(), false, mPaint);
    }

    public void start(){
        animator = ValueAnimator.ofObject(new ArcEvaluator(),new ARC(0,10),new ARC(180,150),new ARC(360,10));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mARC = (ARC) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(mDuration);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }
    public void stop(){
        animator.cancel();
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();
        int l = (right - width) / 2;
        int t = (bottom - height) / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;
        switch (widthMode) {
            //andorid:layout_width="50dp"， match_parent
            case MeasureSpec.EXACTLY :
                width = widthSize;
                break;
            //WRAP_CONTENT
            case MeasureSpec.AT_MOST:
                width = (mRadius+shadowPosition)+mWidth;
                break;
            case MeasureSpec.UNSPECIFIED:
                width = (mRadius+shadowPosition)+mWidth;
                break;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY :
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = (mRadius+shadowPosition)+mWidth;
                break;
            case MeasureSpec.UNSPECIFIED:
                height = (mRadius+shadowPosition)+mWidth;
                break;
        }
        setMeasuredDimension(width,height);
    }

    /**
     * dp 转为 px
     *
     * @param context
     * @param distance
     * @return
     */
    private int dpToPx(Context context, int distance) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, distance, context.getResources().getDisplayMetrics());
    }

}
