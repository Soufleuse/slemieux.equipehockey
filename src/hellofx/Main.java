package hellofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * @param primaryStage Là où mettre les contrôles de la fenêtre.
     * @throws Si une exception survient, la jette par-dessus bord.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("equipe.fxml"));
        primaryStage.setTitle("Ligue de hockey");
        Scene maScene = new Scene(root, 600, 600);
        maScene.getStylesheets().add(getClass().getResource("defaut.css").toExternalForm());
        primaryStage.setScene(maScene);
        primaryStage.show();
    }

    /**
     * La fonction principale.
     * 
     * @param args Les aguments passés à la fonction principale.
     */
    public static void main(String[] args) {
        launch(args);
    }
}