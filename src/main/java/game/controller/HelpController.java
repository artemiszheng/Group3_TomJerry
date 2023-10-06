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
 * helpPane controller
 **/
public class HelpController implements Initializable {



    @FXML
    void backAction(ActionEvent event) {
        WindowUtil.ChangeScreen("WelcomePane");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 设置窗口关闭事件处理器
        Application.stage.setOnCloseRequest((WindowEvent event) -> {
            System.exit(0);
        });
    }
}
