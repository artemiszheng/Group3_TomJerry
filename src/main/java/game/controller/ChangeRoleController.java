package game.controller;

import game.Application;
import game.config.GameConfig;
import game.util.WindowUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;


public class ChangeRoleController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView role;

    @FXML
    private ImageView role1;

    @FXML
    private ImageView role2;

    @FXML
    private ImageView role3;

    @FXML
    private ImageView role4;

    @FXML
    private ImageView role5;

    private ImageView chooseImageView;

    private double initialX;
    private double distance = 100;//右滑距离
    private double initialLayOutX;//初始x位置
    private double initialLayOutY;//初始y位置

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // 设置窗口关闭事件处理器
        Application.stage.setOnCloseRequest((WindowEvent event) -> {
            System.exit(0);
        });
        root.setOnMouseMoved((MouseEvent event) -> {
            double x = event.getSceneX();
            double y = event.getSceneY();

            //选中图片且鼠标右滑偏移量大于0
            if (chooseImageView != null) {
//                chooseImageView.setLayoutX(chooseImageView.getLayoutX()+2);
                chooseImageView.setLayoutX(x);
                //鼠标大于300时恢复位置并取消选中图片
                if (x > 200 || x < 50) {
                    chooseImageView.setLayoutX(initialLayOutX);
                    chooseImageView.setLayoutY(initialLayOutY);
                    role.setImage(chooseImageView.getImage());
                    chooseImageView = null;
                    return;
                }
                //当右滑距离大于设定距离，恢复位置并取消选中图片
//                if(x-initialX>distance){
//                    chooseImageView.setLayoutX(initialLayOutX);
//                    chooseImageView.setLayoutY(initialLayOutY);
//                    role.setImage(chooseImageView.getImage());
//                    chooseImageView = null;
//                }
            }


//            if (chooseImageView != null) {
//                role1.setLayoutX(110);
//                role1.setLayoutY(120);
//
//
//                role2.setLayoutX(110);
//                role2.setLayoutY(220);
//
//
//                role3.setLayoutX(110);
//                role3.setLayoutY(330);
//
//                role4.setLayoutX(110);
//                role4.setLayoutY(440);
//
//
//                role5.setLayoutX(110);
//                role5.setLayoutY(550);
//
//
//            }


        });
    }

    public void setChooseImageView(MouseEvent event, ImageView imageView, int layoutX, int layoutY) {
        double x = event.getSceneX();
        initialX = x;
        initialLayOutX = layoutX;
        initialLayOutY = layoutY;
        chooseImageView = imageView;


    }

    @FXML
    void enter1(MouseEvent event) {
        setChooseImageView(event, role1, 110, 120);
        GameConfig.chooseMousePath = "img/whitemouse_walk.gif";
    }

    @FXML
    void enter2(MouseEvent event) {
        setChooseImageView(event, role2, 110, 220);
        GameConfig.chooseMousePath = "img/Moussepink_walk.gif";
    }

    @FXML
    void enter3(MouseEvent event) {
        setChooseImageView(event, role3, 110, 330);
        GameConfig.chooseMousePath = "img/mouse_walk.gif";
    }

    @FXML
    void enter4(MouseEvent event) {
        setChooseImageView(event, role4, 110, 440);
        GameConfig.chooseMousePath = "img/Mouseblue_walk.gif";
    }

    @FXML
    void enter5(MouseEvent event) {
        setChooseImageView(event, role5, 110, 550);
        GameConfig.chooseMousePath = "img/orangemouse_walk.gif";
    }

    @FXML
    void saveAction(ActionEvent event) {
        WindowUtil.ChangeScreen("LevelChoosePane");
    }

    @FXML
    void backAction(ActionEvent event) {
        WindowUtil.ChangeScreen("WelcomePane");
    }

}
