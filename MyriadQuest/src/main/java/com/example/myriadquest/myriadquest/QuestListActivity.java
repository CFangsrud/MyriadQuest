package com.example.myriadquest.myriadquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;


public class QuestListActivity extends ActionBarActivity {

    public static final int ALIGNMENT_UPDATED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_list);

        updateListVisibility();
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
            startActivityForResult(intent, ALIGNMENT_UPDATED);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == ALIGNMENT_UPDATED && resultCode == RESULT_OK){
            updateListVisibility();
        }
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
        String questAlignment;

        switch(chosenQuest){
            case 1:
                questName = getResources().getString(R.string.quest1Name);
                questGiver = getResources().getString(R.string.quest1Giver);
                questDescription = getResources().getString(R.string.quest1Description);
                questAlignment = getResources().getString(R.string.quest1Alignment);
                break;
            case 2:
                questName = getResources().getString(R.string.quest2Name);
                questGiver = getResources().getString(R.string.quest2Giver);
                questDescription = getResources().getString(R.string.quest2Description);
                questAlignment = getResources().getString(R.string.quest2Alignment);
                break;
            case 3:
                questName = getResources().getString(R.string.quest3Name);
                questGiver = getResources().getString(R.string.quest3Giver);
                questDescription = getResources().getString(R.string.quest3Description);
                questAlignment = getResources().getString(R.string.quest3Alignment);
                break;
            default:
                questName = "No Quest (" + chosenQuest + ")";
                questGiver = "No Quest Giver";
                questDescription = "No Description";
                questAlignment = "No Alignment";
        }

        Intent intent = new Intent(this, QuestDetailActivity.class);
        intent.putExtra(QuestData.QUEST_NAME, questName);
        intent.putExtra(QuestData.QUEST_GIVER, questGiver);
        intent.putExtra(QuestData.QUEST_DESCRIPTION, questDescription);
        intent.putExtra(QuestData.QUEST_ALIGNMENT, questAlignment);
        startActivity(intent);
    }

    /** Update the quest list based on user alignment. **/
    private void updateListVisibility(){

        SharedPreferences savedSettings = getSharedPreferences("accountSettings", MODE_PRIVATE);
        int alignment = savedSettings.getInt(SettingsActivity.ALIGNMENT_KEY, 1);

        RelativeLayout quest;

        // Show quest 1 if Good or Neutral
        quest = (RelativeLayout) findViewById(R.id.Quest1Layout);
        if (alignment < 2){
            quest.setVisibility(View.VISIBLE);
        } else {
            quest.setVisibility(View.GONE);
        }
        // Show quest 2 if Neutral
        quest = (RelativeLayout) findViewById(R.id.Quest2Layout);
        if (alignment == 1){
            quest.setVisibility(View.VISIBLE);
        } else {
            quest.setVisibility(View.GONE);
        }
        // Show quest 3 if Neutral or Evil
        quest = (RelativeLayout) findViewById(R.id.Quest3Layout);
        if (alignment > 0){
            quest.setVisibility(View.VISIBLE);
        } else {
            quest.setVisibility(View.GONE);
        }
    }
}
