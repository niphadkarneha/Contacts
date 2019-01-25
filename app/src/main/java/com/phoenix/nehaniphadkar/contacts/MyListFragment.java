package com.phoenix.nehaniphadkar.contacts;

/**
 * Created by Afzal on 02-Mar-18.
 */

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyListFragment extends Fragment implements View.OnClickListener {
    private ListView listcontact;
    private Button btnadd;
    private Button btndelete;
    private List<Contact> tasks;
    private OnItemSelectedListener listener;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.contacts,
                container, false);
        initUI();

        initListener();
        displyOnresume();
        Button button = (Button) view.findViewById(R.id.updateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetail();
            }
        });
        return view;
    }



    public interface OnItemSelectedListener {
        void onRssItemSelected(String text);
        void onRefresh();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    // triggers update of the details fragment
    public void updateDetail() {
        // create fake data
        String newTime = String.valueOf(System.currentTimeMillis());
        // send data to activity
        listener.onRssItemSelected(newTime);
    }
    private void initListener() {
        btnadd.setOnClickListener(this);
        btndelete.setOnClickListener(this);

    }

    private void initUI() {
        TaskAdapter.selectedStrings=new ArrayList<>();
        listcontact = (ListView) view.findViewById(R.id.card_recycler_view);
        btnadd = (Button) view.findViewById(R.id.btnid);
        btndelete = (Button) view.findViewById(R.id.btndelete);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnid:
                listener.onRssItemSelected("sdxszz");

                break;
            case R.id.btndelete:delete();
        }
    }

    private void delete() {

        ArrayList<String> stringsddd= MyAdapter.newselectedStrings;


        DBHelper helper = new DBHelper(getActivity());
        if (helper.deleteTask(stringsddd)) {
            Toast.makeText(getActivity(), " Task deleted successfully", Toast.LENGTH_LONG).show();
            listener.onRefresh();

            displyOnresume();

        } else {
            Toast.makeText(getActivity(), "Not deleted", Toast.LENGTH_LONG).show();
        }




    }
    private void displyOnresume() {
        DBHelper database1 = new DBHelper(getActivity());
        tasks = database1.showList();
        if (!tasks.equals(null)) {
            listcontact.setAdapter(new MyAdapter(getActivity(), tasks, new Tasklistner() {
                @Override
                public void onclick() {

                }
            }));
        } else {
            listcontact.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public void onResume() {
        super.onResume();

    }
}