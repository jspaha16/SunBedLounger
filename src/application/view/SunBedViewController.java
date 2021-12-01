package application.view;

import application.model.SunBedCollection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.* ;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.* ;
import javafx.scene.image.ImageView ;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller for the main Sun Lounger screen.
 * Draws the sunbeds, adds and removes sunbeds and 
 * clears all sunbeds making them available for hire.
 *
 */
public class SunBedViewController implements Initializable
{
	public AnchorPane rootAnchorPane, homeAnchorPane, sunbedsAnchorPane;
	public TabPane tabPane ;
	public Tab homeTab, sunbedsTab;
	public VBox vbox ;
	public HBox hbox ;
	public ScrollPane scrollPane ;
	public Label prompt ;
	public TextField textField;
	public Button submitBtn, endDayBtn ;
	public ImageView logoImageView ;

	@FXML
	GridPane gridPane ;

	private int numOfSunBeds ;


	/**
	 * Calls the <code>SunBedCollection</code> class to get details about the sunbeds that need to be rendered
	 * and then draws them in the UI.
	 * A GridPane is created in the first tab (i.e. "sunbedsTab") that will contain the buttons(which represent the sunbeds).
	 * Whenever the root layout is resized, the drawSunBeds method is called to reposition the buttons so that
	 * they all fit inside the window properly.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		this.gridPane = new GridPane() ;
		this.sunbedsAnchorPane.getChildren().add(gridPane) ;

		this.rootAnchorPane.widthProperty().addListener((obs, oldVal, newVal) -> drawSunBeds()) ;

		this.textField.setOnKeyPressed(keyEvent ->
		{
			if(keyEvent.getCode() == KeyCode.ENTER) submit() ;
		}) ;

		this.submitBtn.setId("submit_btn_style") ;
		this.endDayBtn.setId("end_day_btn_style") ;

		endDayBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
		       @Override
		       public void handle(MouseEvent e){
		    	  System.out.println("End Button pressed!");
		          SunBedCollection.getInstance().clearSunBeds();
		          numOfSunBeds = SunBedCollection.getInstance().getCount() ;
		          drawSunBeds() ;
		       }
		   });
		
		/* For the dark theme use these
		this.prompt.setStyle("-fx-text-fill: white") ;
		this.tabPane.setStyle("-fx-background-color: #393e46") ;
		this.sunbedsAnchorPane.setStyle("-fx-background-color: black") ; */

