package com.mmrcl.phonecodeapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mmrcl.phonecodeapp.util.DbHelp;

public class AddDataActivity extends Activity {
    Button add;
    EditText deptName,mobile;
    TextView deptDetails,titlem, landline, extCode;
    SQLiteDatabase sql;
    String opration="";
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView title = (TextView) findViewById(titleId);
        /*Typeface titlefont = Typeface.createFromAsset(getAssets(),
                "font/bulletto.ttf");
        title.setTypeface(titlefont);*/
        submit=(Button)findViewById(R.id.save);
        deptName=(EditText)findViewById(R.id.editText1);
        deptDetails=(TextView) findViewById(R.id.editText2);
        mobile=(EditText)findViewById(R.id.mobile);
        landline=(TextView) findViewById(R.id.landno);
        extCode=(TextView) findViewById(R.id.ext);


        titlem=(TextView)findViewById(R.id.textView1);
        /*Typeface font2 = Typeface.createFromAsset(getAssets(),
                "font/candy.ttf");
        titlem.setTypeface(font2);
*/
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        try{
            if(this.getIntent().getStringExtra("opration").equals("update")){
                //add.setText("Update");
                opration="UPDATE";
                extCode.setEnabled(false);
                Details details= (Details) getIntent().getSerializableExtra("details");
                deptName.setText(details.getDeptName());
                deptDetails.setText(details.getDeptDetails());
                mobile.setText(details.getMobileNo());
                landline.setText(details.getLandline());
                extCode.setText(details.getExtCode());
            }else{
                extCode.setEnabled(false);
            }

        }
        catch (Exception e) {
            //self created ....
        }
        try{
            sql=openOrCreateDatabase("DB1",MODE_PRIVATE, null);
            sql.execSQL("create table IF NOT EXISTS phoneDetails(deptName VARCHAR unique, deptDetails VARCHAR(80), extCode VARCHAR)");
        }catch(Exception e){

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.add_data, menu);
        return true;
    }

    public void save(){
        Details details= new Details();
        if(extCode.getText()!=null && extCode.getText().toString()!="")
            details.setExtCode(extCode.getText().toString());
        details.setLandline(landline.getText().toString());
        details.setMobileNo(mobile.getText().toString());
        details.setDeptDetails(deptDetails.getText().toString());
        details.setDeptName(deptName.getText().toString());
        if(details.valid())
        {
            if(opration.equals("UPDATE")){
                DbHelp.update(details,((Details) getIntent().getSerializableExtra("details")).deptName);
                Toast.makeText(getApplicationContext(),"Updated Successfully "+details , Toast.LENGTH_SHORT).show();

            }else{
                DbHelp.insert(details);
                Toast.makeText(getApplicationContext(),"Added Successfully" , Toast.LENGTH_SHORT).show();
            }/*try {
			select("select * from phoneDetails");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(),"Please insert correct data" , Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            /*case R.id.action_save:
                save();
                return true;
*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void select(String qury)throws Exception
    {
        Cursor c=sql.rawQuery(qury, null);
        TableLayout tableLayout = new TableLayout(getApplicationContext());
        tableLayout.setVerticalScrollBarEnabled(true);
        TableRow tableRow;
        TextView textView;
        int no=c.getColumnCount();
        tableRow = new TableRow(getApplicationContext());
        textView=new TextView(getApplicationContext());
        textView.setTypeface(null, Typeface.BOLD_ITALIC);
        textView.setTextColor(Color.RED);
        textView.setTextSize(16);
        textView.setText("TABLE : "+"phoneDetails"+"  ");
        tableRow.addView(textView);
        tableLayout.addView(tableRow);
        tableRow = new TableRow(getApplicationContext());
        for(int i=0;i<no;i++)
        {
            textView=new TextView(getApplicationContext());
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextColor(Color.BLACK);
            textView.setText(c.getColumnName(i)+" ");
            tableRow.addView(textView);
        }
        tableLayout.addView(tableRow);

        if(c.moveToFirst())
        {
            do
            {tableRow = new TableRow(getApplicationContext());
                for(int i=0;i<no;i++)
                {
                    textView=new TextView(getApplicationContext());
                    textView.setTypeface(null, Typeface.NORMAL);
                    textView.setTextColor(Color.BLACK);
                    try{
                        switch(i)
                        {
                            case 0:
                                textView.setText(c.getString(i)+" ");
                                break;
                            case 1:
                                textView.setText(c.getString(i)+" ");
                                break;
                            case 2:
                                textView.setText(c.getInt(i)+" ");
                                break;
                            default :
                                textView.setText("Missing");
                        }
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                    tableRow.addView(textView);

                }
                tableLayout.addView(tableRow);
            }while(c.moveToNext());
        }
        tableRow = new TableRow(getApplicationContext());
        //b2=new Button(getApplicationContext());
        //b2.setText("Back");
        //b2.setOnClickListener(this);
        //tableRow.addView(b2);
        tableLayout.addView(tableRow);
        setContentView(tableLayout);
    }

}
