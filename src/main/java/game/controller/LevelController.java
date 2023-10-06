package game.controller;

import game.Application;
import game.config.GameConfig;
import game.util.SoundEffect;
import game.util.WindowUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * welcomePane controller
 **/
public class LevelController  implements Initializable {

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
        SoundEffect.BACKMUSIC.stop();
        SoundEffect.GAMEMUSIC.setToLoop();
        SoundEffect.GAMEMUSIC.play();

    }
    /**
     * level：hard
     * @param event
     */
    @FXML
    void Leve2Action(ActionEvent event) {
        GameConfig.level =2;
        WindowUtil.ChangeScreen("GamePane");
        SoundEffect.BACKMUSIC.stop();
        SoundEffect.GAMEMUSIC.setToLoop();
        SoundEffect.GAMEMUSIC.play();
    }

    @FXML
    void helpAction(ActionEvent event) {
        WindowUtil.ChangeScreen("HelpPane");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 设置窗口关闭事件处理器
        Application.stage.setOnCloseRequest((WindowEvent event) -> {
            System.exit(0);
        });
    }
}
