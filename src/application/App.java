package application;

import application.model.SunBed;
import application.model.SunBedCollection;
import application.util.AccessDataFile;
import application.util.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

/**
 * Main class for the Sun Lounger application. The application shows a
 * collection of sun beds that are available for hire for the day. Selecting a
 * sun bed marks it as hired, selecting it again makes it available.
 * 
 * @author Josh James
 *
 */
public class App extends Application {

	// private static final String FILE_NAME_AND_LOCATION = "sunloungers.ser";
	private static final String FILE_NAME_AND_LOCATION = "sunloungers.xml";

	/** 
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {
//		logger.info("JavaFX initializing");

		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/application/view/SunBedView.fxml"));
			Scene scene = new Scene(root);
/**** Explain Objects.requireNonNull - what is the advantage? ****/
			scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/application/resources/css/mainStyle.css")).toExternalForm());

//			scene.getStylesheets()
//					.add(getClass().getResource("/application/resources/css/mainStyle.css").toExternalForm());
//			primaryStage.getIcons()
//					.add(new Image(this.getClass().getResourceAsStream("/application/resources/img/icon.png")));
			
			primaryStage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/application/resources/img/sunbed.png"))));
		
			primaryStage.setTitle("Sun Bed Manager");
			primaryStage.setScene(scene);
			primaryStage.show();

			View.centerOnScreen(primaryStage);
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Before calling the JavaFX launch, ensure we can find and open the file used
	 * to hold the data. First try opening, if that fails, try creating and then
	 * opening the file. If that fails then terminate the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Access the file containing data for the application
		List<SunBed> sunBedsToAdd;
		try {
			sunBedsToAdd = AccessDataFile.readData(FILE_NAME_AND_LOCATION);
			for (int i = 1; i < sunBedsToAdd.size(); i++) {
				SunBedCollection.getInstance().addSunBed();
			}
		}

		catch (Exception e) {
			try {
				sunBedsToAdd = new ArrayList<SunBed>();
				AccessDataFile.writeData(sunBedsToAdd, FILE_NAME_AND_LOCATION);
				sunBedsToAdd = AccessDataFile.readData(FILE_NAME_AND_LOCATION);
			}

			catch (Exception ex) {
				ex.printStackTrace();
				System.exit(0);
			}
		}
		launch(args);
	}
}
