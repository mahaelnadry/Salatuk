package com.example.mahae_000.salatuk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by mahae_000 on 7/1/2015.
 */
public class Start_view extends View {
    Bitmap bitmap1;
    Paint color;
    Paint small;
    public Start_view(Context context) {
        super(context);
        bitmap1= BitmapFactory.decodeResource(getResources(),R.drawable.bg);
        color = new Paint();
        color.setColor(Color.RED);
        color.setTextSize(20);
        small = new Paint();
        small.setColor(Color.RED);
        small.setTextSize(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float half_width=canvas.getWidth()/2;
        float half_height=canvas.getHeight()/2;
        float position_x=half_width-bitmap1.getWidth()/2;
        float position_y=half_height-bitmap1.getHeight()/2;
        canvas.drawBitmap(bitmap1,position_x,position_y, null);
        canvas.drawText("Welcome to Salatuk",half_width,half_height,color);
        canvas.drawText("Continue",half_width,half_height+20,small);
    }

}
