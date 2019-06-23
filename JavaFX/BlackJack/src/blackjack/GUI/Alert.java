/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack.GUI;

/**
 *
 * @author Noah Abdelguerfi
 * @since 5/26/2019
 */
public class Alert {

    /**
     *
     * @param title
     * @param message
     * @param alertType Generates a alert box with the specified title,message,
     * and alert type
     */
    public static void displayError(String title, String message, javafx.scene.control.Alert.AlertType alertType) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType);
        alert.setTitle(title);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();

    }
}
