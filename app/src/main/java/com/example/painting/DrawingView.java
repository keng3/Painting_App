package com.example.painting;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DrawingView extends View {
    private Path path;
    private Paint paint;
    private Paint canpaint;
    private int color = 0xFF000000;
    private Canvas canvas;
    private Bitmap map;
    private float newx;
    private float newy;
    private Map<Path, Integer> colorsMap = new HashMap<Path, Integer>();
    private ArrayList<Path> moving = new ArrayList<Path>();
    private ArrayList<Path> undomove = new ArrayList<Path>();
    public DrawingView(Context con, AttributeSet att) {
        super(con, att);
        setDrawing();
    }
    private void setDrawing() {
        path = new Path();
        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(30);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canpaint = new Paint(Paint.DITHER_FLAG);
    }
    @Override
    protected void onSizeChanged(int width, int height, int oldwidth, int oldheight) {
        super.onSizeChanged(width, height, oldheight, oldwidth);
        map = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(map);
    }
    @Override
    protected void onDraw(Canvas can) {
        /*for (Path line : moving) {
            paint.setColor(colorsMap.get(line));
            can.drawPath(line, paint);
        }
        paint.setColor(color);
        */
        /*for (Path line : moving) {
            can.drawPath(line, paint);
        }
        */
        can.drawBitmap(map, 0, 0, canpaint);
        can.drawPath(path, paint);

    }
    @Override
    public boolean onTouchEvent(MotionEvent motion) {
        float x = motion.getX();
        float y = motion.getY();
        switch (motion.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //start(x, y);
                //invalidate();
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                //move(x, y);
                //invalidate();
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                //up();
                //invalidate();
                canvas.drawPath(path, paint);
                path.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }
    public void changecolor (String newcolor) {
        invalidate();
        paint.setColor(Color.parseColor(newcolor));
    }
    public void clear() {
        //path = new Path();
        //moving.clear();
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }
    public void undodrawing() {
        if (moving.size() > 0) {
            undomove.add(moving.remove(moving.size() - 1));
            invalidate();
        }
    }
    private void start(float x, float y) {
        undomove.clear();
        path.reset();
        path.moveTo(x, y);
        newx = x;
        newy = y;
    }
    private void move(float x, float y) {
        float changingx = Math.abs(x - newx);
        float changingy = Math.abs(y - newy);
        if (changingx >= 4 || changingy >= 4) {
            path.quadTo(newx, newy, (x + newx) / 2, (y + newy) / 2);
            newx = x;
            newy= y;
        }
    }
    private void up() {
        path.lineTo(newx, newy);
        canvas.drawPath(path, paint);
        moving.add(path);
        path = new Path();
    }
}