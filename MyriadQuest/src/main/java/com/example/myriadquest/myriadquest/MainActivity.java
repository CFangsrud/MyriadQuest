package com.example.myriadquest.myriadquest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        String enteredUser = userText.getText().toString();
        if (enteredUser.equals("")){
            userText.setError("Required");
        }

        EditText passwordText = (EditText) findViewById(R.id.password);
        String enteredPassword = passwordText.getText().toString();
        if (enteredPassword.equals("")){
            passwordText.setError("Required");
        }

        if(enteredUser.equals(validUser) && enteredPassword.equals(validUserPassword)){
            //login
        } else {
            userText.setError("Invalid username or password");
            passwordText.setError("Invalid username or password");
        }
    }
}
