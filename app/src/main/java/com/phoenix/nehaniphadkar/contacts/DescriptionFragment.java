package com.phoenix.nehaniphadkar.contacts;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Afzal on 02-Mar-18.
 */

public class DescriptionFragment extends Fragment {

    private View view;
    private ListView listcontact;
    private TextView name1,number1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_description,
                container, false);
        listcontact = (ListView) view.findViewById(R.id.list);
        name1=(TextView)view.findViewById(R.id.name);
        number1=(TextView)view.findViewById(R.id.number);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.getString("name");
            String number = bundle.getString("number");
            name1.setText(name);
            number1.setText(number);

            String json = bundle.getString("json");
            try {
                JSONArray jsonArray=new JSONArray(json);
                Type listType = new TypeToken<List<Contact>>() {
                }.getType();
                List<Contact> yourList = new Gson().fromJson(json, listType);
                RelationShipAdapter relationShipAdapter=new RelationShipAdapter(getActivity(),yourList);
                listcontact.setAdapter(relationShipAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
