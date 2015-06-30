package com.example.mahae_000.salatuk;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            Intent intent= getActivity().getIntent();
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            if(intent!=null && intent.hasExtra(Intent.EXTRA_TEXT))
            {
                String data_intent =intent.getStringExtra(Intent.EXTRA_TEXT);
                ((TextView)(rootView.findViewById(R.id.detail_textview))).setText(data_intent);

                //String intent_desc =intent.getStringExtra("desc");
                ((TextView)(rootView.findViewById(R.id.desc2_textview))).setText("");

                //String intent_before =intent.getStringExtra("before");
                ((TextView)(rootView.findViewById(R.id.before_textview))).setText("");

                //String intent_fard =intent.getStringExtra("fard");
                ((TextView)(rootView.findViewById(R.id.fard_textview))).setText("");

                //String intent_after =intent.getStringExtra("after");
                ((TextView)(rootView.findViewById(R.id.after_textview))).setText("");

                //String intent_plural =intent.getStringExtra("plural");
                ((TextView)(rootView.findViewById(R.id.plural_textview))).setText("");

            }

            return rootView;
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

           if (id == R.id.action_settings) {
                //Intent intent = new Intent(getActivity(), SettingsActivity.class);
               Log.v("settings in detail",String.valueOf(id));
               Intent intent = new Intent(getActivity(), SettingsActivity.class);
               startActivity(intent);
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
