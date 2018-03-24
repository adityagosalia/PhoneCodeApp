package com.mmrcl.phonecodeapp;

import com.mmrcl.phonecodeapp.util.DbHelp;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowActivity extends Activity{
    TextView tv1,tv2;
    TextView mobileno, landline, exten;
    Button b1,b2,b3;
    Details details;
    SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        /*TextView title = (TextView) findViewById(titleId);
        Typeface titlefont = Typeface.createFromAsset(getAssets(),
                "font/bulletto.ttf");
        title.setTypeface(titlefont);*/
        details= (Details) this.getIntent().getSerializableExtra("details");
        Log.e("details_selected",details.toString());
        tv1=(TextView)findViewById(R.id.textView2);
        tv2=(TextView)findViewById(R.id.textView3);
        mobileno=(TextView)findViewById(R.id.mobileno);
        landline=(TextView)findViewById(R.id.landline);
        exten=(TextView)findViewById(R.id.exten);
        tv1.setText(details.getDeptName());
        //tv2.setText(details.getDeptDetails());
        mobileno.setText("Channel No : "+details.getMobileNo());
        //landline.setText("Land line No : "+details.getLandline());
        //exten.setText("Office phone extension  : "+details.getExtCode());

        /*mobileno.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(Intent.ACTION_DIAL);
                                            intent.setData(Uri.parse("tel:"+details.getMobileNo()));
                                            startActivity(intent);
                                        }
                                    });
        landline.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+details.getLandline()));
                startActivity(intent);
            }
        });
        exten.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+details.getExtCode()));
                startActivity(intent);
            }
        });*/

                // tv2.setTypeface(font2);
                // tv3.setTypeface(font2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent=new Intent(this,MainActivity.class);
        switch (item.getItemId()) {
            case R.id.action_delete:
                DbHelp.delete(details.deptName);

                startActivity(intent);
                finish();
                return true;
            case R.id.action_update:
                intent=new Intent(this,AddDataActivity.class);
                intent.putExtra("details",details);

                intent.putExtra("opration", "update");
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
