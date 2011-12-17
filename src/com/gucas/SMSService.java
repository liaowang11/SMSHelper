package com.gucas;


import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
//import android.os.IBinder;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

public class SMSService extends IntentService {
	private String TAG = "SMSService";
	public SMSService() {
		super("SMSService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		//Toast.makeText(this, "Started Service", Toast.LENGTH_SHORT);
		Bundle extra_bundle = intent.getExtras();
		String msg_content = extra_bundle.getString("msg_content");
		String source_addr = extra_bundle.getString("msg_source_addr");
		String result = "";
		SMSParser parser;
		ContentResolver cr = getContentResolver();
		try {
			parser = new SMSParser(msg_content);
			while(parser.hasNext()){
				Pair<String, String> next = parser.next();
				BaseCommand command = CommandManager.BuildCommand(next.first, next.second);
				Log.v(TAG,next.first +"," +next.second);
				result += command.Execute(cr);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Log.v(TAG, result);
		SMSSender sender = new SMSSender(source_addr);
		sender.send(result);
		Log.v(TAG,"Sended to " + source_addr);
	}
	
}
