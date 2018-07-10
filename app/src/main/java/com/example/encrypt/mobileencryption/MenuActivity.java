package com.example.encrypt.mobileencryption;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;


public class MenuActivity extends AppCompatActivity {

    Button gallerybutton;
    Button messagebutton;
    Button addimage;
    Button btn2;
    AlertDialog alertDialog;
    String userr;
    EditText inputgallery;
    String getgalkey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Please enter the gallery key:");
        builder.setTitle("Gallery Key");
        inputgallery = new EditText(this);
        final String cameuser=getIntent().getStringExtra("username_after_login");

        builder.setView(inputgallery);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //getgalkey=inputgallery.getText().toString();
                String type="galleryentrance";
                String us=cameuser;
                GalleryKeyWorker galleryKeyWorker=new GalleryKeyWorker(this);
                galleryKeyWorker.execute(type, us);
                //Intent intent = new Intent(MenuActivity.this, GalleryActivity.class);
                //startActivity(intent);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        btn2=findViewById(R.id.button2);
        gallerybutton=findViewById(R.id.btnGallery);
        messagebutton=findViewById(R.id.btnMessages);
        addimage=findViewById(R.id.btnAddPath);

        alertDialog=new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Welcome!");
        alertDialog.setMessage("Welcome "+cameuser+"!");
        alertDialog.show();
        userr=cameuser;
        final AlertDialog ad=builder.create();


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MenuActivity.this, GalleryActivity.class);
                startActivity(intent);
            }
        });


        gallerybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ad.show();
                //Intent intent = new Intent(MenuActivity.this, GalleryActivity.class);
                //startActivity(intent);
            }
        });

        messagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, OpenSMS.class);
                startActivity(intent);
            }
        });

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuActivity.this, AddImageActivity.class);
                intent.putExtra("sent_user", userr);
                startActivity(intent);
            }
        });



    }


}
