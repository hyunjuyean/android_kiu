package com.example.jjupiano;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private TouchView touchView;
    ArrayList<Vertex> arrayVertex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //가로 화면으로 고정(ContentView가 호출되기전 호출되어야함)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        touchView = new TouchView(this);
        setContentView(touchView);

        arrayVertex = new ArrayList<Vertex>();
    }

    public class Vertex {
        float x;
        float y;
        boolean draw;
        Vertex(float xcoord, float ycoord, boolean yesno) {
            x = xcoord;
            y = ycoord;
            draw = yesno;
        }
    }

    protected class TouchView extends View {
        Paint drawPaint;

        public TouchView(Context context) {
            super(context);

            drawPaint = new Paint();
            drawPaint.setColor(Color.BLACK);
            drawPaint.setStrokeWidth(5);
            drawPaint.setAntiAlias(true);
        }

        public void onDraw(Canvas canvas) {
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.pa);

            canvas.drawBitmap(b,0,0,null);

            // connect with vertex
            for (int i=0; i<arrayVertex.size(); i++) {
                if (arrayVertex.get(i).draw) {
                    canvas.drawLine(arrayVertex.get(i-1).x, arrayVertex.get(i-1).y,
                            arrayVertex.get(i).x, arrayVertex.get(i).y, drawPaint);
                }
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                arrayVertex.add(new Vertex(event.getX(), event.getY(), false));
                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                arrayVertex.add(new Vertex(event.getX(), event.getY(), true));
                invalidate();
                return true;
            }
            return false;
        }

    }
}