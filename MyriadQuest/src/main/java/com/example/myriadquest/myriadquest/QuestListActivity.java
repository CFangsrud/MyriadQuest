package com.example.myriadquest.myriadquest;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class QuestListActivity extends ActionBarActivity {

    public static final String QUEST_NAME = "com.example.myriadquest.NAME";
    public static final String QUEST_GIVER = "com.example.myriadquest.GIVER";
    public static final String QUEST_DESCRIPTION = "com.example.myriadquest.DESCRIPTION";

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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Quest 1 selected. **/
    public void selectQuest1(View view) {
        startQuestDetailActivity(1);
    }

    /** Quest 2 selected. **/
    public void selectQuest2(View view) {
        startQuestDetailActivity(2);
    }

    /** Quest 3 selected. **/
    public void selectQuest3(View view) {
        startQuestDetailActivity(3);
    }

    /** Get the details for the selected quest and start QuestDetailActivity. **/
    private void startQuestDetailActivity(int chosenQuest){
        String questName;
        String questGiver;
        String questDescription;

        switch(chosenQuest){
            case 1:
                questName = getResources().getString(R.string.quest1Name);
                questGiver = getResources().getString(R.string.quest1Giver);
                questDescription = getResources().getString(R.string.quest1Description);
                break;
            case 2:
                questName = getResources().getString(R.string.quest2Name);
                questGiver = getResources().getString(R.string.quest2Giver);
                questDescription = getResources().getString(R.string.quest2Description);
                break;
            case 3:
                questName = getResources().getString(R.string.quest3Name);
                questGiver = getResources().getString(R.string.quest3Giver);
                questDescription = getResources().getString(R.string.quest3Description);
                break;
            default:
                questName = "No Quest (" + chosenQuest + ")";
                questGiver = "No Quest Giver";
                questDescription = "No Description";
        }

        Intent intent = new Intent(this, QuestDetailActivity.class);
        intent.putExtra(QUEST_NAME, questName);
        intent.putExtra(QUEST_GIVER, questGiver);
        intent.putExtra(QUEST_DESCRIPTION, questDescription);
        startActivity(intent);
    }

}
