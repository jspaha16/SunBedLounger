module app.sunlounger2 {
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires java.desktop;
	requires org.apache.logging.log4j;
	requires javafx.base;
	exports application.view;
	exports application.model;
	opens application;
	opens application.view;
}