package com.example.myriadquest.myriadquest;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class SettingsActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    public static final String NAME_KEY = "com.example.myriadquest.NAME";
    public static final String LOCATION_KEY = "com.example.myriadquest.LOCATION";
    public static final String ALIGNMENT_KEY = "com.example.myriadquest.ALIGNMENT";

    private Spinner alignmentSpinner;
    private int alignment;
    private TextView editName, editLocation;
    private SharedPreferences savedSettings;
    private SharedPreferences.Editor savedSettingsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editName = (TextView) findViewById(R.id.editNameField);
        editLocation = (TextView) findViewById(R.id.editOriginLocation);

        savedSettings = getSharedPreferences("accountSettings", MODE_PRIVATE);
        savedSettingsEditor = savedSettings.edit();

        String name = savedSettings.getString(NAME_KEY, "");
        String location = savedSettings.getString(LOCATION_KEY, "");
        if (!name.equals("")){
            editName.setText(name);
        }
        if (!location.equals("")){
            editLocation.setText(location);
        }

        alignmentSpinner = (Spinner) findViewById(R.id.alignmentSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.alignmentArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alignmentSpinner.setAdapter(adapter);
        alignmentSpinner.setOnItemSelectedListener(this);

        alignment = savedSettings.getInt(ALIGNMENT_KEY, 1);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void updateSettings(View view) {
        String name = editName.getText().toString();
        String location = editLocation.getText().toString();
        alignment = alignmentSpinner.getSelectedItemPosition();

        savedSettingsEditor.putString(NAME_KEY, name);
        savedSettingsEditor.putString(LOCATION_KEY, location);
        savedSettingsEditor.putInt(ALIGNMENT_KEY, alignment);
        savedSettingsEditor.apply();
    }
}
