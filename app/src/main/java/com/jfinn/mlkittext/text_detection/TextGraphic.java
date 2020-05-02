package com.jfinn.mlkittext.text_detection;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.jfinn.mlkittext.misc.GraphicOverlay;

public class TextGraphic extends GraphicOverlay.Graphic {
    private int id;

    private static final int TEXT_COLOR = Color.WHITE;
    private static final int RECT_COLOR = Color.parseColor("#01737f");
    private static final float TEXT_SIZE = 54.0f;
    private static final float STROKE_WIDTH = 4.0f;

    private final Paint rectPaint;
    private final Paint textPaint;
    private final String text;
    private final Rect bounding;

    public TextGraphic(GraphicOverlay overlay, String text, Rect bounding) {
        super(overlay);

        this.text = text;
        this.bounding = bounding;

        rectPaint = new Paint();
        rectPaint.setColor(RECT_COLOR);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(STROKE_WIDTH);

        textPaint = new Paint();
        textPaint.setColor(TEXT_COLOR);
        textPaint.setTextSize(TEXT_SIZE);

        // redraw overlay, graphic has been added.
        postInvalidate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextBlock() {
        return this.text;
    }

    public boolean contains(float x, float y) {
        String text = this.text;
        if (text == null) {
            return false;
        }

        RectF rect = new RectF(this.bounding);
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);
        return (rect.left < x && rect.right > x && rect.top < y && rect.bottom > y);
    }

    @Override
    public void draw(Canvas canvas) {
        if (text == null) {
            throw new IllegalStateException("Attempting to draw a null text.");
        }

        // draw the bounding box around text
        RectF rect = new RectF(this.bounding);
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);

        canvas.drawRect(rect, rectPaint);
        canvas.drawText(text, rect.left, rect.bottom, textPaint);
    }
}
