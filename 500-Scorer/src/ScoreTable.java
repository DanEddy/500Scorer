/**
 * Java Class used to construct and store the 500 Score Table
 */
public class ScoreTable {

    // Member Variable used to store the Scoring table
    private int[][] scoreTable = new int[5][5];

    // Member Variable defining lowest element
    private int min = 40;

    private String[] suits = new String[] {"Spades", "Clubs", "Diamonds", "Hearts", "No Trump"};
    private String[] tricks = new String[] {"Slam", "Misere", "Open Misere", "Blind Misere"};
    private int[] trickScore = new int[] {250, 250, 500, 1000};

    /**
     * Constructor of score table array.
     */
    public ScoreTable() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                scoreTable[i][j] = (i + 1) * (min + (j * 20));
            }
        }
    }

    /**
     * Returns the score won/lost of the round.
     *
     * @param suit the suit chose during bidding
     * @param trick the number of tricks won during bidding
     * @param outcome true if player won, false otherwise
     * @return points won/lost
     */
    public int getValue(Object suit, Object trick, Object outcome) {

        if (trick == null && outcome == null) {

            // Do something
        }

        int suitIndex = arraySearch(suit, suits);
        int trickIndex = arraySearch(trick, tricks);
        int score = 0;
        int result = 1;

        // Determining if points need to be deducted
        if (!outcome.equals("Win")) {
            result = -1;
        }

        // Determining if bid was numerical, or specialty.
        if (trickIndex == -1) {
            trickIndex = (int) trick - 6;

            // Determining numerical score
            score =  result * scoreTable[suitIndex][trickIndex];

        } else {

            // Determining specialty trick score
            score = result * trickScore[trickIndex];
        }

        return score;
    }

    /**
     * Helper method for searching a small array for a given object.
     *
     * @param value object to be searched for
     * @param array array to be searched
     * @return index of value in array
     */
    private int arraySearch(Object value, String[] array) {

        for (int i = 0; i < array.length; i++) {
            if (value.equals(array[i])) {

                return i;
            }
        }

        return -1;
    }
}
