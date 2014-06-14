package com.example.myriadquest.myriadquest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class RegistrationActivity extends ActionBarActivity {

    private EditText userText, passwordText, nameText;
    private Spinner alignmentSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);
        nameText = (EditText) findViewById(R.id.signUpName);

        // Get the Username entered at login
        userText.setText(getIntent().getStringExtra(MainActivity.USERNAME_KEY));

        alignmentSpinner = (Spinner) findViewById(R.id.alignmentSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.alignmentArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alignmentSpinner.setAdapter(adapter);
        // Set default alignment to 1 (Neutral)
        alignmentSpinner.setSelection(1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registration, menu);
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

    /** Attempt to register a new user */
    public void beginRegistration(View view) {
        String desiredUsername = userText.getText().toString();
        String desiredPassword = passwordText.getText().toString();
        String desiredName = nameText.getText().toString();
        int alignment = alignmentSpinner.getSelectedItemPosition();

        if (desiredUsername.equals("")) {
            userText.setError("Required");
        } else if (desiredPassword.equals("")) {
            passwordText.setError("Required");
        } else if (desiredName.equals("")) {
            nameText.setError("Required");
        } else { // The registration fields are not empty, attempt creating new account

            ParseUser attemptTo = new ParseUser();
            attemptTo.setUsername(desiredUsername);
            attemptTo.setPassword(desiredPassword);
            attemptTo.put("name", desiredName);
            attemptTo.put("alignment", alignment);
            attemptTo.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) { // Registration was successful
                        setResult(RESULT_OK);
                        finish();
                    } else if (e.getCode() == ParseException.USERNAME_TAKEN) {
                        userText.setError("Username unavailable");
                    }
                }
            });

        }
    }
}
