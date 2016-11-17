package com.albertbelardino.webviewlab;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements WebFragment.OnFragmentInteractionListener {

    public ArrayList<WebFragment> fragArray = new ArrayList<WebFragment>();
    public int cur = 0;
    public FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar)findViewById(R.id.webToolbar));

        fm = getFragmentManager();
        WebFragment wf = WebFragment.newInstance();
        fragArray.add(wf);

        fm.beginTransaction()
                .add(R.id.webLayout, WebFragment.newInstance())
                .commit();

        ((Button)findViewById(R.id.goButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ((EditText)findViewById(R.id.urlText)).getText().toString();
                WebFragment wf = WebFragment.newInstance();
                wf.setUrl(url);
                fragArray.add(wf);
                fm.beginTransaction().add(R.id.webLayout, wf).commit();
                cur++;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.web_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.backButton:
                if(cur > 0){
                    fm.beginTransaction().replace(R.id.webLayout, fragArray.get(--cur)).commit();
                }
                return true;
            case R.id.newButton:
                cur++;
                WebFragment wf = WebFragment.newInstance();
                fm.beginTransaction().add(R.id.webLayout, wf).commit();;
                fragArray.add(wf);
                return true;
            case R.id.forwardButton:
                if(cur < (fragArray.size() - 1)){
                    fm.beginTransaction().replace(R.id.webLayout, fragArray.get(++cur)).commit();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
