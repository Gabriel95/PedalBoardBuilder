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
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.RotateAnimation;
import android.widget.Button;
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
    ImageView image;
    Button rotateButton;
    float angle = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_stuff);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "MNsckKzXmwFgf2mu7t1PsrZt0oZQKI39uhMeN1de", "WVx1RVB7qnFQP5Rv1YRyh0dcnqL8P8zIiQbmuOmr");

        testButton = (Button)findViewById(R.id.testButton);
        rotateButton = (Button)findViewById(R.id.rotateButton);
        image = (ImageView)findViewById(R.id.imageView);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Pedals");
                query.getInBackground("OOczVPc67W", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, com.parse.ParseException e) {
                        if (e == null) {
                            Img = object.getParseFile("Image");
                            Img.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, com.parse.ParseException e) {
                                    if (e == null) {
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data , 0, data.length);
                                        image.setImageBitmap(bitmap);

                                    } else {
                                        // something went wrong
                                    }
                                }
                            });
                        }
                        else {

                        }
                    }
                });
            }
        });

        rotateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
              rotate(image);
            }
        });

    }

    public void rotate(ImageView image){

        angle += 90;

        final RotateAnimation rotateAnim = new RotateAnimation(0.0f, angle,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnim.setDuration(0);
        rotateAnim.setFillAfter(true);
        image.startAnimation(rotateAnim);
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
