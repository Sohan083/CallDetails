package com.example.calldetails;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Date;

public class ActivityCallDetails extends AppCompatActivity {


    EditText edtNumber;
    Button callBtn, getDetailsBtn;
    TextView txtDetails;

    String phoneNumber = "";


    private int PERMISSION_ALL = 1;

    private static String[] PERMISSIONS_LIST = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_details);

        edtNumber = findViewById(R.id.number);
        callBtn = findViewById(R.id.callBtn);
        getDetailsBtn = findViewById(R.id.getDetailsBtn);
        txtDetails = findViewById(R.id.callDetailsText);

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = edtNumber.getText().toString();
                if(!phoneNumber.equals(""))
                {
                    String uri = "tel:" + phoneNumber.trim() ;
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                }
            }
        });

        getDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCallDetails();
            }
        });

        checkPermission();

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    private void checkPermission() {
        if (!hasPermissions(this, PERMISSIONS_LIST)) {
            Log.e("per","error perm");
            ActivityCompat.requestPermissions(this, PERMISSIONS_LIST, PERMISSION_ALL);
        }
    }

    private void getCallDetails() {

        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = managedQuery( CallLog.Calls.CONTENT_URI,null, null,null, CallLog.Calls.DATE + " DESC");
        int number = managedCursor.getColumnIndex( CallLog.Calls.NUMBER );
        int type = managedCursor.getColumnIndex( CallLog.Calls.TYPE );
        int date = managedCursor.getColumnIndex( CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex( CallLog.Calls.DURATION);
        sb.append( "Call Details :");
        int i = 0;
        while ( managedCursor.moveToNext() ) {
            String phNumber = managedCursor.getString( number );
            String callType = managedCursor.getString( type );
            String callDate = managedCursor.getString( date );
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString( duration );
            String dir = null;
            int dircode = Integer.parseInt( callType );
            switch( dircode ) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
            sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+callDayTime+" \nCall duration in sec :--- "+callDuration );
            sb.append("\n----------------------------------");
            i++;
            if(i == 3) break;
        }
        managedCursor.close();
        txtDetails.setText(sb);
    }
}
