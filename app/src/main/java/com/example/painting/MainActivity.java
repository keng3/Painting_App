package com.example.painting;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private DrawingView drawingview;
    private ImageButton current;
    private Button clearbutton;
    //private Button undobutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawingview = (DrawingView) findViewById(R.id.drawing);
        LinearLayout painting = (LinearLayout) findViewById(R.id.colorlist);
        current = (ImageButton) painting.getChildAt(0);
        clearbutton = (Button) findViewById(R.id.clearButton);
        clearbutton.setOnClickListener(this);
        //undobutton = (Button) findViewById(R.id.undoButton);
        //undobutton.setOnClickListener(this);
    }
    public void click(View view) {
        if(view != current) {
            drawingview.changecolor(view.getTag().toString());
            current = (ImageButton) view;
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.clearButton) {
            drawingview.clear();
        } //else if (view.getId() == R.id.undoButton) {
            //drawingview.undodrawing();
        //}
    }
}