package com.phoenix.nehaniphadkar.contacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity implements
        MyListFragment.OnItemSelectedListener,MyAdapter.ChangeFilterMainNumber {

    SelectionStateFragment stateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stateFragment =
                (SelectionStateFragment) getFragmentManager()
                        .findFragmentByTag("headless");

        if(stateFragment == null) {
            stateFragment = new SelectionStateFragment();
            getFragmentManager().beginTransaction()
                    .add(stateFragment, "headless").commit();
        }

        if (getResources().getBoolean(R.bool.twoPaneMode)) {
            // restore state
            if (stateFragment.lastSelection.length()>0) {
                onRssItemSelected(stateFragment.lastSelection);
            }
            // otherwise all is good, we use the fragments defined in the layout
            return;
        }
        // if savedInstanceState is null we do some cleanup
        if (savedInstanceState != null) {
            // cleanup any existing fragments in case we are in detailed mode
            getFragmentManager().executePendingTransactions();
            Fragment fragmentById = getFragmentManager().
                    findFragmentById(R.id.fragment_container);
            if (fragmentById!=null) {
                getFragmentManager().beginTransaction()
                        .remove(fragmentById).commit();
            }
        }

        MyListFragment listFragment = new MyListFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, listFragment).commit();
    }

    @Override
    public void onRssItemSelected(String text) {
        stateFragment.lastSelection = text;
        if (getResources().getBoolean(R.bool.twoPaneMode)) {
            DetailsFragment fragment = (DetailsFragment) getFragmentManager()
                    .findFragmentById(R.id.detailFragment);
            fragment.setText(text);
        } else {
            // replace the fragment
            // Create fragment and give it an argument for the selected article
            DetailsFragment newFragment = new DetailsFragment();
            Bundle args = new Bundle();
            args.putString(DetailsFragment.EXTRA_TEXT, text);

            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back

            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    public void onRefresh() {
        MyListFragment test = (MyListFragment) getFragmentManager().findFragmentById(R.id.listFragment);
        if (test != null && test.isVisible()) {
            //DO STUFF
            DetailsFragment fragment = (DetailsFragment) getFragmentManager()
                    .findFragmentById(R.id.detailFragment);
            fragment.show();
        }


    }

    public void onResume(){
        super.onResume();
       /* getFragmentManager().beginTransaction().detach(new MyListFragment()).attach(new MyListFragment()).commit();
        getFragmentManager().beginTransaction().detach(new DetailsFragment()).attach(new DetailsFragment()).commit();
*/

    }

    @Override
    public void OnChangeFilterMainNumberListener(String name, String number, String json) {
        if (getResources().getBoolean(R.bool.twoPaneMode)) {
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.remove(new DetailsFragment());

            ft.add(R.id.detailFragment, new DescriptionFragment(), "NewFragmentTag");
            ft.commit();
            /*DetailsFragment fragment = (DetailsFragment) getFragmentManager()
                    .findFragmentById(R.id.detailFragment);
            fragment.setText(text);*/
        } else {
            // replace the fragment
            // Create fragment and give it an argument for the selected article
            DescriptionFragment newFragment = new DescriptionFragment();
            /*Bundle args = new Bundle();
            args.putString(DetailsFragment.EXTRA_TEXT, text);
            newFragment.setArguments(args);*/
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            Bundle args = new Bundle();

            args.putString("name", name);
            args.putString("number", number);
            args.putString("json", json);
            newFragment.setArguments(args);

            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }

    }
}