package com.gucas;

import android.telephony.SmsManager;

public class SMSSender implements ISender {
	public static String mName = "SMSSender";
	private String mTargetAddr = "";
	public SMSSender(String target_addr) {
		this.mTargetAddr = target_addr;
	}
	public void send(String content) {
		SmsManager sms_manager = SmsManager.getDefault();
		sms_manager.sendTextMessage(this.mTargetAddr, null, content, null, null);
	}

}
