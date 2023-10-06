module game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires javafx.media;
    requires java.desktop;

    opens game to javafx.fxml;
    opens game.controller to javafx.fxml,javafx.controls;
    exports game;
}