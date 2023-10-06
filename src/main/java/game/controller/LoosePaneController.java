package game.controller;

import game.Application;
import javafx.fxml.Initializable;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;


public class LoosePaneController  implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Application.stage.setOnCloseRequest((WindowEvent event) -> {
            System.exit(0);
        });
    }
}
