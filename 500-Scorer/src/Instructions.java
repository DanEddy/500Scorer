import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.*;

/**
 * Instructions of 500 card game
 */
public class Instructions extends GUI {
    public Instructions(Stage stage) {

        BorderPane borderPane = new BorderPane();

        // Buttons for returning from instructions page
        BorderPane border = new BorderPane();
        GridPane gridPane = new GridPane();
        VBox vBox;
        Button back = new Button("Return");
        Button startGame = new Button("Start Game");

        gridPane.add(startGame, 0, 0);
        gridPane.add(back, 1, 0);
        gridPane.setAlignment(Pos.CENTER);

        ScrollPane textBox = new ScrollPane();

        try {
            textBox.setContent(new Text(fileReader("src/instructions.txt")));
        } catch (IOException ignore) {
        }

        border.setCenter(textBox);
        border.setBottom(gridPane);
        border.setMaxSize(800, 400);
        stage.setScene(new Scene(border));

        border.setPadding(new Insets( 10, 10, 10, 10));
        borderBackground("src/felt3.jpg", border, border.getWidth(), border.getHeight());

        back.setOnAction(ActionEvent -> start(stage));
        startGame.setOnAction(ActionEvent -> setTeams(stage, 0));
    }

    private String fileReader(String fileName) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
