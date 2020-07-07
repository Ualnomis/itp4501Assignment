package com.example.itp4501assignment.utils;

import android.content.Context;
import android.graphics.Paint;

import java.math.BigDecimal;

/**
 * 作者：chs on 2016/9/8 10:02
 * 邮箱：657083984@qq.com
 * 计算 工具类
 */
public class CalculateUtil {

    public static float numMathMul(float d1, float d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        float res = b1.multiply(b2).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        return res;
    }

    /**
     * 得到最大宽度值得文本
     *
     * @param maxDivisionValue
     * @return
     */
    public static float getDivisionTextMaxWidth(float maxDivisionValue, Context context) {
        Paint textPaint = new Paint();
        textPaint.setTextSize(DensityUtil.dip2px(context, 10));
        BigDecimal bigDecimal = new BigDecimal(maxDivisionValue);
        float max = textPaint.measureText(String.valueOf(bigDecimal.intValue()));
        for (int i = 2; i <= 10; i++) {
            if (maxDivisionValue * 0.1 >= 1) {
                //当数字非常大的时候会出现精度丢失的情况 所以候使用BigDecimal做运算
                BigDecimal bd = new BigDecimal(maxDivisionValue);
                BigDecimal fen = new BigDecimal(0.1 * i);
                String text = String.valueOf(bd.multiply(fen).longValue());
                float w = textPaint.measureText(text);
                if (w > max) {
                    max = w;
                }
            } else {
                max = textPaint.measureText(String.valueOf(maxDivisionValue * 10));
            }
        }
        return max;
    }
}

