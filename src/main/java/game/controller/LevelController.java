package game.controller;

import game.config.GameConfig;
import game.util.WindowUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * welcomePane controller
 **/
public class LevelController {

    @FXML
    void exitAction(ActionEvent event) {
        System.exit(0);

    }

    /**
     * level：easy
     * @param event
     */
    @FXML
    void Leve1Action(ActionEvent event) {

        GameConfig.level =1;
        WindowUtil.ChangeScreen("GamePane");

    }
    /**
     * level：hard
     * @param event
     */
    @FXML
    void Leve2Action(ActionEvent event) {
        GameConfig.level =2;
        WindowUtil.ChangeScreen("GamePane");
    }

    @FXML
    void helpAction(ActionEvent event) {
        WindowUtil.ChangeScreen("HelpPane");
    }
}
