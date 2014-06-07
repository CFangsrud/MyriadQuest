package com.example.myriadquest.myriadquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


public class QuestListActivity extends ListActivity {

    private QuestData[] questList;
    public static final int ALIGNMENT_UPDATED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_list);

        questList = loadQuestData();

        setListAdapter(new QuestDataArrayAdapter(this, questList));
        updateListVisibility();
    }

    /** Get the quest data for the list adapter.
     *
     * At the moment, only "offline" data.
     */
    private QuestData[] loadQuestData() {
        QuestData[] questList = new QuestData[3];

        String[] questStrings = getResources().getStringArray(R.array.quest0);
        questList[0] = new QuestData(questStrings);

        questStrings = getResources().getStringArray(R.array.quest1);
        questList[1] = new QuestData(questStrings);

        questStrings = getResources().getStringArray(R.array.quest2);
        questList[2] = new QuestData(questStrings);

        return questList;
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

    @Override
    /** Get the details for the selected quest and start QuestDetailActivity. **/
    public void onListItemClick(ListView l, View v, int position, long id){
        Intent intent = new Intent(this, QuestDetailActivity.class);

        QuestData quest = questList[position];
        intent.putExtra(QuestData.QUEST_NAME, quest.getName());
        intent.putExtra(QuestData.QUEST_ALIGNMENT, quest.getAlignment());
        intent.putExtra(QuestData.QUEST_DESCRIPTION, quest.getDescription());
        intent.putExtra(QuestData.QUEST_GIVER, quest.getQuestGiver());

        startActivity(intent);
    }

    /** Update the quest list based on user alignment. **/
    private void updateListVisibility(){

        SharedPreferences savedSettings = getSharedPreferences("accountSettings", MODE_PRIVATE);
        int alignment = savedSettings.getInt(SettingsActivity.ALIGNMENT_KEY, 1);

        //redo alignment filtering
    }
}
