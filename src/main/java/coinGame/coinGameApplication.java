package coinGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.tinylog.Logger;
public class coinGameApplication extends Application{
    private FXMLLoader fxmlLoader = new FXMLLoader();

    @Override
    public void start(Stage stage) throws Exception {
        Logger.info("Starting application");
        fxmlLoader.setLocation(getClass().getResource("/fxml/opening.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Board Game");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
