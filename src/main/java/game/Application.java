package game;

import game.model.Game;
import game.util.WindowUtil;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public  static  Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        WindowUtil.ChangeScreen("WelcomePane");
    }

    public static void main(String[] args) {
        launch();
    }
}