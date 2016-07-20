package com.example.vharari.smsreader;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView1 = (TextView) findViewById(R.id.textView1);

        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        String msgData = "";
        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
//                String msgData = "";
                for(int idx=0;idx<cursor.getColumnCount();idx++)
                {
                    if(cursor.getColumnName(idx).contains("address") || cursor.getColumnName(idx).contains("date_sent") || cursor.getColumnName(idx).contains("body"))
                        msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx) + "\n";
                    if(cursor.getColumnName(idx).contains("body"))
                        msgData += "===========================\n";

                }
                // use msgData
            } while (cursor.moveToNext());
        } else {
            // empty box, no SMS
        }

        textView1.setText(msgData);

        String filepath = "/storage/sdcard1";
        String filepath1 = Environment.getDownloadCacheDirectory().getPath();
        String filepath2 = Environment.getExternalStoragePublicDirectory("hello").getPath();
        String filepath3 = Environment.getRootDirectory().getPath();
        String filepath4 = Environment.getDataDirectory().getPath();
        String filepath5 = Environment.getExternalStorageDirectory().getPath();
        String filepath6 = Environment.getExternalStorageState();
        String filepath7 = Environment.DIRECTORY_DCIM;

        File file = new File(filepath, "PhoneMessages");
        try {
            file.createNewFile();
           /* FileOutputStream out = new FileOutputStream(file);
            out.write(info);*/
            PrintWriter out = new PrintWriter(file);
            out.write(msgData);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "File Path : "+filepath+" File exists : "+file.exists(), Toast.LENGTH_LONG).show();
    }
}
