package windowCreators;

import com.jfoenix.controls.JFXSnackbar;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SnackBarCreator {
    public static void showSnackBar(AnchorPane rootPane , String messageText){
        JFXSnackbar snackbar;
        snackbar =new JFXSnackbar(rootPane);
        Label message=new Label(messageText);
        message.setTextFill(Color.WHITE);
        message.setBackground(Background.fill(Color.BLACK));
        message.setStyle("-fx-font-size:24");
        final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(message, Duration.seconds(3.33), null);
        snackbar.enqueue(snackbarEvent);
    }
    public static void showSnackBar(HBox rootPane , String messageText){
        JFXSnackbar snackbar;
        snackbar =new JFXSnackbar(rootPane);
        Label message=new Label(messageText);
        message.setTextFill(Color.WHITE);
        message.setBackground(Background.fill(Color.BLACK));
        message.setStyle("-fx-font-size:24");
        final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(message, Duration.seconds(3.33), null);
        snackbar.enqueue(snackbarEvent);
    }
    public static void showSnackBar(VBox rootPane , String messageText){
        JFXSnackbar snackbar;
        snackbar =new JFXSnackbar(rootPane);
        Label message=new Label(messageText);
        message.setTextFill(Color.WHITE);
        message.setBackground(Background.fill(Color.BLACK));
        message.setStyle("-fx-font-size:24");
        final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(message, Duration.seconds(3.33), null);
        snackbar.enqueue(snackbarEvent);
    }
}
