package org.m946.mvvmfxsample;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CountryApp extends Application {
	
	public static void main(String... args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Country Demo");
		
		final ViewTuple<CountryView, CountryVM> viewTuple = FluentViewLoader.fxmlView(CountryView.class).load();
		final Parent root = viewTuple.getView(); 
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

}
