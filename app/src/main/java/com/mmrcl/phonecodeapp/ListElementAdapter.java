package com.mmrcl.phonecodeapp;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
public class ListElementAdapter extends BaseAdapter{

	List<String[]> data;
    Context context;
    LayoutInflater layoutInflater;
    TextView txt,no;

    public ListElementAdapter(List<String[]> data, Context context) {
        super();
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView= layoutInflater.inflate(R.layout.custom_list, null);
  	 
  		
  	    
  	    
        ViewHolder viewHolder=new ViewHolder();
        viewHolder.title=(TextView)convertView.findViewById(R.id.title);

        viewHolder.number=(TextView)convertView.findViewById(R.id.number);
        viewHolder.title.setText(data.get(position)[0]);
        viewHolder.number.setText(data.get(position)[1]);

        convertView.setTag(viewHolder);
        return convertView;
    }

	

	

}
