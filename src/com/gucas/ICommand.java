package com.gucas;

import android.content.ContentResolver;
import android.content.SharedPreferences;
//Command pattern
interface ICommand {
	public String Execute(ContentResolver cr) throws AuthException;
}

abstract class BaseCommand implements ICommand {
	public static String mCommandName;
	protected String mArgs;
	protected SharedPreferences mPref;
	public abstract String Execute(ContentResolver cr) throws AuthException;
	public void setmPref(SharedPreferences mPref) {
		this.mPref = mPref;
	}
	public void setArgs(String mArgs) {
		this.mArgs = mArgs;
	}
	public String getArgs() {
		return mArgs;
	}
}
