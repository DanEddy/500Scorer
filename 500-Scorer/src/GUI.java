import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.geometry.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static java.lang.Math.ceil;

/**
 * Gui Class for the 500 scorer
 */
public class GUI extends Application {

    // Team One/ Two Name and Label
    private static String[] teamNames = new String[2];
    private static Label[] teamLabels = new Label[2];

    private int[] teamScores = new int[] {0, 0};
    private ScoreTable scoreTable = new ScoreTable();

    private int numberTeams = 2;

    private int dan = 3;

    private static Stage stage;

    /**
     * Initial scene of stage. Used to Input the first team name
     * @param stage Opening Window
     */
    public void start(Stage stage) {

        GUI.stage = stage;

        // Setting Title of Stage
        stage.setTitle("500 Scorer");

        BorderPane border = new BorderPane();
        GridPane gridPane = gridSetup();

        Button playButton = new Button("Start Game");
        Button instructions = new Button("How to Play?)");

        gridPane.add(playButton, 1, 1);
        gridPane.add(instructions, 1, 2);

        // Background image
        borderBackground("src/background.jpg", border, gridPane.getWidth(), gridPane.getHeight());

        border.setCenter(gridPane);
        Scene scene = new Scene(border);
        stage.setScene(scene);
        //stage.setResizable(false);
        stage.show();

        // Defining actions to buttons
        playButton.setOnAction(ActionEvent -> setTeams(stage, 0));

        instructions.setOnAction(ActionEvent -> {
            new Instructions(stage);
        });
    }

    /**
     * GUI implemented when obtaining the name of each team.
     *
     * @param stage Current Stage
     * @param teamNumber the number of the team to be input
     */
    protected void setTeams(Stage stage, int teamNumber) {

        // Used to set the stage when change teams is called after match is won
//        if (this.stage == null) {
//            this.stage = stage;
//        }

        BorderPane border = new BorderPane();
        GridPane gridPane = gridSetup();

        Label teamName = new Label("Enter team " + (teamNumber + 1) +  " name");
        TextField teamNameInput = new TextField();

        // Submit Button
        Button button = new Button("Submit");

        // method to mount teamName, teamNameInput, and button to the grid pane
        teamInformation(gridPane, teamName, teamNameInput, button);

        // Inputting and mounting the background image to the gridpane
        borderBackground("src/background 2.jpg", border, gridPane.getWidth(), gridPane.getHeight());

        border.setCenter(gridPane);
        Scene scene = new Scene(border);
        stage.setScene(scene);
        stage.show();

        // Mapping Enter to button and Assigning button action
        button.setDefaultButton(true);
        button.setOnAction(ActionEvent -> {

            // Assign team name to member Variable
            teamNames[teamNumber] = teamNameInput.getText();

            // Assigning team one name to the team one label
            teamLabels[teamNumber] = new Label(teamNames[teamNumber]);
            teamLabels[teamNumber].setTextFill(Color.WHITE);

            if (teamNumber + 1 < numberTeams) {
                setTeams(stage, teamNumber + 1);
            } else {
                scoreDisplay(stage);
            }
        });
    }

    /**
     * The third stage. Contains the current score for each team, and the method to update the score.
     * @param stage Open Window
     */
    protected void scoreDisplay(Stage stage) {

        // Initialising grid components
        // Creating Bordered Pane to separate the top and bottom areas
        BorderPane border = new BorderPane();
        GridPane upperGridPane = gridSetup();
        GridPane lowerGridPane = gridSetup();

        // Combo Box array and update button
        ComboBox[] updateMenu = new ComboBox[4];
        Button button = new Button("Update");

        // Calling private help method to construct upper grid - containing team names and scores
        upperGridConstruct(upperGridPane);

        // Calling private helper method to construct lower grid - containing comboBox's to input round results
        lowerGridConstruct(lowerGridPane, updateMenu, button);

        Image image = null;
        try {
            image = new Image(new FileInputStream("src/back3.jpg"));
        } catch (FileNotFoundException ignored) {
        }

        BackgroundSize size = new BackgroundSize(border.getWidth(),border.getHeight(), false, false,
                true, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);

        Background background = new Background(backgroundImage);
        border.setBackground(background);

        // Setting the top and bottom GridPane's to the BorderPane
        border.setTop(upperGridPane);
        border.setBottom(lowerGridPane);

        Scene sceneThree = new Scene(border);
        stage.setScene(sceneThree);
        stage.show();

        // Mapping Enter to Button and Assigning Button action
        button.setDefaultButton(true);
        button.setOnAction(ActionEvent -> {

            // Obtaining the score of the round
            int score = scoreTable.getValue(updateMenu[1].getValue(), updateMenu[2].getValue(),
                    updateMenu[3].getValue());

            // Updating the current score
            updateScore((String) updateMenu[0].getValue(), score, border);
        });
    }

