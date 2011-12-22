package com.gucas;

import android.R.array;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;

public class FetchContactCommand extends BaseCommand {
	
	public static String mCommandName = "fc";
	
	private String mArgs;
	public FetchContactCommand(String args) {
		this.mArgs = args;
	}
	@Override
	public String Execute(ContentResolver resolver) {
		String[] contact_projection = new String[] {
				Contacts._ID,
				Contacts.DISPLAY_NAME,
				Contacts.LOOKUP_KEY,};
		//TODO:Just for testing, refactor below. 
		Uri contact_lookup_uri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, this.mArgs.split(",")[0]);
		Cursor contact = resolver.query(contact_lookup_uri, contact_projection, null, null, null);
		if(contact != null && contact.getCount() == 1 && contact.moveToFirst()){
			//String lookup_key = contact.getString(2);
			Uri phone_lookup_uri = CommonDataKinds.Phone.CONTENT_URI;
	        String phone_selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contact.getString(0); 
			Cursor phone = resolver.query(phone_lookup_uri, 
					new String[] {ContactsContract.CommonDataKinds.Phone.NUMBER},
					phone_selection, null, null);
			if(phone != null && phone.getCount() >= 1 && phone.moveToFirst()){
				Log.v("FetchContact", "Phone count: " + phone.getCount());
				String name_with_numbers = phone.getString(0);
				while(phone.moveToNext()){
					name_with_numbers += " " + phone.getString(0);
				}
				return contact.getString(1) + ":" + name_with_numbers; //Name + Numbers
				//TODO: ^^^^^^^^^^^^^^^^^^:Encoding Problem
			}
		}
		return "Cannot find " + this.mArgs;
	}
}
