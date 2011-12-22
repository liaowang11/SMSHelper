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
	public SMSService() {
		super("SMSService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extra_bundle = intent.getExtras();
		String msg_content = extra_bundle.getString("msg_content");
		String source_addr = extra_bundle.getString("msg_source_addr");
		String result = "";
		SMSParser parser;
		ContentResolver cr = getContentResolver();
		SharedPreferences pref = getSharedPreferences(SettingActivity.SHARED_MAIN, MODE_PRIVATE);
		HistoryDB histo_db = new HistoryDB(this); 
		boolean authenticated = false;
		
		try {
			parser = new SMSParser(msg_content);
		}catch(ParseException e){
			return; //The Message is not for me.
		}
		while(parser.hasNext()){
			Pair<String, String> next = parser.next();
			BaseCommand command = CommandManager.BuildCommand(pref, next.first, next.second);
			if(command == null){
				result = "Ill-formed Command.";
				break;
			}else{
				Log.v(TAG,next.first +"," +next.second);
				try{
					result += command.Execute(cr) + "|";
				}catch(AuthException e){
					result = "Invalid Auth Code";
					break; //No longer build commands.
				}
			}
		}
		histo_db.insert(msg_content, source_addr);
		Log.v(TAG, result);
		SMSSender sender = new SMSSender(source_addr);
		sender.send(result);
		Log.v(TAG, "Sended to " + source_addr);
		histo_db.close();
	}
	
}
