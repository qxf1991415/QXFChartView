package com.qxf.qxfchartview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class ChartView extends View {

    private Paint backPaint;
    private Paint charPaint;
    private Paint textPaint;
    private List<Integer> data;
    private int width;
    private int height;
    private int morningColor;
    private int nightColor;
    private int weekColor;
    private int morningTextColor;
    private Context context;
    private float charWidth;
    private float rxy;
    private float translate;
    private float charTextSize;
    private float leftMargin;
    private float topMargin;
    private String[] weekList = {"一", "二", "三", "四", "五", "六", "日"};

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.history);
        morningColor = typedArray.getColor(R.styleable.history_morningColor, Color.parseColor("#2283E2"));
        nightColor = typedArray.getColor(R.styleable.history_nightColor, Color.parseColor("#A5D2FF"));
        weekColor = typedArray.getColor(R.styleable.history_weekColor, Color.parseColor("#888888"));
        morningTextColor = typedArray.getColor(R.styleable.history_morningTextColor, Color.parseColor("#FFFFFF"));
        typedArray.recycle();
        init();
    }

    private void init() {
        backPaint = new Paint();
        charPaint = new Paint();
        textPaint = new Paint();

        backPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        backPaint.setColor(Color.WHITE);
        backPaint.setAntiAlias(true);

        charPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        charPaint.setAntiAlias(true);

        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        charWidth = DpUtils.dp2px(context, 22);
        rxy = DpUtils.dp2px(context, 11f);
        translate = DpUtils.dp2px(context, 42);
        canvas.translate(translate, height / 2);
        darwBack(canvas);
        drawMoringAndNight(canvas);
        drawWeek(canvas);
        drawLine(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void drawLine(Canvas canvas) {
        canvas.translate(-translate, 0f);
        charPaint.setColor(morningColor);
        charPaint.setStrokeWidth(2f);
        canvas.drawLine(0f, 0f, width, 0f, charPaint);

    }

    private void darwBack(Canvas canvas) {
        float charHeight = DpUtils.dp2px(context, 157f);
        for (int i = 0; i < 14; i++) {
            if (i % 2 == 0) {
                RectF rect = new RectF(i * charWidth, -charHeight, (i + 1) * charWidth, 0f);
                canvas.drawRect(rect, backPaint);
                canvas.drawCircle((i + 0.5f) * charWidth, -charHeight, rxy, backPaint);
            } else {
                RectF rect = new RectF((i - 1) * charWidth, 0f, i * charWidth, charHeight);
                canvas.drawRect(rect, backPaint);
                canvas.drawCircle((i - 0.5f) * charWidth, charHeight, rxy, backPaint);
            }
        }
    }


    private void drawMoringAndNight(Canvas canvas) {
        charTextSize = DpUtils.sp2px(context, 15f);
        leftMargin = DpUtils.dp2px(context, 3f);
        topMargin = DpUtils.dp2px(context, 16f);
        for (int i = 0; i < 14; i++) {
            if (i < data.size()) {
                if (i % 2 == 0) {
                    drawMoring(i, canvas);
                } else {
                    drawNight(i, canvas);
                }
            } else {
                if (i % 2 == 0) {
                    charPaint.setColor(morningColor);
                    RectF rect = new RectF(i * charWidth, -rxy, (i + 1) * charWidth, rxy);
                    canvas.drawArc(rect, 180, 180, true, charPaint);
                } else {
                    charPaint.setColor(nightColor);
                    RectF rect = new RectF((i - 1) * charWidth, -rxy, i * charWidth, rxy);
                    canvas.drawArc(rect, 0, 180, true, charPaint);
                }
            }
        }
    }

    private void drawMoring(int i, Canvas canvas) {
        charPaint.setColor(morningColor);
        int score = data.get(i);
        float charHeight = DpUtils.dp2px(context, 168);
        float factHeight = charHeight * score / 100f;
        RectF rect = new RectF(i * charWidth, -factHeight + rxy, (i + 1) * charWidth, 0f);
        canvas.drawRect(rect, charPaint);
        canvas.drawCircle((i + 0.5f) * charWidth, -factHeight + rxy, rxy, charPaint);

        textPaint.setColor(morningTextColor);
        textPaint.setTextSize(charTextSize);
        canvas.drawText(String.valueOf(score), i * charWidth + leftMargin, -factHeight + rxy + topMargin, textPaint);
    }

    private void drawNight(int i, Canvas canvas) {
        charPaint.setColor(nightColor);
        int score = data.get(i);
        float charHeight = DpUtils.dp2px(context, 168);
        float factHeight = charHeight * score / 100f;
        RectF rect = new RectF((i - 1) * charWidth, 0f, i * charWidth, factHeight - rxy);
        canvas.drawRect(rect, charPaint);
        canvas.drawCircle((i - 0.5f) * charWidth, factHeight - rxy, rxy, charPaint);

        textPaint.setColor(morningColor);
        textPaint.setTextSize(charTextSize);
        canvas.drawText(String.valueOf(score), (i - 1) * charWidth + leftMargin, factHeight - topMargin, textPaint);
    }

    private void drawWeek(Canvas canvas) {
        float charHeight = DpUtils.dp2px(context, 168f);
        float topMarginWeek = DpUtils.dp2px(context, 35f);
        textPaint.setColor(weekColor);
        textPaint.setTextSize(charTextSize);
        for (int i = 0; i < weekList.length; i++) {
            canvas.drawText(weekList[i], i * 2 * charWidth + leftMargin, charHeight + topMarginWeek, textPaint);
        }
    }

    public void setList(List<Integer> data) {
        this.data = data;
        postInvalidate();
    }
}
