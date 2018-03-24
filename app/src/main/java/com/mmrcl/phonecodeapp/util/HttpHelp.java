package com.mmrcl.phonecodeapp.util;

	import java.io.BufferedReader;
	import java.io.FileInputStream;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.util.ArrayList;
	import java.util.List;

	import org.apache.http.HttpEntity;
	import org.apache.http.HttpResponse;
	import org.apache.http.NameValuePair;
	import org.apache.http.client.HttpClient;
	import org.apache.http.client.entity.UrlEncodedFormEntity;
	import org.apache.http.client.methods.HttpGet;
	import org.apache.http.client.methods.HttpPost;
	import org.apache.http.impl.client.DefaultHttpClient;
	import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mmrcl.phonecodeapp.Details;
	import com.mmrcl.phonecodeapp.PhoneCodeApp;

	import android.content.Context;
	import android.util.Log;

public class HttpHelp {
		public static List<Details> getList1()
		{
			List<Details> li=new ArrayList<Details>();
			try{
				HttpClient httpclient;
				InputStream is = null;
				httpclient = new DefaultHttpClient();
				
				HttpGet httpget = new HttpGet("http://10.0.2.2:8080/RestPhoneService/service/rest/phonecodeservice/getall");
				HttpResponse response = httpclient.execute(httpget);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();

		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        String dat=reader.readLine();
		        JSONObject read = new JSONObject(dat);
		        JSONArray jsonArray = read.optJSONArray("Details");  
		        for(int i=0; i < jsonArray.length(); i++){  
                    JSONObject jsonObject = jsonArray.getJSONObject(i);  
                    Details details=new Details();
                    details.setDeptDetails(jsonObject.optString("deptDetails").toString());
                    details.setDeptName(jsonObject.optString("deptName").toString());
					details.setMobileNo(jsonObject.optString("mobileNo").toString());
					details.setLandline(jsonObject.optString("landline").toString());
                    details.setExtCode(jsonObject.optString("extCode").toString());
                    li.add(details);   
                   
                  }
		        
		        return li;
			}
				catch(Exception e)
				{
					//li.add(e.toString());
					return null;
				}
		}
		public static List<Details> getList(){
			List<Details> li=new ArrayList<Details>();
			try {
				JSONObject read = new JSONObject(Data.json);
				JSONArray jsonArray = read.optJSONArray("Details");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					Details details = new Details();
					details.setDeptDetails(jsonObject.optString("deptDetails").toString());
					details.setDeptName(jsonObject.optString("deptName").toString());
					details.setMobileNo(jsonObject.optString("mobileNo").toString());
					details.setLandline(jsonObject.optString("landline").toString());
					details.setExtCode(jsonObject.optString("extCode").toString());
					li.add(details);

				}
			}catch(Exception e) {
			}

			return li;
		}

		public static String loadJSONFromAsset()  {
			String json = null;
			try {

				InputStream is = PhoneCodeApp.appContext.getAssets().open("contact.json");

				int size = is.available();

				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();

				json = new String(buffer, "UTF-8");
				Log.e("json",json);

			} catch (IOException ex) {
				ex.printStackTrace();
				return null;
			}
			return json;

		}

	public static String ReadFromfile() {
		StringBuilder returnString = new StringBuilder();
		InputStream fIn = null;
		InputStreamReader isr = null;
		BufferedReader input = null;
		try {
			fIn = PhoneCodeApp.appContext.getResources().getAssets()
					.open("contact.json", Context.MODE_WORLD_READABLE);
			isr = new InputStreamReader(fIn);
			input = new BufferedReader(isr);
			String line = "";
			while ((line = input.readLine()) != null) {
				returnString.append(line);
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (isr != null)
					isr.close();
				if (fIn != null)
					fIn.close();
				if (input != null)
					input.close();
			} catch (Exception e2) {
				e2.getMessage();
			}
		}Log.e("json",returnString.toString());
		return returnString.toString();
	}
}
