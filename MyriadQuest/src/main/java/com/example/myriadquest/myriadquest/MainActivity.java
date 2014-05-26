package com.example.myriadquest.myriadquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    public static final String SAVE_LOGIN_KEY = "com.example.myriadquest.SAVE_LOGIN";
    public static final String USERNAME_KEY = "com.example.myriadquest.USERNAME";

    private SharedPreferences savedSettings;
    private SharedPreferences.Editor savedSettingsEditor;

    private CheckBox saveLoginBox;
    private boolean isLoginSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        savedSettings = getSharedPreferences("loginSettings", MODE_PRIVATE);
        savedSettingsEditor = savedSettings.edit();

        saveLoginBox = (CheckBox) findViewById(R.id.saveLoginCheckBox);
        isLoginSaved = savedSettings.getBoolean(SAVE_LOGIN_KEY, false);

        // Display saved username, if any has been saved.
        saveLoginBox.setChecked(isLoginSaved);
        if (isLoginSaved){
            EditText usernameField = (EditText) findViewById(R.id.username);
            String savedUsername = savedSettings.getString(USERNAME_KEY, "");
            if (savedUsername.equals("")){
                usernameField.setText("No Saved Username");
            } else {
                usernameField.setText(savedUsername);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    /** Verify that the given login details are valid. **/
    public void verifyLogin(View view) {
        String validUser = getResources().getString(R.string.validUsername);
        String validUserPassword = getResources().getString(R.string.validPassword);

        EditText userText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);

        String enteredUser = userText.getText().toString();
        String enteredPassword = passwordText.getText().toString();

        if (enteredUser.equals("")) {
            userText.setError("Required");
        } else if (enteredPassword.equals("")) {
            passwordText.setError("Required");
        } else if (enteredUser.equals(validUser) && enteredPassword.equals(validUserPassword)) {
            // Only save username when the username is valid
            isLoginSaved = saveLoginBox.isChecked();
            savedSettingsEditor.putBoolean(SAVE_LOGIN_KEY, isLoginSaved);
            if (isLoginSaved){
                savedSettingsEditor.putString(USERNAME_KEY, enteredUser);
            }
            // else clear saved username.
            else {
                savedSettingsEditor.remove(USERNAME_KEY);
            }
            savedSettingsEditor.commit();

            Intent intent = new Intent(this, QuestListActivity.class);
            startActivity(intent);

        } else {
            userText.setError("Invalid username or password");
            passwordText.setError("Invalid username or password");
        }
    }
}
