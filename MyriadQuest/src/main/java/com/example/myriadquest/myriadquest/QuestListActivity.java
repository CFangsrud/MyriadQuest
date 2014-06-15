package com.example.myriadquest.myriadquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;


public class QuestListActivity extends ListActivity {

    public static final int ALIGNMENT_UPDATED = 1;

    private QuestData[] questList;
    private QuestDataArrayAdapter questListAdapter;

    private ParseUser user;
    private QuestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_list);

        user = ParseUser.getCurrentUser();

        // See ALL quests
        adapter = new QuestAdapter(this, new ParseQueryAdapter.QueryFactory<ParseObject>(){
            @Override
            public ParseQuery create() {
                ParseQuery query = new ParseQuery(QuestApp.QUEST_DATABASE);
                query.include("User.name");
                return query;
            }
        });

        setListAdapter(adapter);

        loadQuestData();        // Load all quests into questList
        questListAdapter = new QuestDataArrayAdapter(this, getVisibleQuestList());
    }

    /** Get the quest data for the list adapter, storing them in questList
     *
     * At the moment, only "offline" data.
     */
    private void loadQuestData() {
        questList = new QuestData[3];

        questList[0] = new QuestData(getResources().getStringArray(R.array.quest0));
        questList[1] = new QuestData(getResources().getStringArray(R.array.quest1));
        questList[2] = new QuestData(getResources().getStringArray(R.array.quest2));

        return;
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

        QuestData quest = questListAdapter.getItem(position);
        intent.putExtra(QuestData.QUEST, quest);

        startActivity(intent);
    }

    /** Update the list of visible quests based on user alignment. **/
    private void updateListVisibility(){
        questListAdapter.clear();
        ArrayList<QuestData> visibleQuests = getVisibleQuestList();
        
        for (QuestData v : visibleQuests)
            questListAdapter.add(v);
        
        questListAdapter.notifyDataSetChanged();
    }

    /** Return an ArrayList<QuestData> containing only the quests which are visible according
     *  to the hero's alignment.
     */
    private ArrayList<QuestData> getVisibleQuestList(){
        ArrayList<QuestData> visibleList = new ArrayList<QuestData>();

        SharedPreferences savedSettings = getSharedPreferences("accountSettings", MODE_PRIVATE);
        int heroAlign = savedSettings.getInt(QuestApp.ALIGNMENT_KEY, 1);

        for (QuestData quest : questList){
            String questAlign = quest.getAlignment().toLowerCase();

            switch (heroAlign){
                case 0:     // Good alignment, only show Good quests
                    if (questAlign.equalsIgnoreCase("good"))
                        visibleList.add(quest);
                    break;
                case 2:     // Evil alignment, only show Evil quests
                    if (questAlign.equalsIgnoreCase("evil"))
                        visibleList.add(quest);
                    break;
                default:
                case 1:     // Neutral alignment, show all quests
                    visibleList.add(quest);
                    break;
            }
        }

        visibleList.trimToSize();

        return visibleList;
    }
}
