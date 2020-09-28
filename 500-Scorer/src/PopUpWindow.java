import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * Pop Up Window acknowledging Winner of 500 game.
 */
public class PopUpWindow extends GUI{

    public PopUpWindow(Stage stage, boolean win, String team, int numberTeams, int[] teamScores) {

        //GridPane outcome = gridSetup();
        GridPane options = gridSetup();
        BorderPane border = new BorderPane();
        Label winner;

        if (win) {
            winner = new Label("Congratulations " + team + ", you won by reaching 500!");
        } else {
            winner = new Label("Congratulations " + team + ", you won by default!");
        }

        winner.setTextFill(Color.WHITE);
        winner.setFont(new Font(16));

        Button rematch = new Button("Rematch?");
        Button changeTeams = new Button("Change Teams");
        Button close = new Button("Exit");

        //options.add(outcome, 1, 0);
        options.add(rematch,0,1);
        options.add(changeTeams, 1, 1);
        options.add(close, 2, 1);

        options.setAlignment(Pos.CENTER);
        winner.setAlignment(Pos.CENTER);
        winner.setMinSize(400, 100);
        options.setMinSize(200, 70);
        border.setCenter(winner);
        border.setBottom(options);

        borderBackground("src/felt3.jpeg", border, 600, 200);
        stage.setScene(new Scene(border));

        // Starting rematch
        rematch.setOnAction(ActionEvent -> {

            for (int i = 0; i < numberTeams; i++) {
                teamScores[i] = 0;
            }
            scoreDisplay(stage);
        });

        // Changing teams
        changeTeams.setOnAction(ActionEvent -> {
            for (int i = 0; i < numberTeams; i++) {
                teamScores[i] = 0;
            }
            setTeams(stage, 0);
        });

        // Closing gui
        close.setOnAction(ActionEvent -> {
            Platform.exit();
        });
    }
}
