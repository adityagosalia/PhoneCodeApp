package com.mmrcl.phonecodeapp.util;

import com.mmrcl.phonecodeapp.Details;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.*;
public class DbHelp extends SQLiteOpenHelper {

	public DbHelp(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	static SQLiteDatabase sqldb;
	static Exception err;
	public String msg;
	public static List<String> searchlist;
	public static List<String[]> showlist;
	public static List<String[]> filterlist;
	public static List<Details> data;
	public static void load(Context context)
	{
		DbHelp dbHelp=new DbHelp(context);
		DbHelp.sqldb=dbHelp.getWritableDatabase();
		try{
			Cursor c=sqldb.rawQuery("select * from phoneDetails",null);
			setList(c);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(e.toString().contains("no such table:"))
			{
				sqldb.execSQL("create table IF NOT EXISTS phoneDetails(deptName VARCHAR unique, deptDetails VARCHAR, mobileNo VARCHAR, landLine VARCHAR, extCode VARCHAR)");

				//sqldb.execSQL("create table IF NOT EXISTS phoneDetails(deptName VARCHAR unique, deptDetails VARCHAR(180), extCode VARCHAR)");
				//load from server
				setServerData();
			}
		}
	}
	private static void setList(Cursor c)
	{
		searchlist=new ArrayList<String>();
		showlist=new ArrayList<String[]>();
		data=new ArrayList<Details>();
		while (c.moveToNext()) {
			searchlist.add(c.getString(0));
			searchlist.add(c.getString(1));
			Details details=new Details();
			details.setDeptDetails(c.getString(1));
			details.setDeptName(c.getString(0));
			details.setExtCode(c.getString(2));
			data.add(details);
			String[] values=new String[2];
			values[0]=c.getString(0);
			values[1]=c.getString(2);
		showlist.add(values);
			
			
		}
		c.close();

	}
	public static int update(Details details,String name)
	{
		try{
		sqldb.execSQL("update phoneDetails set deptName='"+details.getDeptName()+"' ,deptDetails='"+details.getDeptDetails()+"',mobileNo='"+details.getMobileNo()
				+ "',landLine='"+details.getLandline()+"',extCode='"+details.getExtCode()+"' where deptName='"+name+"'");
		return 1;
		}
		catch(Exception e)
		{
			return 0;
		}
	}
	
	public static int insert(Details details)
	{
		try{
		sqldb.execSQL("insert into phoneDetails values('"+details.getDeptName()+"','"+details.getDeptDetails()
				+"','"+details.getMobileNo()+"','"+details.getLandline()
				+"','"+details.getExtCode()+"')");
		return 1;
		}
		catch(Exception e)
		{
			return 0;
		}
	}
	
	public static int delete(String name)
	{
		try{
		sqldb.execSQL("delete from phoneDetails where deptName='"+name+"'");
		return 1;
		}
		catch(Exception e)
		{
			return 0;
		}
	}
	public static void setServerData() {
		List<Details> list = new ArrayList<Details>();
		ArrayAdapter<String> adapter;
		try {

			list = HttpHelp.getList();
			sqldb.execSQL("drop table phoneDetails");
			sqldb.execSQL("create table phoneDetails(deptName VARCHAR unique, deptDetails VARCHAR, mobileNo VARCHAR, landLine VARCHAR, extCode VARCHAR)");
			for(int i=0;i<list.size();i++){
				sqldb.execSQL("insert into phoneDetails values('"+list.get(i).getDeptName()+"','"+list.get(i).getDeptDetails()
						+"','"+list.get(i).getMobileNo()+"','"+list.get(i).getLandline()
						+"','"+list.get(i).getExtCode()+"')");
				
			}
			Log.e("no of inserts",list.size()+"");
			Cursor c = sqldb.rawQuery("select * from phoneDetails",null);
			Log.e("no of rows in sqlight",c.getCount()+"");
			setList(c);
	
		} catch (Exception e) {
				e.printStackTrace();
		}
	}

	public static void setFilterList(Cursor c){
		filterlist=new ArrayList<String[]>();
		while (c.moveToNext()) {
			String[] values=new String[2];
			values[0]=c.getString(0);
			values[1]=c.getString(2);
			filterlist.add(values);
			
			
		}
		c.close();
	}
	
	public static SQLiteDatabase getSqldb() {
		return sqldb;
	}

	public static void setSqldb(SQLiteDatabase database) {
		sqldb = database;
	}

	public static Exception getErr() {
		return err;
	}

	public static void setErr(Exception error) {
		err = error;
	}

	public DbHelp(Context c) {
		super(c, "DB1", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		setSqldb(db);
	}

	public void setData(List<Details> list) {

		
		sqldb=this.getWritableDatabase();
		sqldb.execSQL("drop table phoneDetails");
		sqldb.execSQL("create table phoneDetails(deptName VARCHAR unique, deptDetails VARCHAR, extCode INTEGER)");
		for(int i=0;i<list.size();i++){
			sqldb.execSQL("insert into phoneDetails values('"+list.get(i).getDeptName()+"','"+list.get(i).getDeptDetails()+"','"+list.get(i).getExtCode()+"')");
			
		}
		
	}
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
