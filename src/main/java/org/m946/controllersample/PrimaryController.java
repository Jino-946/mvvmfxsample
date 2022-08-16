package org.m946.controllersample;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        MVCApp.setRoot("secondary");
    }
}
