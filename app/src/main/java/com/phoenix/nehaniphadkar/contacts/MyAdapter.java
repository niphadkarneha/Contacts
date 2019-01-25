package com.phoenix.nehaniphadkar.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Afzal on 02-Mar-18.
 */

public class MyAdapter  extends ArrayAdapter<Contact> {
    private Context context;
    private List<Contact> tasks;
    private MyAdapter.ChangeFilterMainNumber listerner;
    static ArrayList<String> newselectedStrings;

    public interface ChangeFilterMainNumber {
        void OnChangeFilterMainNumberListener(String name,String number,String json);
    }


    public MyAdapter(@NonNull Context context, List<Contact> tasks, Tasklistner tasklistner) {
        super(context, R.layout.task_item,tasks);
        this.context=context;
        this.tasks=tasks;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.task_item,parent,false);
        TextView tvtitle=(TextView)view.findViewById(R.id.tv_title);
    /*    selectedStrings=new ArrayList<>();
        numberstring=new ArrayList<>();*/
/*
        TextView tvdescription=(TextView)view.findViewById(R.id.tv_desc);
*/
        final CheckBox checkBox=(CheckBox)view.findViewById(R.id.ch_delete);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if (b) {
                    tasks.get(position).setIschecked(true);
                    newselectedStrings = new ArrayList<String>();

                    for (int i = 0; i < tasks.size(); i++) {
                        if (tasks.get(i).ischecked){
                            newselectedStrings.add(tasks.get(i).getName());
                        }
                    }
                    System.out.println("sliderString"+newselectedStrings);

                }else{
                    tasks.get(position).setIschecked(false);
                    newselectedStrings = new ArrayList<String>();

                    for (int i = 0; i < tasks.size(); i++) {
                        if (tasks.get(i).ischecked){
                            newselectedStrings.add(tasks.get(i).getName());

                        }
                    }
                    System.out.println("sliderString"+newselectedStrings);

                }

            }
        });

        tvtitle.setText(tasks.get(position).getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intent=new Intent(context,ContactInformation.class);
                DBHelper helper = new DBHelper(context);
                Contact contact=helper.getContact(tasks.get(position).getName());
            String name=contact.getName();
               String number= contact.getNumber();
               String json= contact.getJson();
                System.out.println("sdxaszx"+name+number+json);
                listerner = (MyAdapter.ChangeFilterMainNumber) context;

                listerner.OnChangeFilterMainNumberListener(name,number,json);

                /*intent.putExtra("name",tasks.get(position).getName());
                intent.putExtra("number",tasks.get(position).getNumber());*/
                // context.startActivity(intent);


            }
        });
/*
        tvdescription.setText(tasks.get(position).getDescription());
*/
        return view;

    }


}