		/* For the light theme use these */
		this.prompt.setStyle("-fx-text-fill: black") ;
		this.homeAnchorPane.setStyle("-fx-background-color: linear-gradient(gray, white, white, gray)") ;
		this.sunbedsAnchorPane.setStyle("-fx-background-color: linear-gradient(gray, white, white, gray)") ;
	}


	/**
	 * This method is called at the beginning of the application only when data about the sunbeds on that day is found stored in
	 * disk.
	 * @param option when true, will use the value from the SunBedCollection getCount method to determine how many
	 * buttons to create and display the second tab (i.e. "sunbedsTab") showing the sunbeds. And when false, clear the sunbeds from
	 * the SunBedCollection class by calling the clearSunBeds method and then display the first tab (i.e. "homeTab").
	 */
	private void loadSunBeds(boolean option)
	{
		if(option)
		{
			this.numOfSunBeds = SunBedCollection.getInstance().getCount() ;

			drawSunBeds() ;
			this.tabPane.getSelectionModel().select(sunbedsTab) ;
		}
		else
		{
//			SunBedCollection.getInstance().clearSunBeds() ;
			this.tabPane.getSelectionModel().select(homeTab) ;
		}
	}


	/**
	 * This method is called once the user clicks the "submitBtn" or ENTER key after entering a number in the text field.
	 * The method will get the text entered and set it as the number of sunbeds to draw
	 */
	public void submit()
	{
		if(!textField.getText().isBlank())
		{
			this.numOfSunBeds = Integer.parseInt(textField.getText()) ;
			textField.setText("");

			System.out.println("Creating sun beds");
			for(int i = 0 ; i < this.numOfSunBeds ; i++) {
				SunBedCollection.getInstance().addSunBed() ;
			}

			drawSunBeds() ;
			this.tabPane.getSelectionModel().select(sunbedsTab) ;
		}
	}


	/**
	 * This method will draw a number of buttons using the value held in the variable "numOfSunBeds".
	 * The buttons represent the sunbeds.
	 * The buttons are positioned depending on the size of the root layout.
	 * Before drawing the button, the state (occupied or not) of the sunbed will be used to determine the background color
	 * of the button.
	 */
	private void drawSunBeds()
	{
		int buttonWidth = 100 ;
		int buttonHeight = 80 ;
		int hGap = 4 ;
		int vGap = 4 ;
		int padding = 15 ;

		int cellCount, row, col, maxNumOfRows, maxNumOfCols, numOfBtnsHorizontally ;
		double widthOfRoot ;

		gridPane.getChildren().clear() ;
		gridPane.setHgap(hGap) ;
		gridPane.setVgap(vGap) ;
		gridPane.setPadding(new Insets(padding)) ;


		// Get the width of the scene
		widthOfRoot = (this.rootAnchorPane.getWidth() == 0)?
				this.rootAnchorPane.getPrefWidth() : this.rootAnchorPane.getWidth() ;

		// Calculate the number of buttons on each row.
		numOfBtnsHorizontally = (int) (widthOfRoot - 2*padding) / buttonWidth ;

		maxNumOfRows = numOfSunBeds ;
		maxNumOfCols = (int) ((widthOfRoot - 2*padding) - (numOfBtnsHorizontally-1)*hGap) / buttonWidth ;

		cellCount = 0 ;
		row = 0 ;
		while(cellCount < this.numOfSunBeds && row < maxNumOfRows)
		{
			col = 0 ;
			while(cellCount < this.numOfSunBeds && col < maxNumOfCols)
			{
				Button btn = new Button(Integer.toString(cellCount+1)) ;
				btn.setPrefWidth(buttonWidth) ;
				btn.setPrefHeight(buttonHeight) ;
				btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE) ;
				btn.setOnAction(this::buttonClicked) ;
				btn.setId("sun_bed_btn_style") ;

//				if(SunBedCollection.getInstance().isOccupied(cellCount+1))
				if(SunBedCollection.getInstance().isOccupied(cellCount))
				{
					btn.setStyle("-fx-background-color: orange;" +
								 "-fx-text-fill: black;") ;
				}
				else
				{
					btn.setStyle("-fx-background-color: #393e46;" +
								 "-fx-text-fill: white;") ;
				}

				GridPane.setConstraints(btn, col, row) ;

				gridPane.getChildren().add(btn) ;

				cellCount++ ;
				col++ ;
			}

			row++ ;
		}
	}


	/**
	 * This method is called when the user clicks on a button that's in the second tab (i.e. "sunBedsTab").
	 * The sunbed whose id is identified by the button's text will have it's state toggled.
	 * The background colour of the button will then be changed depending on the sunbed's state.
	 * Grey (hex: #393e46) indicates that the sunbed is available and orange indicates that it's occupied.
	 * @param actionEvent is used to get the text that's been set to the button. This text represents the sunbed's id.
	 */
	private void buttonClicked(ActionEvent actionEvent)
	{
		String btnText = ((Button) actionEvent.getSource()).getText() ;
		int sunBedId = Integer.parseInt(btnText) - 1;

		SunBedCollection.getInstance().toggleSunBed(sunBedId) ;

		if(SunBedCollection.getInstance().isOccupied(sunBedId))
		{
			((Button) actionEvent.getSource()).setStyle("-fx-background-color: orange;" +
														"-fx-text-fill: black;") ;
		} else
		{
			((Button) actionEvent.getSource()).setStyle("-fx-background-color: #393e46;" +
														"-fx-text-fill: white;") ;
		}
	}
}
