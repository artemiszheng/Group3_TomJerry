package game.controller;

import game.util.WindowUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * helpPane controller
 **/
public class HelpController {



    @FXML
    void backAction(ActionEvent event) {
        WindowUtil.ChangeScreen("WelcomePane");
    }
}