    /**
     * Construct a grid with gap and padding.
     *
     * @return Constructed grid
     */
    protected GridPane gridSetup() {

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400, 200);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets( 10, 10, 10, 10));

        return gridPane;
    }

    /**
     * Method used to add a label, textfield and button to a GridPane.
     *
     * @param gridPane GridPane to which components are mounted
     * @param label label to be mounted
     * @param textfield textfield to be mounted
     * @param button button to be mounted
     */
    private void teamInformation(GridPane gridPane, Label label, TextField textfield, Button button) {

        // Adding name, text box and button to grid
        gridPane.add(label, 0, 0);
        gridPane.add(textfield, 1, 0);
        gridPane.add(button, 0, 1);
    }

    /**
     * Method for mounting an image to the background of a GridPane.
     *
     * @param fileName Name of image to be input
     * @param border border to mount image
     * @param width Width of GridPane
     * @param length Length of GridPane
     */
    protected void borderBackground(String fileName, BorderPane border, double width, double length) {

        Image image = null;
        try {
            image = new Image(new FileInputStream(fileName));
        } catch (FileNotFoundException ignore) {
        }
        BackgroundSize size = new BackgroundSize(width,length, false, false,
                true, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);

        Background background = new Background(backgroundImage);
        border.setBackground(background);
    }

    /**
     * Private helper method - Construct grid consisting of combo box's allowing round result input.
     *
     * @param gridPane GridPane to which components are mounted
     * @param comboBox Array of ComboBox's for round result input
     * @param button Button for input conformation
     */
    private void lowerGridConstruct(GridPane gridPane, ComboBox[] comboBox, Button button) {

        Object[] suits = new Object[] {"Spades", "Clubs", "Diamonds", "Hearts", "No Trump"};
        Object[] tricks = new Object[] {6, 7, 8, 9, 10, "Slam", "Misere", "Open Misere", "Blind Misere"};
        String[] titles = new String[] {"Select Team", "Select Suit", "Select Trick Number", "Select Outcome"};
        Object[][] content = new Object[][] {teamNames, suits, tricks, new Object[]{"Win", "Loss"}};

        for (int i = 0; i < 4; i++) {

            // Creating Combo Box
            comboBox[i] = new ComboBox();

            // Updating title of Combo Box
            comboBox[i].setPromptText(titles[i]);

            // Updating Combo Box drop down options
            comboBox[i].getItems().addAll(content[i]);

            // Adding Combo Box to the gridPane
            gridPane.add(comboBox[i], i, 0);
        }

        // Mounting Button
        gridPane.add(button, 4, 0);
    }

    /**
     * Private helper method - Construct grid consisting of team names and current scores.
     *
     * @param gridPane GridPane to be constructed
     */
    private void upperGridConstruct(GridPane gridPane) {

        // Inputting team one and two scores into the gridPane
        for (int i = 0; i < numberTeams; i++) {
            teamLabels[i].setFont(new Font(16));
            gridPane.add(teamLabels[i], 1, i + 1);
            Label teamScore = new Label(Integer.toString(teamScores[i]));
            teamScore.setTextFill(Color.WHITE);
            teamScore.setFont(new Font(16));
            gridPane.add(teamScore, 2, i + 1);
        }
    }

    /**
     * Updating the score of the winning team, and displaying updated results.
     *
     * @param teamName Winning Team
     * @param score Score of round win
     * @param border BorderPane of score screen
     */
    private void updateScore(String teamName, int score, BorderPane border) {

        if (teamName == null) {

        }

//        if (teamName.equals("daniel") || teamName.equals("Daniel")) {
//            score = (int) ceil(1.5 * score);
//        }
        // Updating the winning teams score
        for (int i = 0; i < numberTeams; i++) {
            if (teamNames[i].equals(teamName)) {
                teamScores[i] += score;

                if (teamScores[i] >= 500) {
                    new PopUpWindow(stage, true, teamNames[i], numberTeams, teamScores);
                } else if (teamScores[i] <= -500) {

                    if (i == 0) {
                        new PopUpWindow(stage, false, teamNames[1], numberTeams, teamScores);
                    } else if (i == 1) {
                        new PopUpWindow(stage, false, teamNames[0], numberTeams, teamScores);
                    }
                }
            }
        }

        // Creating a new upperGrid and mounting to the top of the border
        GridPane gridPane = gridSetup();
        upperGridConstruct(gridPane);
        border.setTop(gridPane);
    }

    public static void main(String[] args)  {
        launch(args);
    }
}
