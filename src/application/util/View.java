package application.util;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Utility class with method to centre window on screen.
 * @author Josh James
 *
 */
public class View {

    private View() {
        throw new IllegalStateException("View Utility Class");
    }

    /**
     * Used to center the window on the screen
     * 
     * @param stage The object of the window as a Stage
     */
    public static void centerOnScreen(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
        double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;

        stage.setX(centerX);
        stage.setY(centerY);
    }

    /**
     * Used to set the properties of the secondary Stage
     * 
     * @param secondaryStage The object of the secondary window as a Stage
     * @param title          The title of the secondary window as a String
     */
    public static void setSecondaryStage(Stage secondaryStage, String title) {
        secondaryStage.setTitle(title);

        if (secondaryStage.getStyle() != StageStyle.UTILITY) {
            secondaryStage.initStyle(StageStyle.UTILITY);
        }

        if (secondaryStage.getModality() != Modality.APPLICATION_MODAL) {
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
        }

        secondaryStage.showAndWait();
        secondaryStage.setResizable(false);
        centerOnScreen(secondaryStage);
    }

    public static void closeStage(Node n) {
        Stage thisStage = (Stage) n.getScene().getWindow();
        thisStage.close();
    }

    public static void showAlert(String type, String title, String header, String content) {
        Alert alert;
        if (type.equalsIgnoreCase("NONE")) {
            alert = new Alert(AlertType.NONE);
        }

        else if (type.equalsIgnoreCase("INFORMATION")) {
            alert = new Alert(AlertType.INFORMATION);
        }

        else if (type.equalsIgnoreCase("WARNING")) {
            alert = new Alert(AlertType.WARNING);
        }

        else if (type.equalsIgnoreCase("CONFIRMATION")) {
            alert = new Alert(AlertType.CONFIRMATION);
        }

        else {
            alert = new Alert(AlertType.ERROR);
        }

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
