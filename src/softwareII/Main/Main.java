/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareII.Main;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static softwareII.Implementation.DBConnection.conn;

/**
 *
 * @author Nick
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view_controller/LoginForm.fxml"));
        primaryStage.setTitle("My Scheduling Application");
        primaryStage.setScene(new Scene(root, 600, 350));
        primaryStage.show();
    }

    
    public static void main(String[] args) {
            launch(args);
    }

}
