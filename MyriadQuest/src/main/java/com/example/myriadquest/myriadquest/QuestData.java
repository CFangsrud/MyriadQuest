package com.example.myriadquest.myriadquest;

public class QuestData {

    // Constants for various quest-specific functions which require a key.
    public static final String
            QUEST_NAME = "com.example.myriadquest.NAME",
            QUEST_ALIGNMENT = "com.example.myriadquest.ALIGNMENT",
            QUEST_DESCRIPTION = "com.example.myriadquest.DESCRIPTION",
            QUEST_LOCATION = "com.example.myriadquest.LOCATION",
            QUEST_GIVER = "com.example.myriadquest.GIVER",
            QUEST_GIVER_LOCATION = "com.example.myriadquest.GIVERLOCATION";

    private String name, alignment, description, location, giver, giverLocation;

    // Store location coordinate data as actual numbers.
    private float[] questLoc, giverLoc;

    /** Constructor given 6 individual Strings.
     *
     * The Strings location and giverLocation are coordinates, and must be
     * able to be split by String.split("[(,\s)]").  For instance "(-45.52, 23.1)" is valid.
     */
    public QuestData(String name, String alignment, String description,
                     String location, String giver, String giverLocation){
        this.name = name;
        this.alignment = alignment;
        this.description = description;
        this.location = location;
        this.giver = giver;
        this.giverLocation = giverLocation;

        this.questLoc = makeFloatCoords(location);
        this.giverLoc = makeFloatCoords(giverLocation);
    }

    /** Helper method to split coordinate Strings into a pair of floats.
     *
     * The String source is assumed to be formatted like "(-45.52, 23.1)"
     * which results in the float array {-45.52, 23.1}.
     */
    private float[] makeFloatCoords(String source){
        String[] splitCoords = source.split("[(,\\s)]");
        return new float[]{
                Float.parseFloat(splitCoords[0]),
                Float.parseFloat(splitCoords[1])
        };
    }

    // Constructor given an array of Strings. Only the first 6 are used.
    public QuestData(String[] s) {
        this(s[0], s[1], s[2], s[3], s[4], s[5]);
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

    // Return the quest's location coordinate String.
    public String getQuestLocationString(){
        return location;
    }

    /** Return the quest location coordinates in an array of size 2.
     * Use index [0] for the x-coordinate, [1] for the y-coordinate.
     */
    public float[] getQuestLocationCoords(){
        return questLoc;
    }

    // Return the quest giver's location coordinate String.
    public String getQuestGiverLocationString(){
        return giverLocation;
    }

    /** Return the quest giver's location coordinates in an array of size 2.
     * Use index [0] for the x-coordinate, [1] for the y-coordinate.
     */
    public float[] getQuestGiverLocationCoords(){
        return giverLoc;
    }
}
