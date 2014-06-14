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

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity {

    public static final String
            SAVE_LOGIN_KEY = "com.example.myriadquest.SAVE_LOGIN",
            USERNAME_KEY = "com.example.myriadquest.USERNAME";

    private SharedPreferences savedSettings;
    private SharedPreferences.Editor savedSettingsEditor;

    private EditText userText, passwordText;
    private CheckBox saveLoginBox;
    private boolean isLoginSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);

        savedSettings = getSharedPreferences("loginSettings", MODE_PRIVATE);
        savedSettingsEditor = savedSettings.edit();

        // Display saved username, if any has been saved.
        saveLoginBox = (CheckBox) findViewById(R.id.saveLoginCheckBox);
        isLoginSaved = savedSettings.getBoolean(SAVE_LOGIN_KEY, false);
        saveLoginBox.setChecked(isLoginSaved);
        if (isLoginSaved){
            String savedUsername = savedSettings.getString(USERNAME_KEY, "");
            if (savedUsername.equals("")){
                userText.setText("No Saved Username");
            } else {
                userText.setText(savedUsername);
            }
        }

        // Initialize the Parse
        Parse.initialize(this, "6HnBaZ2nGmvu55xwXaH9zU7t9pVgZyRJa9GOc1Te", "VqwDbwKCBeMgjZY7G3JLU7wDWcb7WZIPMvehyyBi");
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
        String enteredUser = userText.getText().toString();
        String enteredPassword = passwordText.getText().toString();

        if (enteredUser.equals("")) {
            userText.setError("Required");
        } else if (enteredPassword.equals("")) {
            passwordText.setError("Required");
        } else { // The login fields are not empty, attempt logging in.

            ParseUser.logInInBackground(enteredUser, enteredPassword, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (parseUser != null && e == null) { // Login succeeded
                        finishLogin(parseUser);
                    } else if (parseUser == null) { // Login failed because something invalid...
                        userText.setError("Invalid username or password");
                        passwordText.setError("Invalid username or password");
                    }
                }
            });

        }
    }

    /** Finish the final steps of logging in:
     *   1. Remembering the Username if desired
     *   2. Starting the QuestListActivity
     */
    private void finishLogin(ParseUser user) {
        if (user != null) {
            isLoginSaved = saveLoginBox.isChecked();
            savedSettingsEditor.putBoolean(SAVE_LOGIN_KEY, isLoginSaved);

            if (isLoginSaved) {
                savedSettingsEditor.putString(USERNAME_KEY, user.getUsername());
            }
            else { // else clear saved username.
                savedSettingsEditor.remove(USERNAME_KEY);
            }
            savedSettingsEditor.commit();

            // Login process complete, start quest list
            startActivity(new Intent(this, QuestListActivity.class));
        }
    }

    /** Take the user to the sign up screen */
    public void beginRegistration(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);

        // Send through any entered Username to the registration, so it does not need to be retyped
        intent.putExtra(USERNAME_KEY, userText.getText().toString());

        startActivity(intent);
    }
}
