package com.example.testing.cropcamera.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/4/6.
 */

public class CameraLayerView extends View {

    private int         mWidth;
    private int         mHeight;

    //显示区域大小
    private int         mVisibleWidth;
    private int         mVisibleHeight;
    private int         mVisiblePadding;

    private Rect        mVisibleRect;   //可视的区域大小
    private int         mDimmedColor = 0x7c000000;   //模糊默认颜色
    private int         mLineColor = Color.WHITE;
    private Paint       mBoundPaint;
    private int         mMarginBottom = 260;        //调节框的位置，(height-marginBottom)/2居中
    private int         mLineWidth = 4;

    public CameraLayerView(Context context) {
        super(context);
    }

    public CameraLayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraLayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDimmedSize(int width, int height) {
        mWidth = width;
        mHeight = height - mMarginBottom;
    }

    public void setVisibleSize(int width, int height, int padding) {
        mVisiblePadding = padding;
        mVisibleWidth = mWidth - 2 * mVisiblePadding;
        mVisibleHeight = mVisibleWidth * height / width;
        createVisibleArea();
    }

    public void setLineColor(int color) {
        mLineColor = color;
    }

    public void setLineWidth(int width) {
        mLineWidth = width;
    }

    private void createVisibleArea() {
        mVisibleRect = new Rect();
        mVisibleRect.left = mVisiblePadding;
        mVisibleRect.right = mVisibleRect.left + mVisibleWidth;
        mVisibleRect.top = (mHeight - mVisibleHeight) / 2;
        mVisibleRect.bottom = mVisibleRect.top + mVisibleHeight;

        //set drawing paint
        mBoundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoundPaint.setColor(mLineColor);
        mBoundPaint.setStyle(Paint.Style.STROKE);
        mBoundPaint.setStrokeWidth(mLineWidth);
    }

    public Rect getVisibleRect() {
        return mVisibleRect;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawDimmedLayer(canvas);
        drawBound(canvas);
    }

    private void drawBound(Canvas canvas) {
        canvas.drawRect(mVisibleRect, mBoundPaint);
    }

    private void drawDimmedLayer(Canvas canvas) {
        canvas.save();
        canvas.clipRect(mVisibleRect, Region.Op.DIFFERENCE);
        canvas.drawColor(mDimmedColor);
        canvas.restore();
    }
}
