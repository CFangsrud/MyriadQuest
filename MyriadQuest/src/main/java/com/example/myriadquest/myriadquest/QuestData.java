package com.example.myriadquest.myriadquest;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestData implements Parcelable {

    // Constants for various quest-specific functions which require a key.
    public static final String
            QUEST = "com.example.myriadquest.QUEST",
            QUEST_NAME = "com.example.myriadquest.NAME",
            QUEST_ALIGNMENT = "com.example.myriadquest.ALIGNMENT",
            QUEST_DESCRIPTION = "com.example.myriadquest.DESCRIPTION",
            QUEST_LOCATION = "com.example.myriadquest.LOCATION",
            QUEST_GIVER = "com.example.myriadquest.GIVER",
            QUEST_GIVER_LOCATION = "com.example.myriadquest.GIVERLOCATION";

    private String name, alignment, description, giver;

    // Store location coordinate data as actual numbers.
    private double[] questLoc, giverLoc;

    /** Constructor given 6 Strings.
     *
     * The Strings location and giverLocation are coordinates, and must be
     * able to be split by String.split("[(,\s)]").  For instance "(-45.52, 23.1)" is valid.
     */
    public QuestData(String name, String alignment, String description,
                     String location, String giver, String giverLocation){
        this.name = name;
        this.alignment = alignment;
        this.description = description;
        this.questLoc = makeCoords(location);
        this.giver = giver;
        this.giverLoc = makeCoords(giverLocation);
    }

    /** Constructor given an array of Strings. Exactly 6 are used. */
    public QuestData(String[] s) {
        this(s[0], s[1], s[2], s[3], s[4], s[5]);
    }

    /** Helper method to split coordinate Strings into a pair of doubles.
     *
     * The String source is assumed to be formatted like "(-45.52, 23.1)"
     * which results in the double array {-45.52, 23.1}.
     */
    private double[] makeCoords(String source){
        double[] coords = new double[2];
        String[] splitCoords = source.split("[(,\\s)]");

        int i = 0;
        for(String s : splitCoords)
        {
            try {
                coords[i] = Double.parseDouble(s);
                i++;                            // 'i' should only increment after a successful parse
            }
            catch (NumberFormatException e){}   // For possible empty Strings and other non-doubles

            if (i == coords.length) break;      // Prevent ArrayIndexOutOfBoundsException

        }

        return coords;
    }

    public String getName(){
        return name;
    }

    public String getAlignment(){
        return alignment;
    }

    public String getDescription(){
        return description;
    }

    public String getQuestGiver(){
        return giver;
    }

    // Return the quest's location coordinate String formatted "(x, y)".
    public String getQuestLocationString(){
        return "(" + questLoc[0] + ", " + questLoc[1] + ")";
    }

    /** Return the quest location coordinates in an array of size 2.
     * Use index [0] for the x-coordinate, [1] for the y-coordinate.
     */
    public double[] getQuestLocationCoords(){
        return questLoc;
    }

    // Return the quest giver's location coordinate String formatted "(x, y)".
    public String getQuestGiverLocationString(){
        return "(" + giverLoc[0] + ", " + giverLoc[1] + ")";
    }

    /** Return the quest giver's location coordinates in an array of size 2.
     * Use index [0] for the x-coordinate, [1] for the y-coordinate.
     */
    public double[] getQuestGiverLocationCoords(){
        return giverLoc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    /** Fill Parcel p with the contents of this QuestData object. **/
    public void writeToParcel(Parcel p, int i) {
        p.writeString(name);
        p.writeString(alignment);
        p.writeString(description);
        p.writeDoubleArray(questLoc);
        p.writeString(giver);
        p.writeDoubleArray(giverLoc);
    }


    public static final Parcelable.Creator<QuestData> CREATOR = new Parcelable.Creator<QuestData>() {
        public QuestData createFromParcel(Parcel p) {
            return new QuestData(p);
        }
        public QuestData[] newArray(int size) {
            return new QuestData[size];
        }
    };


    /** Create a new QuestData copying the contents of Parcel p. **/
    private QuestData(Parcel p) {
        this.name = p.readString();
        this.alignment = p.readString();
        this.description = p.readString();
        this.questLoc = p.createDoubleArray();
        this.giver = p.readString();
        this.giverLoc = p.createDoubleArray();
    }
}
