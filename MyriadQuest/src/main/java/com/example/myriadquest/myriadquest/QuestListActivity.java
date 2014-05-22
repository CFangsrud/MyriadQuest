package com.example.myriadquest.myriadquest;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class QuestListActivity extends ActionBarActivity {

    public static final String QUEST_NUMBER = "com.example.myriadquest.NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_list);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quest_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectQuest1(View view) {
        Intent intent = new Intent(this, QuestDetailActivity.class);
        intent.putExtra(QUEST_NUMBER, 1);
        startActivity(intent);
    }

    public void selectQuest2(View view) {
        Intent intent = new Intent(this, QuestDetailActivity.class);
        intent.putExtra(QUEST_NUMBER, 2);
        startActivity(intent);
    }

    public void selectQuest3(View view) {
        Intent intent = new Intent(this, QuestDetailActivity.class);
        intent.putExtra(QUEST_NUMBER, 3);
        startActivity(intent);
    }
}