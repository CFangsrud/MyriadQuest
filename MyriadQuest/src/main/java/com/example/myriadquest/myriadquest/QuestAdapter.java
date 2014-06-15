package com.example.myriadquest.myriadquest;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;


public class QuestAdapter extends ParseQueryAdapter<ParseObject> {

    private final String[] alignment;

    public QuestAdapter(Context context, QueryFactory<ParseObject> queryFactory) {
        super(context, queryFactory);
        alignment = context.getResources().getStringArray(R.array.alignmentArray);
    }

    @Override
    public View getItemView(ParseObject quest, View questView, ViewGroup parent) {
        if (questView == null) {
            questView = View.inflate(getContext(), R.layout.quest_list_row, null);
        }

        super.getItemView(quest, questView, parent);

        // Fetch the quest giver's data, so their name can be shown
        ParseUser questGiver = quest.getParseUser(QuestApp.QUESTGIVER_KEY);
        try {
            questGiver.fetchIfNeeded();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextView questNameText = (TextView) questView.findViewById(R.id.questName);
        TextView questGiverText = (TextView) questView.findViewById(R.id.questGiver);
        TextView questAlignmentText = (TextView) questView.findViewById(R.id.questAlignment);

        questNameText.setText(quest.getString(QuestApp.NAME_KEY));
        questGiverText.setText(questGiver.getString(QuestApp.NAME_KEY));
        questAlignmentText.setText(alignment[quest.getInt(QuestApp.ALIGNMENT_KEY)]);

        return questView;
    }
}
