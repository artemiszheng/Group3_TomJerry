package game.controller;

import game.util.WindowUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * welcomePane controller
 **/
public class WelcomeController {

    @FXML
    void exitAction(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void helpAction(ActionEvent event) {
        WindowUtil.ChangeScreen("HelpPane");
    }

    @FXML
    void startAction(ActionEvent event) {
        WindowUtil.ChangeScreen("LevelChoosePane");
    }
}
