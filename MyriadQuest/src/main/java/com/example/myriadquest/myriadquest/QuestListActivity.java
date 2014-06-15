package com.example.myriadquest.myriadquest;

import android.content.Intent;
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


public class QuestListActivity extends ListActivity {

    public static final int ALIGNMENT_UPDATED = 1;

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
                query.include("_User");
                return query;
            }
        });

        setListAdapter(adapter);
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

        String questId = adapter.getItem(position).getObjectId();
        intent.putExtra(QuestApp.OBJECTID_KEY, questId);

        startActivity(intent);
    }

    /** Update the list of visible quests based on user alignment
     * and the desired quest status. **/
    private void updateListVisibility(){

    }
}
