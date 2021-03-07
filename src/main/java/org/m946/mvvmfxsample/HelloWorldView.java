package org.m946.mvvmfxsample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class HelloWorldView implements FxmlView<HelloWorldVM>, Initializable {
    @FXML
    private Label helloLabel;

    @InjectViewModel
    private HelloWorldVM vm;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        helloLabel.textProperty().bind(vm.helloMessage());

    }
}
