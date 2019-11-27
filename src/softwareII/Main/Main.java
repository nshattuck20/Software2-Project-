package softwareII.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

/**
 *
 * @author Nick
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view_controller/LoginForm.fxml"));

        primaryStage.setTitle("Schedule IT");
        primaryStage.setScene(new Scene(root, 800, 600));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
