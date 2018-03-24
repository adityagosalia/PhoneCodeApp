package com.mmrcl.phonecodeapp;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;

import com.mmrcl.phonecodeapp.util.DbHelp;
import com.mmrcl.phonecodeapp.util.HttpHelp;

class UpdateServiceTask extends AsyncTask<Integer,Integer,Integer>{
	 private ProgressDialog dialog;
     List<Message> titles;
     private Context context;
     private Activity activity;
     //private List<Message> messages;
     public  UpdateServiceTask(Activity activity) {
         this.activity = activity;
         context = activity;
         dialog = new ProgressDialog(context);
     }



     /** progress dialog to show user that the backup is processing. */

     /** application context. */
  


   @Override
protected void onPreExecute() {
	
	super.onPreExecute();
    this.dialog.setMessage("Progress start");
    this.dialog.show();
}
   
   @Override
protected void onPostExecute(Integer result) {
	// TODO Auto-generated method stub
	super.onPostExecute(result);
	 if (dialog.isShowing()) {
         dialog.dismiss();}
}
@Override
protected Integer doInBackground(Integer... arg0) {
	System.out.println("here");
	int a=20;
	int b=30;
	for (int i = 0; i < 5000; i++) {
	//System.out.println(i+a);
		
	}
    DbHelp.setServerData();
		return a;
}


}