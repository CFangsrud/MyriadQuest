package com.example.myriadquest.myriadquest;

import android.content.Intent;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;


public class QuestListActivity extends ListActivity implements AdapterView.OnItemSelectedListener {

    public static final int ALIGNMENT_UPDATED = 1;

    private ParseUser user;
    private QuestAdapter adapterAvailableNeutral,
            adapterAvailableGood,
            adapterAvailableEvil,
            adapterAccepted,
            adapterCompleted;

    private Spinner questListSpinner;
    private int currentFilter, lastAlign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_list);

        user = ParseUser.getCurrentUser();
        lastAlign = user.getInt(QuestApp.ALIGNMENT_KEY);

        questListSpinner = (Spinner) findViewById(R.id.questSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.questListFilterOptions, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questListSpinner.setAdapter(spinnerAdapter);
        questListSpinner.setOnItemSelectedListener(this);
        questListSpinner.setSelection(currentFilter);

        getListView().setEmptyView(findViewById(R.id.empty));

        createAdapters();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ALIGNMENT_UPDATED && resultCode == RESULT_OK) {
            if (lastAlign != user.getInt(QuestApp.ALIGNMENT_KEY)) {
                lastAlign = user.getInt(QuestApp.ALIGNMENT_KEY);
                updateListVisibility();
            }
        }
    }

    @Override
    /** Get the details for the selected quest and start QuestDetailActivity. **/
    public void onListItemClick(ListView l, View v, int position, long id){
        Intent intent = new Intent(this, QuestDetailActivity.class);

        QuestAdapter adapter = (QuestAdapter) getListAdapter();
        String questId = adapter.getItem(position).getObjectId();
        intent.putExtra(QuestApp.OBJECTID_KEY, questId);

        startActivity(intent);
    }

    // Create the various needed QuestAdapters
    private void createAdapters() {
        // Available quests for a Neutral hero
        adapterAvailableNeutral = new QuestAdapter(this, new ParseQueryAdapter.QueryFactory<ParseObject>(){
            @Override
            public ParseQuery create() {
                ParseQuery query = new ParseQuery(QuestApp.QUEST_DATABASE);
                query.whereEqualTo(QuestApp.COMPLETED_KEY, false);
                query.whereDoesNotMatchKeyInQuery(QuestApp.ACCEPTEDBY_KEY, QuestApp.OBJECTID_KEY, ParseUser.getQuery());
                return query;
            }
        });

        // Available quests for a Good hero
        adapterAvailableGood = new QuestAdapter(this, new ParseQueryAdapter.QueryFactory<ParseObject>(){
            @Override
            public ParseQuery create() {
                ParseQuery query = new ParseQuery(QuestApp.QUEST_DATABASE);
                query.whereEqualTo(QuestApp.COMPLETED_KEY, false);
                query.whereEqualTo(QuestApp.ALIGNMENT_KEY, 0);
                query.whereDoesNotMatchKeyInQuery(QuestApp.ACCEPTEDBY_KEY, QuestApp.OBJECTID_KEY, ParseUser.getQuery());
                return query;
            }
        });

        // Available quests for an Evil hero
        adapterAvailableEvil = new QuestAdapter(this, new ParseQueryAdapter.QueryFactory<ParseObject>(){
            @Override
            public ParseQuery create() {
                ParseQuery query = new ParseQuery(QuestApp.QUEST_DATABASE);
                query.whereEqualTo(QuestApp.COMPLETED_KEY, false);
                query.whereEqualTo(QuestApp.ALIGNMENT_KEY, 2);
                query.whereDoesNotMatchKeyInQuery(QuestApp.ACCEPTEDBY_KEY, QuestApp.OBJECTID_KEY, ParseUser.getQuery());
                return query;
            }
        });

        // Quests accepted by user
        adapterAccepted = new QuestAdapter(this, new ParseQueryAdapter.QueryFactory<ParseObject>(){
            @Override
            public ParseQuery create() {
                ParseQuery query = new ParseQuery(QuestApp.QUEST_DATABASE);
                query.whereEqualTo(QuestApp.COMPLETED_KEY, false);
                query.whereEqualTo(QuestApp.ACCEPTEDBY_KEY, user);
                return query;
            }
        });

        // Quests completed by user
        adapterCompleted = new QuestAdapter(this, new ParseQueryAdapter.QueryFactory<ParseObject>(){
            @Override
            public ParseQuery create() {
                ParseQuery query = new ParseQuery(QuestApp.QUEST_DATABASE);
                query.whereEqualTo(QuestApp.COMPLETED_KEY, true);
                query.whereEqualTo(QuestApp.ACCEPTEDBY_KEY, user);
                return query;
            }
        });
    }


    /** Update the list of visible quests based on user alignment
     * and the desired quest status. **/
    private void updateListVisibility() {
        switch (currentFilter) {
            case 0: // Available quests...
                if (user.getInt(QuestApp.ALIGNMENT_KEY) == 0) {         //... for good heroes
                    setListAdapter(adapterAvailableGood);
                } else if (user.getInt(QuestApp.ALIGNMENT_KEY) == 1) {  //... for neutral heroes
                    setListAdapter(adapterAvailableNeutral);
                } else if (user.getInt(QuestApp.ALIGNMENT_KEY) == 2){   //... for evil heroes
                    setListAdapter(adapterAvailableEvil);
                }
                break;
            case 1: // Accepted quests
                setListAdapter(adapterAccepted);
                break;
            case 2: // Completed quests
                setListAdapter(adapterCompleted);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        if (pos != currentFilter) {
            currentFilter = pos;
            updateListVisibility();
        }
    }

    @Override
    /* On nothing, Do nothing. */
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
