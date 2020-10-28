package com.example.calldetails;

import android.content.Context;
import android.util.Log;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CallReceiver extends PhonecallReceiver {

    @Override
    protected void onIncomingCallReceived(Context ctx, String number, Date start)
    {
        //
    }

    @Override
    protected void onIncomingCallAnswered(Context ctx, String number, Date start)
    {
        //
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end)
    {
        //
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start)
    {
        //
        Log.d("call status","out start");
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end)
    {
        //
        Log.d("call status","out end "+String.valueOf(getDateDiff(start,end, TimeUnit.MILLISECONDS)/1000.0));
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start)
    {
        //
        Log.d("call status","missed");
    }

    public  long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

}