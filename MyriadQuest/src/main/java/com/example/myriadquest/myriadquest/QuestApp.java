package com.example.myriadquest.myriadquest;


import android.app.Application;

import com.parse.Parse;

public class QuestApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the Parse
        Parse.initialize(this, "6HnBaZ2nGmvu55xwXaH9zU7t9pVgZyRJa9GOc1Te", "VqwDbwKCBeMgjZY7G3JLU7wDWcb7WZIPMvehyyBi");
    }
}
