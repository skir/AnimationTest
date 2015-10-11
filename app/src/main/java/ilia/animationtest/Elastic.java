package ilia.animationtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ilia on 11.10.15.
 */
public class Elastic extends View {

    Paint color;
    Path polygon;

    public Elastic(Context context, AttributeSet attrs) {
        super(context, attrs);

        color = new Paint();
        color.setColor(getResources().getColor(R.color.background));
        color.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(polygon == null) {
            polygon = new Path();
            polygon.lineTo(0,getHeight());
            polygon.lineTo(getWidth(),getHeight());
            polygon.lineTo(getWidth(),0);
            polygon.lineTo(0,0);
        }

        canvas.drawPath(polygon, color);
    }

    public void onMotion(float value){

        if(polygon == null)
            polygon = new Path();

        Path path = new Path();

        if(value <= 0.5)
            path.lineTo(0, getHeight() - value * 50);
        else
            path.lineTo(0, getHeight() - 50 + value * 50);

        path.lineTo(getWidth(), getHeight());

        if(value <= 0.5)
            path.lineTo(getWidth(), value * 200);
        else
            path.lineTo(getWidth(), 200 - value * 200);

        path.lineTo(0, 0);
        polygon.set(path);

        invalidate();
    }

    public void onBounceMotion(float value){

        if(polygon == null)
            polygon = new Path();

        Path path = new Path();

        path.lineTo(0, getHeight());

        if(value <= 0.5)
            path.lineTo(getWidth(), getHeight() - 20 * value);
        else
            path.lineTo(getWidth(), getHeight() - 20 + 20 * value);

        path.lineTo(getWidth(), 0);
        path.lineTo(0, 0);
        polygon.set(path);

        invalidate();
    }
}
