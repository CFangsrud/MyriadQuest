package com.example.myriadquest.myriadquest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class QuestDataArrayAdapter extends ArrayAdapter<QuestData>{
    private final Context context;
    private final QuestData[] questList;

    public QuestDataArrayAdapter(Context context, QuestData[] questList){
        super(context, R.layout.quest_list_row, questList);
        this.context = context;
        this.questList = questList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        QuestData quest = questList[position];

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View questView = inflater.inflate(R.layout.quest_list_row, parent, false);

        TextView questName = (TextView) questView.findViewById(R.id.questName);
        TextView questGiver = (TextView) questView.findViewById(R.id.questGiver);
        TextView questAlignment = (TextView) questView.findViewById(R.id.questAlignment);

        questName.setText(quest.getName());
        questGiver.setText(quest.getQuestGiver());
        questAlignment.setText(quest.getAlignment());

        return questView;
    }
}
