package com.example.encrypt.mobileencryption;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ShowImage extends AppCompatActivity {
    ImageView comingimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

       comingimg=(ImageView)findViewById(R.id.imageView3);

        Bitmap bm=null;
        String filename=getIntent().getStringExtra("swimg");
        try {
            FileInputStream is=this.openFileInput(filename);
            bm=BitmapFactory.decodeStream(is);
            is.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        comingimg.setImageBitmap(bm);
    }
}
