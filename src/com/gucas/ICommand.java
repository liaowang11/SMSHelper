package com.gucas;

import android.content.ContentResolver;
//Command pattern
interface ICommand {
	public String Execute(ContentResolver cr);
}

abstract class BaseCommand implements ICommand {
	public static String mCommandName;
	public String mArgs;
	public abstract String Execute(ContentResolver cr);
	public void setArgs(String mArgs) {
		this.mArgs = mArgs;
	}
	public String getArgs() {
		return mArgs;
	}
}
