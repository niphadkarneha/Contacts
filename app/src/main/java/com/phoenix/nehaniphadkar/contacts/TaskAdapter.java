package com.phoenix.nehaniphadkar.contacts;


import android.content.Context;
import android.content.Intent;
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


@SuppressWarnings("ALL")
public class TaskAdapter extends ArrayAdapter<Contact> {
private Context context;
private List<Contact> tasks;
    static ArrayList<String> selectedStrings;
    public static ArrayList<String> numberstring;



    public TaskAdapter(@NonNull Context context, List<Contact> tasks) {
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
                    selectedStrings = new ArrayList<String>();
                    numberstring = new ArrayList<String>();

                    for (int i = 0; i < tasks.size(); i++) {
                        if (tasks.get(i).ischecked){
                            selectedStrings.add(tasks.get(i).getName());

                            numberstring.add(tasks.get(i).getNumber());
                        }
                    }
                    System.out.println("sliderString"+selectedStrings);

                }else{
                   tasks.get(position).setIschecked(false);
                    selectedStrings = new ArrayList<String>();
                    numberstring = new ArrayList<String>();

                    for (int i = 0; i < tasks.size(); i++) {
                        if (tasks.get(i).ischecked){
                            selectedStrings.add(tasks.get(i).getName());
                            numberstring.add(tasks.get(i).getNumber());

                        }
                    }
                    System.out.println("sliderString"+selectedStrings);

                }

            }
        });

        tvtitle.setText(tasks.get(position).getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent=new Intent(context,ContactInformation.class);
                DBHelper helper = new DBHelper(context);
                helper.getContact(tasks.get(position).getName());
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

   static ArrayList<String> getSelectedString(){
        return selectedStrings;
    }
}






