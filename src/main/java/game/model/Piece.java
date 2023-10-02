package game.model;

import game.config.GameConfig;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * 方块类：封装格子方块的属性以及方法。
 **/
public class Piece  extends StackPane {

    //文本字符，对应角色
    private int flag;

    String backColorByCSS ="-fx-background-color:#ffffff;";
    String backColorCoverByCSS ="-fx-background-color:#F2EBFB;";
    String backColorNumberByCSS ="-fx-background-color:#cab1f1;";



    private int row;
    private int col;

    public Piece(int flag,int row,int col) {
        //设置大小和背景色
        this.setPrefHeight(GameConfig.PIECE_SIZE);
        this.setPrefWidth(GameConfig.PIECE_SIZE);
        this.setStyle("-fx-background-color:#ffffff;" +        "-fx-padding: 0px;"
        );
        this.flag =flag;
        this.row=row;
        this.col=col;
        parseStringAndDraw(flag);
        if(flag!=6&&!GameConfig.viewArea.getBoundsInParent().intersects(new Rectangle(col*GameConfig.PIECE_SIZE, row*GameConfig.PIECE_SIZE, GameConfig.PIECE_SIZE,GameConfig.PIECE_SIZE).getBoundsInLocal())){
            Rectangle rect = new Rectangle(GameConfig.PIECE_SIZE,GameConfig.PIECE_SIZE);
            //设置透明度
            rect.setOpacity(0.8);
            this.getChildren().add(rect);
        }


    }

    /**
     * 解析字符串并画出图片
     */
    private void parseStringAndDraw(int flag) {
        //0代表墙壁
        if (flag == 0){
            ImageView imageView = getImg(GameConfig.PIECE_SIZE,GameConfig.PIECE_SIZE,GameConfig.wallPath);
            this.getChildren().add(imageView);

        }

        //2代表猫
        if (flag == 2){
            ImageView imageView = getImg(GameConfig.PIECE_SIZE,GameConfig.PIECE_SIZE,GameConfig.catPath);
            this.getChildren().add(imageView);

        }

        //3代表陷阱
        if (flag == 3){
            ImageView imageView = getImg(GameConfig.PIECE_SIZE,GameConfig.PIECE_SIZE,GameConfig.trapPath);
            this.getChildren().add(imageView);
        }

        //4代表瓜子
        if (flag == 4){
            ImageView imageView = getImg(GameConfig.PIECE_SIZE,GameConfig.PIECE_SIZE,GameConfig.seedPath);
            this.getChildren().add(imageView);

        }

        //5代表奶酪
        if (flag == 5){
            ImageView imageView = getImg(GameConfig.PIECE_SIZE,GameConfig.PIECE_SIZE,GameConfig.cheesePath);
            this.getChildren().add(imageView);
        }

        //6代表老鼠
        if (flag == 6){
            ImageView imageView = getImg(GameConfig.PIECE_SIZE,GameConfig.PIECE_SIZE,GameConfig.mouseWalkNormalPath);
            this.getChildren().add(imageView);
        }
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    /**
     * 创建并返回一个图像视图。
     *
     * @param width  图像的宽度
     * @param height 图像的高度
     * @param imgURL 图像的URL
     * @return ImageView对象
     */
    public static ImageView getImg(int width, int height, String imgURL) {
        Image img = new Image(imgURL);
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(height);
        imgView.setFitWidth(width);
        return imgView;
    }

}
