package com.example.myriadquest.myriadquest;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class QuestDetailActivity extends ActionBarActivity {

    QuestData quest;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_detail);

        TextView questNameView = (TextView) findViewById(R.id.name);
        TextView questGiverView = (TextView) findViewById(R.id.giverName);
        TextView questDescriptionView = (TextView) findViewById(R.id.description);
        TextView questAlignmentView = (TextView) findViewById(R.id.alignmentValue);

        Intent intent = getIntent();
        quest = intent.getParcelableExtra(QuestData.QUEST);

        questNameView.setText(quest.getName());
        questGiverView.setText(quest.getQuestGiver() + " @ " + quest.getQuestGiverLocationString());
        questDescriptionView.setText(quest.getDescription());
        questAlignmentView.setText(quest.getAlignment());

        initializeMap();

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

    @Override
    protected void onResume() {
        super.onResume();
        initializeMap();
    }

    private void initializeMap() {
        if (map == null){
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            if (map != null) {
                putQuestMarker(quest.getQuestLocationCoords());
                putQuestMarker(quest.getQuestGiverLocationCoords());
            }
        }
    }

    /** Add a marker to the map at a specific location. */
    private void putQuestMarker(double[] coordinates) {
        LatLng questLL = new LatLng(coordinates[0], coordinates[1]);
        MarkerOptions questMarker = new MarkerOptions().position(questLL);
        map.addMarker(questMarker);
    }
}
