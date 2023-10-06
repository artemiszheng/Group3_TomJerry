package game;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageViewHoverEffect extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 400, 400);
        URL imageUrl = null;
        try {
            imageUrl = new File("img/cat.gif").toURI().toURL();
        } catch (MalformedURLException e) {
            throw  new RuntimeException(e);
        }

        Image image = new Image(String.valueOf(imageUrl));
        // 创建ImageView并加载图片
        ImageView imageView = new ImageView(image);

        // 设置ImageView的初始颜色
        imageView.setOnMouseEntered(e -> imageView.setImage(new Image("your_hover_image.png")));
        imageView.setOnMouseExited(e -> imageView.setImage(new Image("your_image.png")));

        // 创建摇晃动画
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(100), imageView);
        rotateTransition.setByAngle(10); // 旋转角度
        rotateTransition.setCycleCount(2); // 摇晃两次
        rotateTransition.setAutoReverse(true);
        rotateTransition.setInterpolator(Interpolator.EASE_BOTH);

        // 添加鼠标悬停事件处理器
        imageView.setOnMouseEntered(event -> {
            imageView.setStyle("-fx-background-color: red");

            rotateTransition.play(); // 播放摇晃动画
        });

        // 添加鼠标离开事件处理器
        imageView.setOnMouseExited(event -> {
            imageView.setStyle("-fx-background-color: black");
            rotateTransition.stop(); // 停止摇晃动画
        });

        root.getChildren().add(imageView);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ImageView Hover Effect");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
