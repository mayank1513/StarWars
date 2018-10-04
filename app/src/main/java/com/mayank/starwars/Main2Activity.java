package com.mayank.starwars;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

public class Main2Activity extends Activity {
    public static final String NAME = "name", HEIGHT = "height", MASS = "mass", TIME_CREATED = "createdOn";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        actionBar.setTitle(Html.fromHtml("<i><b>" + getString(R.string.app_name) + "</b>: " + intent.getStringExtra(NAME) + "</i>"));
        ((TextView)findViewById(R.id.name)).setText(intent.getStringExtra(NAME));
        ((TextView)findViewById(R.id.height)).setText(intent.getStringExtra(HEIGHT));
        ((TextView)findViewById(R.id.mass)).setText(intent.getStringExtra(MASS));
        ((TextView)findViewById(R.id.time)).setText(intent.getStringExtra(TIME_CREATED));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return true;
    }
}
