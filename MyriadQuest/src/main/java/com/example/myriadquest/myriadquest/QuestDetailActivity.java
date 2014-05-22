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

        TextView questName = (TextView) findViewById(R.id.name);
        TextView questGiver = (TextView) findViewById(R.id.giver);
        TextView questDescription = (TextView) findViewById(R.id.description);

        Intent intent = getIntent();
        int chosenQuest = intent.getIntExtra(QuestListActivity.QUEST_NUMBER,0);

        switch(chosenQuest){
            case 1:
                questName.setText(getResources().getString(R.string.quest1Name));
                questGiver.setText(getResources().getString(R.string.quest1Giver));
                questDescription.setText(getResources().getString(R.string.quest1Description));
                break;
            case 2:
                questName.setText(getResources().getString(R.string.quest2Name));
                questGiver.setText(getResources().getString(R.string.quest2Giver));
                questDescription.setText(getResources().getString(R.string.quest2Description));
                break;
            case 3:
                questName.setText(getResources().getString(R.string.quest3Name));
                questGiver.setText(getResources().getString(R.string.quest3Giver));
                questDescription.setText(getResources().getString(R.string.quest3Description));
                break;
            default:
                questName.setText("No Quest (" + chosenQuest + ")");
                questGiver.setText("No Quest Giver");
                questDescription.setText("No Description");
        }
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
