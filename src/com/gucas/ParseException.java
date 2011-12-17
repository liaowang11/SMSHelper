package com.gucas;

import android.util.Log;

public class ParseException extends Exception{
	public ParseException() {
		super();
		Log.v("Parser", "Error Parsing the SMS");
	}
	public ParseException(String msg){
		super(msg);
		Log.v("Parser", "Error Parsing the SMS:"+msg);
	}
}