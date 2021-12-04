package mines;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MinesFX extends Application  {
    //all opjects are static so that they can be used in setingRoot()
    private static Stage stage; 

    private static MinesController gameController = new MinesController();

    static VBox vbox;
    static HBox root;


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        vbox = FXMLLoader.load(getClass().getResource("layout.fxml"));//get the vbox that was made in scenebuilder

        stage.setTitle("The Amazing Mines Sweeper");
        setingRoot();//use the reset function to start
        stage.show();
    }
        //function is static so that it can be used from controller 
        //function is made for the abilliy to reset the scene 
    public static void setingRoot() {
        root = new HBox(10);

        root.getChildren().addAll(vbox, gameController.gameGrid());//gets the grid from the controller
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        stage.setScene(new Scene(root));
    }

    public static void main(String[] args) {
        launch(args);
    }
}