package com.mmrcl.phonecodeapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotificationHelp {
	
	public static void customNotify(Context context,int icon,Intent intent, String flashMessage, String title, String contentMessage, int ID)
	{
		final NotificationManager mgr = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification note = new Notification(icon, flashMessage, System.currentTimeMillis());

		// This pending intent will open after notification click
		PendingIntent i = PendingIntent.getActivity(context, 0, intent, 0);
//new change
		//note.setLatestEventInfo(context, title, contentMessage, i);

		
		// After uncomment this line you will see number of notification arrived
		// note.number=2;
		mgr.notify("Notification1",ID, note);
		
	}

}
