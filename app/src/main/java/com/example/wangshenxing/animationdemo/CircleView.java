package com.example.wangshenxing.animationdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class CircleView extends View {
    ValueAnimator circleAnimator;
    Path circlePath;
    Path needToDrawPath;
    Path previous;
    PathMeasure pathMeasure;
    float pathLength;
    float animatorValue;
    Paint paint;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        needToDrawPath.reset();
        pathMeasure.getSegment(0, pathLength * animatorValue * 3, needToDrawPath, true);
        previous = needToDrawPath;

        //画圆弧
        RectF rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawArc(rectF, animatorValue * 360 - 10, animatorValue * 360, false, paint);

        //canvas.drawPath(needToDrawPath, paint);

    }

    private void initPath() {
        circlePath = new Path();
        int[] location = new int[2];
        this.getLocationInWindow(location);
        circlePath.addCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, 180, Path.Direction.CW);
        pathMeasure = new PathMeasure();
        pathMeasure.setPath(circlePath, false);
        pathLength = pathMeasure.getLength();
        needToDrawPath = new Path();
        previous = new Path();
    }

    private void initAnimator() {
        int[] arcColors = new int[]{
                0xFF09F68C,
                0xFFB0F44B,
                0xFFE8DD30,
                0xFFF1CA2E,
                0xFFFF902F,
                0xFFFF6433,
                0xFF09F68C};
        SweepGradient sweepGradient = new SweepGradient(getMeasuredWidth() / 2, getMeasuredHeight() / 2, arcColors, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setStrokeJoin(Paint.Join.ROUND);
        LinearGradient shader = new LinearGradient(0, 0, getMeasuredWidth(), getMeasuredHeight(),
                Color.RED, Color.BLUE, Shader.TileMode.REPEAT);
        paint.setShader(sweepGradient);
        circleAnimator = ValueAnimator.ofFloat(0, 1);
        circleAnimator.setDuration(3000);


        circleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        circleAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    private void init() {
        initPath();
        initAnimator();

    }

    public void startAnimator() {
        paint.setColor(Color.RED);
        circleAnimator.start();
    }
}
