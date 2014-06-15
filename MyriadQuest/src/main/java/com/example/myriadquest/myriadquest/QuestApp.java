package com.example.myriadquest.myriadquest;


import android.app.Application;

import com.parse.Parse;

public class QuestApp extends Application {

    // Names of Parse columns, used in many activities
    public static final String
            USERNAME_KEY = "username",
            NAME_KEY = "name",
            LOCATION_KEY = "location",
            ALIGNMENT_KEY = "alignment",

            QUEST_DATABASE = "Quests",
            QUESTGIVER_KEY = "questGiver",
            ACCEPTEDBY_KEY = "acceptedBy",
            COMPLETED_KEY = "completed";

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the Parse
        Parse.initialize(this, "6HnBaZ2nGmvu55xwXaH9zU7t9pVgZyRJa9GOc1Te", "VqwDbwKCBeMgjZY7G3JLU7wDWcb7WZIPMvehyyBi");
    }
}
