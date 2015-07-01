package com.example.mahae_000.salatuk;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;


public class MainActivity2 extends ActionBarActivity {
Start_view grafix;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        grafix=new Start_view(this);
        setContentView(grafix);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Intent startmenu = new Intent("com.example.solarpower.MenuAc");
        Intent startmenu = new Intent(MainActivity2.this,MainActivity.class);
        startActivity(startmenu);

        // TODO Auto-generated method stub
        return super.onTouchEvent(event);
    }
}
