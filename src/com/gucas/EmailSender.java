package com.gucas;

import android.content.Intent;
//TODO:Sending email without interaction seems a lot of work.
//Put this work to later.
public class EmailSender implements ISender {
	public static String mName = "EmailSender";
	private String mTargetAddr = "";
	public EmailSender(String target_addr) {
		this.mTargetAddr = target_addr;
	}
	public void send(String content) {
	}

}
