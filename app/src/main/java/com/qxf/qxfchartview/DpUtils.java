package com.qxf.qxfchartview;

import android.content.Context;

public class DpUtils {
    /**
     * dp转换成px
     */
    public static float dp2px(Context context, float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (dpValue*scale+0.5f);
    }

    /**
     * px转换成dp
     */
    public static float px2dp(Context context,float pxValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (pxValue/scale+0.5f);
    }
    /**
     * sp转换成px
     */
    public static float sp2px(Context context,float spValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (spValue*fontScale+0.5f);
    }
    /**
     * px转换成sp
     */
    public static float px2sp(Context context,float pxValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (pxValue/fontScale+0.5f);
    }
}
