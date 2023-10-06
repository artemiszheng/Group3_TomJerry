package game.controller;

import game.Application;
import javafx.fxml.Initializable;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;


public class WinPaneController   implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 设置窗口关闭事件处理器
        Application.stage.setOnCloseRequest((WindowEvent event) -> {
           System.exit(0);
        });

    }
}
