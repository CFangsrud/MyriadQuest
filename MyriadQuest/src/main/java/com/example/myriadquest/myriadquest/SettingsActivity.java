package com.example.myriadquest.myriadquest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseGeoPoint;
import com.parse.ParseUser;


public class SettingsActivity extends ActionBarActivity {

    private Spinner alignmentSpinner;
    private int alignment;
    private TextView editName, editLatitude, editLongitude;

    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        user = ParseUser.getCurrentUser();

        editName = (TextView) findViewById(R.id.editNameField);
        editLatitude = (TextView) findViewById(R.id.editLatitude);
        editLongitude = (TextView) findViewById(R.id.editLongitude);

        String name = user.getString(QuestApp.NAME_KEY);
        if (!name.equals("")){
            editName.setText(name);
        }
        ParseGeoPoint location = user.getParseGeoPoint(QuestApp.LOCATION_KEY);
        if (location != null) {
            editLatitude.setText(""+location.getLatitude());
            editLongitude.setText(""+location.getLongitude());
        }

        alignmentSpinner = (Spinner) findViewById(R.id.alignmentSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.alignmentArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alignmentSpinner.setAdapter(adapter);

        alignment = user.getInt(QuestApp.ALIGNMENT_KEY);
        alignmentSpinner.setSelection(alignment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
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


    public void updateSettings(View view) {
        String name = editName.getText().toString();
        String latitudeString = editLatitude.getText().toString();
        String longitudeString = editLongitude.getText().toString();
        alignment = alignmentSpinner.getSelectedItemPosition();

        user.put(QuestApp.NAME_KEY, name);
        ParseGeoPoint location = new ParseGeoPoint(Double.parseDouble(latitudeString), Double.parseDouble(longitudeString));
        user.put(QuestApp.LOCATION_KEY, location);
        user.put(QuestApp.ALIGNMENT_KEY, alignment);
        user.saveInBackground();

        setResult(RESULT_OK);
        finish();
    }
}
