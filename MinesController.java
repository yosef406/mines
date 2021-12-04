package mines;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MinesController implements Initializable, EventHandler<MouseEvent> {

    public TextField heightField;
    public TextField widthField;
    public TextField minesField;

    private static Mines minesGame;
    private static boolean gameOn = true;

    private static int height = 10, width = 10, numMines = 10;
    // the reset button function 
    public void resetButton() {
        try {
            //get's numbers from textfield
            height = Integer.parseInt(heightField.getText());
            width = Integer.parseInt(widthField.getText());
            numMines = Integer.parseInt(minesField.getText());

            //to make sure there aren't too many mines
            if (numMines < (height * width) / 3) {
                gameOn = true;
                minesGame = new Mines(height, width, numMines);
                MinesFX.setingRoot();//reset the scene from the minesfx
            } else {
                displayPrompt("Too many mines, \nMines = width * height / 4", "toxic.png", "over kill");
            }
        } catch (NumberFormatException e) {
            displayPrompt("Wrong input!\nYou need to put in Numbers", "toxic.png", "wrong input");
        }
        

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        minesGame = new Mines(height, width, numMines);
    }
    //build the buttons grid
    public GridPane gameGrid() {
        GridPane aGridPane = new GridPane();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Button b = new Butten(minesGame.get(i, j), i, j);// set all the buttons
                b.setOnMouseClicked(this);// set for action
                b.setPrefSize(50, 50);
                b.setAlignment(Pos.CENTER);
                if (minesGame.get(i, j) == "X") {// set an image of a mine
                     b.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bomb.png"))));
                    b.setText("");// remove the text from the image
                } else if (minesGame.get(i, j) == "F") {// set an image of a flag
                 b.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("flag.png"))));
                    b.setText("");// remove the text from the image
                } else if (minesGame.get(i, j) == ".") {
                     b.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("dot.png"))));
                    b.setText("");// remove the text from the image
                } else
                    b.setFont(new Font("Arial", 17));// setting a font size will afect the image display
                aGridPane.add(b, j, i);// add the button to the grid
            }
        }

        return aGridPane;
    }

    @Override
    public void handle(MouseEvent event) {
        Butten b = (Butten) event.getSource();
        if (event.getButton() == MouseButton.PRIMARY) {
            // if the left mouse button was used
            if (!minesGame.open(b.getx(), b.gety()) && gameOn) {
                // the function open returns false only trying to open a mine
                gameOn = false;
                minesGame.setShowAll(true);
                displayPrompt("Sorry you lose!", "explosion.png", "lose");// display lose
                // message
            } else if (minesGame.isDone() && gameOn) {
                // if all spots that are not a mine have been opened
                gameOn = false;
                minesGame.setShowAll(true);// show the board
                displayPrompt("Congrats you just won", "quality.png", "won");// display
                // wining message
            }
        } else if (event.getButton() == MouseButton.SECONDARY) {
            // if the left mouse button was pressed
            minesGame.toggleFlag(b.getx(), b.gety());// put a flage in the spot
        }
        MinesFX.setingRoot();// iniate scene with changes

    }

    private void displayPrompt(String message, String imageURL, String title) {
        Image image = new Image(getClass().getResourceAsStream(imageURL));// set the appropriate image

        Stage windowPrompt = new Stage();// new window to display
        Label promptMessage = new Label(message);// display message
        promptMessage.setTextAlignment(TextAlignment.CENTER);

        Button promptButton = new Button("Exit");
        promptButton.setOnAction(e -> windowPrompt.close());// this button only closes the window

        VBox promptBox = new VBox(20);
        promptBox.getChildren().addAll(new ImageView(image), promptMessage, promptButton);
        promptBox.setPadding(new Insets(20, 100, 20, 100));
        promptBox.setAlignment(Pos.CENTER);

        windowPrompt.initModality(Modality.APPLICATION_MODAL);// blocs interaction with the board window
        windowPrompt.setTitle(title);
        windowPrompt.setScene(new Scene(promptBox));
        windowPrompt.showAndWait();
    }

}