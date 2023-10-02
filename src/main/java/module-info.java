module game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires javafx.media;

    opens game to javafx.fxml;
    opens game.controller to javafx.fxml,javafx.controls;
    exports game;
}