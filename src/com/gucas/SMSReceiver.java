package com.gucas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String source_addr = "";
		String str = null;
		if(bundle != null){
			Object[] pdus = (Object []) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
				byte[] content = msgs[i].getPdu();
				//str += msgs[i].getMessageBody(); //XXX:Encoding Problem
				str = msgs[i].getMessageBody();
				source_addr = msgs[i].getOriginatingAddress();
				Log.v("SMSReceiver", str);
				str = msgs[i].getDisplayMessageBody();
				Log.v("SMSReceiver", str);
			}
		}
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	    Intent service_intent = new Intent(context, SMSService.class);
	    service_intent.putExtra("msg_content", str);
	    service_intent.putExtra("msg_source_addr",source_addr);
	    context.startService(service_intent);
	}
}
