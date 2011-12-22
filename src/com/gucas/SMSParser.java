package com.gucas;

import java.util.ArrayList;
import java.util.Iterator;

import android.util.Pair;
//This class parses the SMS content, returning list of tokens(String) standing for commands.
public class SMSParser implements Iterator<Pair<String, String>>{
	private String mMsg;
	private ArrayList<Pair<String, String>> mTokens; //List of Pair<CommandName, Arguments>
	private Iterator<Pair<String, String>> mIter;

	public SMSParser(String msg_content) throws ParseException {
		this.mMsg = msg_content;
		this.mTokens = new ArrayList<Pair<String,String>>();
		String[]commands = this.mMsg.split(";");
		Pair<String, String> new_pair;
		for (String command : commands) {
			String[] command_components = command.split(":");
			if(command_components.length == 0){
				throw new ParseException();
			}else if (command_components.length > 1){
				new_pair = new Pair<String, String>(command_components[0], command_components[1]);
			}else{
				new_pair = new Pair<String, String>(command_components[0], "");
			}
			mTokens.add(new_pair);
		}
		this.mIter = mTokens.iterator();
	}
	public boolean hasNext() {
		return mIter.hasNext();
	}
	public Pair<String, String> next() {
		return mIter.next();
	}
	public void remove() {
		mIter.remove();
	}
}
