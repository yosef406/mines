package mines;

import javafx.application.Application;
import javafx.event.EventHandler;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MinesFX extends Application implements EventHandler<MouseEvent> {
    private Stage stage;

    private boolean play = true;// used so that the game does not continue if the player loses

    private Mines minesGame;
    private GridPane aGridPane;

    private TextField widthTextField = new TextField("10");
    private TextField heightTextField = new TextField("10");
    private TextField minesTextField = new TextField("10");

    private Button resetButton = new Button("Reset");

    private Image mineImage = new Image(getClass().getResourceAsStream("Mine-24x24x32.png"));
    private Image flagImage = new Image(getClass().getResourceAsStream("Flag-24x24x32.png"));
    private Image dotImage = new Image(getClass().getResourceAsStream("dot.png"));

    private VBox vBox = new VBox(10);
    private HBox root = new HBox(10);

    private int height = 10, width = 10, minesNum = 10;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        // creating label and text field for width
        Label widthLabel = new Label("width ");
        widthLabel.setMaxWidth(40);
        widthLabel.setAlignment(Pos.CENTER_LEFT);
        widthTextField.setMaxWidth(60);
        widthTextField.setAlignment(Pos.CENTER);

        // creating label and text field for height
        Label heightLabel = new Label("height ");
        heightLabel.setMaxWidth(40);
        heightLabel.setAlignment(Pos.CENTER_LEFT);
        heightTextField.setMaxWidth(60);
        heightTextField.setAlignment(Pos.CENTER);

        // creating label and text field for mines
        Label minesLabel = new Label("mines ");
        minesLabel.setMaxWidth(40);
        minesLabel.setAlignment(Pos.CENTER_LEFT);
        minesTextField.setMaxWidth(60);
        minesTextField.setAlignment(Pos.CENTER);

        // set labels and text fields
        HBox labelAndText1 = new HBox();
        labelAndText1.getChildren().addAll(widthLabel, widthTextField);
        labelAndText1.setAlignment(Pos.CENTER);
        HBox labelAndText2 = new HBox();
        labelAndText2.getChildren().addAll(heightLabel, heightTextField);
        labelAndText2.setAlignment(Pos.CENTER);
        HBox labelAndText3 = new HBox();
        labelAndText3.getChildren().addAll(minesLabel, minesTextField);
        labelAndText3.setAlignment(Pos.CENTER);

        // setting the reset button
        resetButton.setOnMouseClicked(this);
        resetButton.setAlignment(Pos.CENTER);
        resetButton.setMaxWidth(100);

        // creating game controller
        minesGame = new Mines(height, width, minesNum);

        vBox.getChildren().addAll(resetButton, labelAndText1, labelAndText2, labelAndText3);
        vBox.setAlignment(Pos.CENTER);
        /////////////////////////////////////////////////////////////////////////////////

        this.stage.setTitle("The Amazing Mines Sweeper");
        setingRoot();
        this.stage.show();
    }

    private GridPane initiateGrid(int width, int height) {
        GridPane aGridPane = new GridPane();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Button b = new MyButton(minesGame.get(i, j), i, j);// set all the buttons
                b.setOnMouseClicked(this);// set for action
                b.setPrefSize(40, 40);
                b.setAlignment(Pos.CENTER);
                if (minesGame.get(i, j) == "X") {// set an image of a mine
                    b.setGraphic(new ImageView(mineImage));
                    b.setText("");// remove the text from the image
                } else if (minesGame.get(i, j) == "F") {// set an image of a flag
                    b.setGraphic(new ImageView(flagImage));
                    b.setText("");// remove the text from the image
                } else if (minesGame.get(i, j) == ".") {
                    b.setGraphic(new ImageView(dotImage));
                    b.setText("");// remove the text from the image
                } else
                    b.setFont(new Font("Arial", 17));// setting a font size will afect the image display
                aGridPane.add(b, i, j);// add the button to the grid
            }
        }
        return aGridPane;
    }

    private void setingRoot() {
        // this function is made because this code is used too many times
        aGridPane = initiateGrid(width, height);// initiate a new grid
        aGridPane.setAlignment(Pos.CENTER);
        // make a new scene
        root = new HBox(10);
        root.getChildren().addAll(vBox, aGridPane);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        this.stage.setScene(new Scene(root));
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getSource() instanceof MyButton) {
            // only the reset button is not instance of MyButton
            MyButton b = (MyButton) event.getSource();
            if (event.getButton() == MouseButton.PRIMARY) {
                // if the left mouse button was used
                if (!minesGame.open(b.getX(), b.getY()) && play) {
                    // the function open returns false only trying to open a mine
                    play = false;
                    minesGame.setShowAll(true);
                    displayWinOrLose("Sorry you play!", "lose128.png", "lose");// display lose missage
                } else if (minesGame.isDone() && play) {
                    // if all spots that are not a mine have been opened
                    play = false;
                    minesGame.setShowAll(true);// show the board
                    displayWinOrLose("Congrats you just won", "award128.png", "won");// display wining message
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                // if the left mouse button was pressed
                minesGame.toggleFlag(b.getX(), b.getY());// put a flage in the spot
            }
            setingRoot();// iniate scene with changes
        } else {// only the reset button will arive here
            play = true;
            resetGame();// reset board
        }
    }

    private void displayWinOrLose(String message, String imageLocation, String title) {
        Image image = new Image(getClass().getResourceAsStream(imageLocation));// set the appropriate image

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

    private void resetGame() {
        try {// try to get a number from the text fields
            width = Integer.parseInt(widthTextField.getText());
            height = Integer.parseInt(heightTextField.getText());
            minesNum = Integer.parseInt(minesTextField.getText());

            if (minesNum < (width * height) / 4) {// to make sure the user dose not over doeit
                // with the number of mines
                minesGame = new Mines(width, height, minesNum);// iniate a new game
                setingRoot();
            } else {// if the number of mines is not good display message
                displayWinOrLose("Too many mines, \nMines = width * height / 4", "too many mines.png", "over kill");
            }
        } catch (NumberFormatException e) {// if the textField has other than a number display prompt
            displayWinOrLose("Wrong input!\nYou need to put in Numbers", "wrong input.png", "wrong input");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
