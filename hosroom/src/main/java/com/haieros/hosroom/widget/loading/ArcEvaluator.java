package com.haieros.hosroom.widget.loading;

import android.animation.TypeEvaluator;

/**
 * 转换器
 * Created by Kang on 2017/10/25.
 */

class ArcEvaluator implements TypeEvaluator<ARC> {
    private static final String TAG = ArcEvaluator.class.getSimpleName();
    /**
     * This function returns the result of linearly interpolating the start and end values, with
     * <code>fraction</code> representing the proportion between the start and end values. The
     * calculation is a simple parametric calculation: <code>result = x0 + t * (x1 - x0)</code>,
     * where <code>x0</code> is <code>startValue</code>, <code>x1</code> is <code>endValue</code>,
     * and <code>t</code> is <code>fraction</code>.
     *
     * @param fraction   The fraction from the starting to the ending values
     * @param startValue The start value.
     * @param endValue   The end value.
     * @return A linear interpolation between the start and end values, given the
     * <code>fraction</code> parameter.
     */
    @Override
    public ARC evaluate(float fraction, ARC startValue, ARC endValue) {

        //开始位置
        int startDegree = startValue.getStartDegree();
        int startTotal = startValue.getTotalArc();
        //结束位置
        int endDegree = endValue.getStartDegree();
        int endTotal = endValue.getTotalArc();


        int currentTotal = (int) (startTotal + fraction * (endTotal - startTotal));
        int currentStart = (int) (startDegree + fraction * (endDegree-startDegree) - currentTotal*2/3);
        return new ARC(currentStart,currentTotal);
    }
}
