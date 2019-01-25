package com.phoenix.nehaniphadkar.contacts;

/**
 * Created by Afzal on 02-Mar-18.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailsFragment extends Fragment {
    public static final String EXTRA_TEXT ="text";
    private EditText edtname;
    private EditText edtnumber;
    private Button btnadd;
    private String name;
    private String number;
    private ListView lv;
    private List<Contact> tasks;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_details,
                container, false);
        initUI();
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtname.getText().toString().matches("")){
                    edtname.setError("please enter name");

                }
                else if(edtnumber.getText().toString().matches("")){
                    edtnumber.setError("please enter number");
                }else {
                    addData();
                }
            }
        });
        show();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String text = bundle.getString(EXTRA_TEXT);
            //setText(text);
        }
    }

    public void setText(String text) {
        TextView view = (TextView) getView().findViewById(R.id.detailsText);
        view.setText(text);
    }
    private void addData() {

        name = edtname.getText().toString();
        number = edtnumber.getText().toString();
        Contact task = new Contact();
        task.setName(name);
        task.setNumber(number);
        ArrayList<JSONObject>  jsonObjects=new ArrayList<>();
        for (int i = 0; i < TaskAdapter.selectedStrings.size(); i++) {
            try {
                JSONObject jsonObject=new JSONObject();

                jsonObject.put("name",TaskAdapter.selectedStrings.get(i));
                jsonObject.put("number",TaskAdapter.numberstring.get(i));
                jsonObjects.add(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println("dsewas"+jsonObjects.toString());
        task.setJson(jsonObjects.toString());
        DBHelper database = new DBHelper(getActivity());
        if (database.insertData(task)) {
            Toast.makeText(getActivity(), "Task inserted successfully", Toast.LENGTH_LONG).show();
            edtname.setText("");
            edtnumber.setText("");
            display();
        } else {
            Toast.makeText(getActivity(), " Task not inserted", Toast.LENGTH_LONG).show();
        }


    }

    private void display() {

        DBHelper database1 = new DBHelper(getActivity());

        lv.setAdapter(new TaskAdapter(getActivity(), database1.showList()));


    }

    private void initUI() {
        edtname=(EditText)view.findViewById(R.id.edt_title);
        edtnumber=(EditText)view.findViewById(R.id.edt_number);
        btnadd=(Button)view.findViewById(R.id.btnaddc);
        lv=(ListView)view.findViewById(R.id.card_recycler_view_contact);

    }



    public void show() {

        DBHelper database1 = new DBHelper(getActivity());
        tasks = database1.showList();
        if (!tasks.equals(null)) {
            lv.setAdapter(new TaskAdapter(getActivity(), tasks));
        } else {
            lv.setVisibility(View.INVISIBLE);
        }

    }
}
