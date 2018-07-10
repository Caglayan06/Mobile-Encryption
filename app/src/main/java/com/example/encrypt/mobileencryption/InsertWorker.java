package com.example.encrypt.mobileencryption;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class InsertWorker extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    //public String username_sent;
    InsertWorker (Context ctx){
        context=ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type=params[0];
        String login_url="http://192.168.1.38/MobileEncryption/imageinsert.php";
        if(type.equals("insert")){
            try {
                String picpath=params[1];
                //username_sent=params[1];
                String us_na=params[2];
                String dt=params[3];
                URL url=new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data= URLEncoder.encode("picpath", "UTF-8")+"="+URLEncoder.encode(picpath, "UTF-8")+"&"
                        +URLEncoder.encode("us_na", "UTF-8")+"="+URLEncoder.encode(us_na, "UTF-8")+"&"+URLEncoder.encode("dt", "UTF-8")+"="+URLEncoder.encode(dt, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result!=null&&result.equals("Insert Successful")){
            alertDialog.setMessage(result);
            alertDialog.show();
        }
        else {
            alertDialog.setMessage("Insert Not Successful");
            alertDialog.show();
        }
    }

    @Override
    protected void onPreExecute() {
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Insert Status");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
