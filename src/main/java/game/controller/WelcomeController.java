package game.controller;

import game.Application;
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
public class WelcomeController implements Initializable {

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
        WindowUtil.ChangeScreen("ChangeRolePane");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 设置窗口关闭事件处理器
        Application.stage.setOnCloseRequest((WindowEvent event) -> {
            System.exit(0);
        });
    }
}
