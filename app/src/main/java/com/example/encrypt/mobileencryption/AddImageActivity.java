package com.example.encrypt.mobileencryption;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class AddImageActivity extends AppCompatActivity {
    Button addimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);
        addimg=(Button)findViewById(R.id.btnAddImage);
        String get_user=getIntent().getStringExtra("sent_user");
        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        ListView l=(ListView)findViewById(R.id.lst1);
        String encodedpath = null;
        //String decodedpath=null;
        String u=getIntent().getStringExtra("sent_user");
        String a=u;
        if (resultCode == RESULT_OK){

            Calendar c=Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = sdf.format(c.getTime());

            Uri targetUri = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(targetUri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            final String picturePath = cursor.getString(columnIndex);
            File f = new File(picturePath);
            String imageName = f.getName();

            String imgpath=picturePath;
            String u_name=a;

            String type="insert";

            try{
                KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
                SecretKey myDesKey = keygenerator.generateKey();

                Cipher desCipher;
                desCipher = Cipher.getInstance("DES");


                byte[] text = imgpath.getBytes("UTF8");


                desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
                byte[] textEncrypted = desCipher.doFinal(text);


                encodedpath = textEncrypted.toString();
                //decodedpath=textEncrypted.toString();



                //desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
                //byte[] textDecrypted = desCipher.doFinal(textEncrypted);

               // decodedpath = new String(textDecrypted);

                //Toast.makeText(getApplicationContext(), encodedpath+"\n"+decodedpath, Toast.LENGTH_LONG).show();

            }catch(Exception e)
            {
                System.out.println("Exception");
            }

            InsertWorker insertWorker=new InsertWorker(this);
            insertWorker.execute(type, encodedpath, u_name, strDate);
        }
    }


}
