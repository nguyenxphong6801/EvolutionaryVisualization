package visual;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Visual extends Application{
    public void start(Stage primaryStage) throws Exception {
    	try {
    		Parent root = FXMLLoader.load(getClass()
    				.getResource("/visual/Visual.fxml"));
 
    		primaryStage.setTitle("Evolutionary Algorithm");
    		primaryStage.setScene(new Scene(root));
    		primaryStage.show();
    	} catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}