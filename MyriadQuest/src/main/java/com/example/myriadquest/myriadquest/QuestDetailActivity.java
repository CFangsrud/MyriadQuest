package com.example.myriadquest.myriadquest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class QuestDetailActivity extends ActionBarActivity {

    private String[] alignment;

    private ParseObject quest;
    private ParseUser questGiver;

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_detail);

        alignment = getResources().getStringArray(R.array.alignmentArray);

        // Get the Quest data from Parse, given the objectId passed via the intent
        queryQuest(getIntent().getStringExtra(QuestApp.OBJECTID_KEY));
    }


    /* Load a particular quest whose objectId is questId, and the quest giver. */
    private void queryQuest(String questId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(QuestApp.QUEST_DATABASE);
        query.include("_User");
        query.getInBackground(questId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) { // Quest was found
                    // Set quest
                    quest = parseObject;

                    // Set questGiver
                    questGiver = quest.getParseUser(QuestApp.QUESTGIVER_KEY);
                    try {
                        questGiver.fetchIfNeeded();
                    } catch (ParseException pe) {
                        pe.printStackTrace();
                    }

                    showQuestDetails();  // Show details for this quest
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    /* Display the details of the quest. */
    private void showQuestDetails() {
        if (questGiver != null) {
            TextView questGiverView = (TextView) findViewById(R.id.giverName);

            questGiverView.setText(questGiver.getString(QuestApp.NAME_KEY));
        }

        if (quest != null) {
            TextView questNameView = (TextView) findViewById(R.id.name);
            TextView questDescriptionView = (TextView) findViewById(R.id.description);
            TextView questAlignmentView = (TextView) findViewById(R.id.alignmentValue);

            questNameView.setText(quest.getString(QuestApp.NAME_KEY));
            questDescriptionView.setText(quest.getString(QuestApp.DESCRIPTION_KEY));
            questAlignmentView.setText(alignment[quest.getInt(QuestApp.ALIGNMENT_KEY)]);

            initializeMap();
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

    @Override
    protected void onResume() {
        super.onResume();
        if (quest != null && questGiver != null) {
            initializeMap();
        }
    }

    private void initializeMap() {
        if (map == null){
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            if (map != null) {
                final ParseGeoPoint questGeoPoint = quest.getParseGeoPoint(QuestApp.LOCATION_KEY);
                final ParseGeoPoint giverGeoPoint = questGiver.getParseGeoPoint(QuestApp.LOCATION_KEY);
                putQuestMarker(questGeoPoint);
                putQuestMarker(giverGeoPoint);

                // Center the map on the quest markers and zoom.  Can be done before map is loaded.
                double centerLat = (questGeoPoint.getLatitude() + giverGeoPoint.getLatitude()) / 2.0;
                double centerLng = (questGeoPoint.getLongitude() + giverGeoPoint.getLongitude()) / 2.0;
                LatLng center = new LatLng(centerLat, centerLng);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 12));

                // Only once the map is loaded, fit the zoom to the map points more accurately.
                map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback(){
                    @Override
                    public void onMapLoaded() {
                        LatLng southwest = new LatLng(
                                Math.min(questGeoPoint.getLatitude(), giverGeoPoint.getLatitude()),
                                Math.min(questGeoPoint.getLongitude(), giverGeoPoint.getLongitude())
                        );
                        LatLng northeast = new LatLng(
                                Math.max(questGeoPoint.getLatitude(), giverGeoPoint.getLatitude()),
                                Math.max(questGeoPoint.getLongitude(), giverGeoPoint.getLongitude())
                        );

                        LatLngBounds zoomBounds = new LatLngBounds(southwest, northeast);
                        map.moveCamera(CameraUpdateFactory.newLatLngBounds(zoomBounds, 50));
                    }
                });
            }
        }
    }

    /** Add a marker to the map at a specific location. */
    private void putQuestMarker(ParseGeoPoint location) {
        LatLng questLL = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions questMarker = new MarkerOptions().position(questLL);
        map.addMarker(questMarker);
    }
}
