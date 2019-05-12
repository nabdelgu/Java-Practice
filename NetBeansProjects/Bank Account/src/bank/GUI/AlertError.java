package bank.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Used to display a dialog box for data input validation
 * 
 * @author noaha
 * @since 05/12/2019
 * 
 */
public class AlertError {

    public static void displayError(String title,String message,AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();

    }
}
