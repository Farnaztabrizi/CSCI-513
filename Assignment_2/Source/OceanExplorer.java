//****************************
// author: Farnaz Tabrizi
// Course: Software Engineering-Csci 513
//****************************

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.FileNotFoundException;

public class OceanExplorer extends Application {


    private OceanMap map;
    private GridPane grid;
    private Ship ccs;

    public static void main(String[] args) {
        launch(args);
    }

    public static void piratesWins(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION) ;
        alert.setTitle("game finished!!!");
        alert.setContentText("the pirates catch Christopher Columbus and win the game!");
        alert.setHeaderText(null);
        alert.showAndWait();
        close();
    }

    public static void close(){
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        map = new OceanMap();
        ccs = map.getCcs();
        grid = map.getGrid();
        StackPane root = new StackPane();
        root.getChildren().add(grid);
        Scene scene = new Scene(root, 900, 900);
        scene.setOnKeyPressed(event -> {
            try {
                switch (event.getCode()) {
                    case UP:
                        ccs.changeLocation(ccs.getColumnIndex(), ccs.getRowIndex() - 1);
                        break;
                    case DOWN:
                        ccs.changeLocation(ccs.getColumnIndex(), ccs.getRowIndex() + 1);
                        break;
                    case LEFT:
                        ccs.changeLocation(ccs.getColumnIndex() - 1, ccs.getRowIndex());
                        break;
                    case RIGHT:
                        ccs.changeLocation(ccs.getColumnIndex() + 1, ccs.getRowIndex());
                        break;
                    case ESCAPE:
                        close();
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
            }
        });
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Christopher Columbus Sails the Ocean Blue");
        primaryStage.show();
    }
}