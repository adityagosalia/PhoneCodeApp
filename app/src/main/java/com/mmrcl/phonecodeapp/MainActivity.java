package com.mmrcl.phonecodeapp;

import java.util.ArrayList;
import java.util.Locale;

import com.mmrcl.phonecodeapp.util.DbHelp;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
    AutoCompleteTextView textView;
    SQLiteDatabase sql;
    ListView list;
    Button addBtn;
    static Context mainContext;
    boolean isConnected=false;
    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView title = (TextView) findViewById(titleId);
       /* Typeface titlefont = Typeface.createFromAsset(getAssets(),
                "font/bulletto.ttf");
        title.setTypeface(titlefont);*/


        mainContext=getApplicationContext();
        textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        list = (ListView) findViewById(R.id.listView1);
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
        //.permitAll().build();

        //StrictMode.setThreadPolicy(policy);
        /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
           // updateListFromServer();
            DbHelp.setServerData();

            // mark first time has runned.

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();


        }*/

        DbHelp.load(getApplicationContext());
        ArrayAdapter<String> adapter;
        try {

            if(DbHelp.searchlist.size()>0){
                adapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.simple_list, DbHelp.searchlist);

                textView.setAdapter(adapter);
                textView.setThreshold(1);

                textView.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> av, View view,
                                            int index, long id) {
                        //Toast.makeText(getApplicationContext(), av.getItemAtPosition(index).toString().trim(), 4000).show();
                        ListElementAdapter adapter;

                        try {

                            Cursor c = DbHelp.getSqldb().rawQuery(
                                    "select * from phoneDetails where deptName='"
                                            + av.getItemAtPosition(index).toString() + "'",
                                    null);
                            if (c.getCount() == 0) {
                                c = DbHelp.getSqldb().rawQuery(
                                        "select * from phoneDetails where extCode='"
                                                + av.getItemAtPosition(index).toString().trim() + "'",
                                        null);
                            }
                            DbHelp.setFilterList(c);
                            adapter = new ListElementAdapter(DbHelp.filterlist, getApplicationContext());

                            list.setAdapter(adapter);

                        } catch (Exception e) {

                            //Toast.makeText(getApplicationContext(), e.toString(),
                            //Toast.LENGTH_LONG).show();
                        }
                    }

                });

                ListElementAdapter listadapter = new ListElementAdapter(DbHelp.showlist, getApplicationContext());

                list.setAdapter(listadapter);
            }
        } catch (Exception e) {

            //Toast.makeText(getApplicationContext(), e.toString(),
            //Toast.LENGTH_LONG).show();
        }



        textView.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                if(textView.getText().toString()!="")
                {
                    DbHelp.filterlist=new ArrayList<String[]>();
                    for(int i=0;i<DbHelp.data.size();i++)
                    {
                        if(DbHelp.data.get(i).isPresent(textView.getText().toString()))
                        {
                            String[] values=new String[2];
                            values[0]=DbHelp.data.get(i).getDeptName();
                            values[1]=DbHelp.data.get(i).getMobileNo();
                            DbHelp.filterlist.add(values);
                        }
                    }
                    ListElementAdapter adapter = new ListElementAdapter(DbHelp.filterlist, getApplicationContext());

                    list.setAdapter(adapter);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                ViewHolder viewHolder=(ViewHolder) view.getTag();
                //String item = ((TextView) view).getText().toString();
                Intent intent = new Intent(getApplicationContext(),
                        ShowActivity.class);


                Cursor c = DbHelp.getSqldb().rawQuery(
                        "select * from phoneDetails where deptName='"
                                + viewHolder.title.getText().toString() + "'", null);

                if (c.moveToNext()) {
                    Details details=new Details();
                    details.setDeptName(c.getString(0));
                    details.setDeptDetails(c.getString(1));
                    details.setMobileNo(c.getString(2));
                    details.setLandline(c.getString(3));
                    details.setExtCode(c.getString(4));

                    intent.putExtra("details",details);
                    startActivity(intent);
                    //finish();
                }

            }
        });
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textView.setText(result.get(0));
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            /*case R.id.item1:
                //new UpdateServiceTask(MainActivity.this).execute(20);
                //updateFromNet();
                //updateListFromServer();

                return true;*/

            case R.id.action_add:
                Intent intent=new Intent(getApplicationContext(), AddDataActivity.class);
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateListFromServer(){
        ConnectivityManager check = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = check.getAllNetworkInfo();
        for (int i = 0; i<info.length; i++){
            if (info[i].getState() == NetworkInfo.State.CONNECTED){
                Toast.makeText(getApplicationContext(), "Internet is connected",Toast.LENGTH_SHORT).show();
                isConnected=true;
            }
        }
        if(isConnected){
            NetService netService=new NetService();
            Thread t=new Thread(netService);
            t.start();
            new UpdateServiceTask(MainActivity.this).execute(2000);
            Intent intent = getIntent();
            intent.putExtra("command", "main");
            finish();
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"internet is not avalible",
                    Toast.LENGTH_LONG).show();
        }

    }
class NetService extends Thread
{
    @Override
    public void run() {
        super.run();
       // updateListFromServer();
    }
}
}
