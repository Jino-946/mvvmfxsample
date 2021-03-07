package org.m946.mvvmfxsample;


import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Starter extends Application {

    public static void main(String... args) {
        Application.launch(args);
    }
    

    @Override
    public void start(Stage stage) throws Exception {
        ViewTuple<HelloWorldView, HelloWorldVM> viewTuple = FluentViewLoader.fxmlView(HelloWorldView.class).load();
    
        Parent root = viewTuple.getView();
        stage.setScene(new Scene(root));
        stage.show();		
    }

}
