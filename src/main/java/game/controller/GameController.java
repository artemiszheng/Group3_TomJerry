package game.controller;

import game.config.GameConfig;
import game.model.Game;
import game.model.Piece;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * gamePane controller
 **/
public class GameController implements Initializable {
    @FXML
    private GridPane girdPane;

    @FXML
    private Pane loadingPane;

    private Piece[][]gameMap =new Piece[GameConfig.ROW][GameConfig.COL];
    int seconds = 2;//倒计时三秒
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Timer timer = new Timer();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //启动三秒倒计时
                startLoading(timer);
            }
        });

        loadImage();
        loadMap();

    }

    private void startLoading(Timer timer) {
        timer.schedule(new TimerTask() {
            int count = seconds;

            @Override
            public void run() {
                if (count >= 0) {
                    System.out.println(count);
                    count--;
                    girdPane.setOpacity(0.5);
                } else {
                    girdPane.setOpacity(1);
                    loadingPane.setVisible(false);
                    timer.cancel(); // 停止计时器
                }
            }
        }, 0, 1000); // 0 表示立即开始，每隔 1000 毫秒执行一次
    }

    private void loadImage() {
        try {

            GameConfig.catPath = String.valueOf(new File("src/main/resources/img/cat.gif").toURI().toURL());
            GameConfig.mouseWalkNormalPath = String.valueOf(new File("src/main/resources/img/mouse_walk.gif").toURI().toURL());
            GameConfig.cheesePath = String.valueOf(new File("src/main/resources/img/cheese.png").toURI().toURL());
            GameConfig.wallPath = String.valueOf(new File("src/main/resources/img/wall1.png").toURI().toURL());
            GameConfig.trapPath = String.valueOf(new File("src/main/resources/img/trap.png").toURI().toURL());
            GameConfig.seedPath = String.valueOf(new File("src/main/resources/img/seed.png").toURI().toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadMap() {
        //解析文本
        if(GameConfig.level==1){
            Game.parseLeveFormFile("src/main/resources/level/first.txt");
        }else {
            Game.parseLeveFormFile("src/main/resources/level/second.txt");
        }

        //添加图片
    Platform.runLater(new Runnable() {

        @Override
        public void run() {
            System.out.println("加载地图---------------------------------");
            // 打印二维数组
            for (int i = 0; i < GameConfig.ROW; i++) {
                for (int j = 0; j < GameConfig.COL; j++) {
                    System.out.print(GameConfig.StringMap[i][j] + " ");
                    //girdPane是先列后行！
                    Piece piece = new Piece(GameConfig.StringMap[i][j]);
                    girdPane.add(piece,j,i);
                    gameMap[i][j]= piece;
                }
                System.out.println();
            }
            System.out.println("---------------------------------------------");
        }
    });


    }

}
