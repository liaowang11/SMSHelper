package com.gucas;


import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.os.IBinder;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

public class SMSService extends IntentService {
	private static String TAG = "SMSService";
	private static String PREF_FILE = "SMSHELPER_PREF";
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
		SharedPreferences pref = getSharedPreferences(PREF_FILE, MODE_PRIVATE);
		try {
			parser = new SMSParser(msg_content);
			while(parser.hasNext()){
				Pair<String, String> next = parser.next();
				BaseCommand command = CommandManager.BuildCommand(pref, next.first, next.second);
				Log.v(TAG,next.first +"," +next.second);
				result += command.Execute(cr);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			result = "Invalid Password";
		}
		Log.v(TAG, result);
		SMSSender sender = new SMSSender(source_addr);
		sender.send(result);
		Log.v(TAG,"Sended to " + source_addr);
	}
	
}
