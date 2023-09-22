package game.util;


import game.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

/**
 * change stage
 */
public class WindowUtil {

    /**
     * change stage
     * 切换窗口
     *
     * @param newViewName
     */
    public static void ChangeScreen(String newViewName) {
        Stage stage = Application.stage;
        try {
            URL url = new File("src/main/resources/view/" + newViewName + ".fxml").toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);


            AnchorPane anchorPane = null;

            Scene scene = null;

            anchorPane = fxmlLoader.load();


            scene = new Scene(anchorPane);


            stage.setTitle("Tom&Jerry");
            //添加icon
            URL imageUrl = new File("src/main/resources/img/cat.gif").toURI().toURL();

            Image image = new Image(String.valueOf(imageUrl));
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);



            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pop frame information
     * 弹框信息
     * @param msg
     * @return
     */
    public static boolean alertInfo(String msg) {


        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, new ButtonType("OK", ButtonBar.ButtonData.YES));
        alert.setContentText(msg);
        alert.setHeaderText("");
        // set stage
        alert.setTitle("warn");
        URL imageUrl = null;
        try {
            imageUrl = new File("src/main/resources/image/Publisher_material/Display_1.jpg").toURI().toURL();
        } catch (MalformedURLException e) {
            throw  new RuntimeException(e);
        }

        Image image = new Image(String.valueOf(imageUrl));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(image);

        Optional<ButtonType> buttonType = alert.showAndWait();

        return buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES);

    }

    /**
     * Pop-up warning
     * 弹框警告
     * @param msg
     * @return
     */
    public static boolean alertWarn(String msg) {


        Alert alert = new Alert(Alert.AlertType.WARNING, msg, new ButtonType("OK", ButtonBar.ButtonData.YES));
        alert.setContentText(msg);
        alert.setHeaderText("");

        alert.setTitle("warn");
        URL imageUrl = null;
        try {
            imageUrl = new File("src/main/resources/image/Publisher_material/Display_1.jpg").toURI().toURL();
        } catch (MalformedURLException e) {
            throw  new RuntimeException(e);
        }

        Image image = new Image(String.valueOf(imageUrl));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(image);

        Optional<ButtonType> buttonType = alert.showAndWait();

        return buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES);

    }

    /**
     * Pack input
     * @return
     */
    public static TextInputDialog inputDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("tip");
        dialog.setHeaderText("content");
        URL imageUrl = null;
        try {
            imageUrl = new File("src/main/resources/image/Publisher_material/Display_1.jpg").toURI().toURL();
        } catch (MalformedURLException e) {
            throw  new RuntimeException(e);
        }

        Image image = new Image(String.valueOf(imageUrl));
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(image);


        return dialog;
    }

}
