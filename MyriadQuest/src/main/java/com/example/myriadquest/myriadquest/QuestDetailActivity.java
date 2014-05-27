package com.example.myriadquest.myriadquest;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class QuestDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_detail);

        TextView questNameView = (TextView) findViewById(R.id.name);
        TextView questGiverView = (TextView) findViewById(R.id.giverName);
        TextView questDescriptionView = (TextView) findViewById(R.id.description);
        TextView questAlignmentView = (TextView) findViewById(R.id.alignmentValue);

        Intent intent = getIntent();
        String questName = intent.getStringExtra(QuestListActivity.QUEST_NAME);
        String questGiver = intent.getStringExtra(QuestListActivity.QUEST_GIVER);
        String questDescription = intent.getStringExtra(QuestListActivity.QUEST_DESCRIPTION);
        String questAlignment = intent.getStringExtra(QuestListActivity.QUEST_ALIGNMENT);

        questNameView.setText(questName);
        questGiverView.setText(questGiver);
        questDescriptionView.setText(questDescription);
        questAlignmentView.setText(questAlignment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quest_detail, menu);
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

}
