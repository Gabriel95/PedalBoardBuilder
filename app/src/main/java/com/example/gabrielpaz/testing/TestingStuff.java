package com.example.gabrielpaz.testing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;

public class TestingStuff extends AppCompatActivity {
    ParseFile Img;
    Button testButton;
    Button rotateButton;
    Button boardButton;
    ImageView selectedImg;
    ImageView Board;
    float angle = 0;
    boolean moving = true;
    FrameLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_stuff);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ParseService.MakeConnection(this);

        testButton = (Button)findViewById(R.id.testButton);
        rotateButton = (Button)findViewById(R.id.rotateButton);
        boardButton = (Button)findViewById(R.id.boardButton);
        layout = (FrameLayout)findViewById(R.id.fmlayout);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Pedals");
                query.getInBackground("pRNQEu9S08", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, com.parse.ParseException e) {
                        if (e == null) {
                            Img = object.getParseFile("Image");
                            final float w = object.getInt("Width");
                            final float h = object.getInt("Height");
                            Img.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, com.parse.ParseException e) {
                                    if (e == null) {
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        AddNewPedal(bitmap,w,h);

                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        boardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Board");
                query.getInBackground("GFipvulrr9", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, com.parse.ParseException e) {
                        if (e == null) {
                            Img = object.getParseFile("Image");
                            final float w = object.getInt("Width");
                            final float h = object.getInt("Height");
                            Img.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, com.parse.ParseException e) {
                                    if (e == null) {
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        AddBoard(bitmap,w,h);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        rotateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                angle += 90;
                selectedImg.setRotation(angle);
            }
        });


    }

    public void AddNewPedal(Bitmap bitmap, float w, float h)
    {
        final ImageView p = new Pedal(this);
        p.setImageBitmap(bitmap);
        ((Pedal)p).PedalWidth = w;
        ((Pedal)p).PedalHeight = h;
        p.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
        p.bringToFront();
        p.setScaleType(ImageView.ScaleType.FIT_CENTER);

        int boardWidth = (int) ((Pedal)Board).PedalWidth;
        int pedalWidth = (int) ((Pedal)p).PedalWidth;

        int boardHeight = (int) ((Pedal)Board).PedalHeight;
        int pedalHeight = (int) ((Pedal)p).PedalHeight;

        int wi = getProportion(boardWidth, Board.getWidth(), pedalWidth);
        int he = getProportion(boardHeight, Board.getHeight(), pedalHeight);

        p.getLayoutParams().width = wi;
        p.getLayoutParams().height = he;

        p.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        moving = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (moving) {
                            p.setX(event.getRawX() - p.getWidth());
                            p.setY(event.getRawY() - p.getHeight());
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        moving = false;
                        selectedImg = p;
                        p.bringToFront();
                        break;
                }
                return true;
            }
        });
        layout.addView(p);
    }

    public void AddBoard(Bitmap bitmap, float w, float h){
         Board = new Pedal(this);
        ((Pedal)Board).PedalWidth = w;
        ((Pedal)Board).PedalHeight = h;
        ((Pedal)Board).type = "Board";
         Board.setImageBitmap(bitmap);
         layout.addView(Board);
    }

    public int getProportion(int a, int b, int c)
    {
        return (b * c)/a ;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_testing_stuff, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
