package game.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import game.config.GameConfig;
import game.model.Game;
import game.model.Piece;
import game.util.WindowUtil;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * gamePane controller
 **/
public class GameController implements Initializable {
    @FXML
    private GridPane girdPane;
    @FXML
    private Pane pane;
    @FXML
    private Pane loadingPane;

    @FXML
    private AnchorPane ap;
    private Piece[][]gameMap =new Piece[GameConfig.ROW][GameConfig.COL];
    int seconds = 2;//倒计时三秒
    int startmouseRow=0;
    int startmouseCol=0;
    int mouseRow=0;
    int mouseCol=0;
    int catRow=0;
    int catCol=0;
    Random r=new Random();
    int catMoveDirection=0;
    MediaPlayer mplayer;
    int mouseLive=6;
    @FXML
    private Text timeText;
    int meetTomTimes=0;
    int time=60;
    int mouseStep=1;
    int trapStopCount=0;
    List<ImageView> mouseLiveImg=new ArrayList<ImageView>();
    ImageView pressbarimg;
    Piece tempPiece=null;
    ImageView tempImage=null;
    ScheduledExecutorService service=Executors.newScheduledThreadPool(1);
    ScheduledExecutorService service2=Executors.newScheduledThreadPool(1);
    File directory = new File("");
    boolean pause=false;
    @FXML
    private Button pauseBtn;
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Timer timer = new Timer();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //启动三秒倒计时
                startLoading(timer);
            }
        });
        //初始化猫移动的方向
        catMoveDirection=r.nextInt(4);
        GameConfig.viewArea.setFill(Paint.valueOf("transparent"));
        loadImage();
        loadMap();


        service.scheduleAtFixedRate(new Runnable() {

            public void run() {
                Platform.runLater(new Task<Integer>() {
                    protected Integer call() {
                        if(pause)return null;
                        //老鼠遇到猫后时间3秒改变图片
                        if(meetTomTimes!=0){
                            meetTomTimes--;
                        }else{
                            try {
                                GameConfig.mouseWalkNormalPath = String.valueOf(new File("img/mouse_walk.gif").toURI().toURL());
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        int flag=-1;
                        if(catMoveDirection==0){
                            if((catCol+1)<GameConfig.COL){
                                flag=GameConfig.StringMap[catRow][catCol+1];
                                if(flag!=0){
                                    GameConfig.StringMap[catRow][catCol+1]=2;
                                    GameConfig.StringMap[catRow][catCol]=GameConfig.BeforeStringMap[catRow][catCol];
                                    catCol++;
                                }else{
                                    changeCatDirection();
                                }
                            }else{
                                changeCatDirection();
                            }
                        }else if(catMoveDirection==1){
                            if(catCol-1>=0){
                                flag=GameConfig.StringMap[catRow][catCol-1];
                                if(flag!=0){
                                    GameConfig.StringMap[catRow][catCol-1]=2;
                                    GameConfig.StringMap[catRow][catCol]=GameConfig.BeforeStringMap[catRow][catCol];
                                    catCol--;
                                }else{
                                    changeCatDirection();
                                }
                            }else{
                                changeCatDirection();
                            }
                        }else if(catMoveDirection==2){
                            if((catRow-1)>=0){
                                flag=GameConfig.StringMap[catRow-1][catCol];
                                if(flag!=0){
                                    GameConfig.StringMap[catRow-1][catCol]=2;
                                    GameConfig.StringMap[catRow][catCol]=GameConfig.BeforeStringMap[catRow][catCol];;
                                    catRow--;
                                }else{
                                    changeCatDirection();
                                }
                            }else{
                                changeCatDirection();
                            }
                        }else if(catMoveDirection==3){
                            if((catRow+1)<GameConfig.ROW){
                                flag=GameConfig.StringMap[catRow+1][catCol];
                                if(flag!=0){
                                    GameConfig.StringMap[catRow+1][catCol]=2;
                                    GameConfig.StringMap[catRow][catCol]=GameConfig.BeforeStringMap[catRow][catCol];;
                                    catRow++;
                                }else{
                                    changeCatDirection();
                                }
                            }else{
                                changeCatDirection();
                            }
                        }
                        refreshGamePane();
                        if(time>=0){
                            timeText.setText(String.valueOf(time--));
                        }
                        return null;
                    }
                });
            }
        }, 1, 1, TimeUnit.SECONDS);


        service.scheduleAtFixedRate(new Runnable() {

            public void run() {
                Platform.runLater(new Task<Integer>() {
                    protected Integer call() {
                        if(mouseCol==catCol&&mouseRow==catRow){
                            mouseLive--;
                            refreshLiveImage();
                            GameConfig.StringMap[catRow][catCol]=2;
                            mouseRow=startmouseRow;
                            mouseCol=startmouseCol;
                            GameConfig.StringMap[mouseRow][mouseCol]=6;
                            try {
                                GameConfig.mouseWalkNormalPath=String.valueOf(new File("img/touch_track.gif").toURI().toURL());
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            GameConfig.viewArea.setLayoutX(mouseCol*GameConfig.PIECE_SIZE);
                            GameConfig.viewArea.setLayoutY(mouseRow*GameConfig.PIECE_SIZE);

                            refreshGamePane();
                            refreshViewArea();
                            playMusic("file://"+directory.getAbsolutePath().toString().replace('\\', '/')+ "/music/meettom_smile.MP3");
                            meetTomTimes=3;
                        }

                        if(trapStopCount<=4&&trapStopCount>2){
                            refreshPressbar(GameConfig.pressbarPath1);
                        }else if(trapStopCount==2){
                            refreshPressbar(GameConfig.pressbarPath2);
                        }else if(trapStopCount==1){
                            refreshPressbar(GameConfig.pressbarPath3);
                        }else{
                            ap.getChildren().remove(pressbarimg);
                        }
                        gameOver();
                        return null;
                    }
                });
            }
        }, 1000, 200, TimeUnit.MILLISECONDS);
    }

    @FXML
    private void changPause(){
        pause=!pause;
        if(pause){
            pauseBtn.setText("START");
        }else{
            pauseBtn.setText("PAUSE");

        }
    }
    private void gameOver(){
        if(time<=0||mouseLive==0){
            service.shutdownNow();
            playMusic("file://"+directory.getAbsolutePath().toString().replace('\\', '/')+ "/music/loose_game.MP3");
            WindowUtil.alertInfo("You Lose!");
            System.exit(0);
            return;
        }
    }
    private void refreshPressbar(String pressbar){
        ap.getChildren().remove(pressbarimg);
        pressbarimg=new ImageView(pressbar);
        pressbarimg.setLayoutY(mouseRow*GameConfig.PIECE_SIZE-12);
        pressbarimg.setLayoutX(mouseCol*GameConfig.PIECE_SIZE+12);
        pressbarimg.setFitHeight(GameConfig.PIECE_SIZE);
        pressbarimg.setFitWidth(GameConfig.PIECE_SIZE);
        ap.getChildren().add(pressbarimg);
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
    /**
     * 播放音乐方法
     */
    public void playMusic(String bmg) {
        if (mplayer != null)
            mplayer.stop();
        mplayer = new MediaPlayer(new Media(bmg));
        mplayer.play();

    }
    private void changeCatDirection(){
        catMoveDirection=r.nextInt(4);
    }
    private void loadImage() {
        try {

            GameConfig.catPath = String.valueOf(new File("img/cat.gif").toURI().toURL());
            GameConfig.mouseWalkNormalPath = String.valueOf(new File("img/mouse_walk.gif").toURI().toURL());
            GameConfig.cheesePath = String.valueOf(new File("img/cheese.png").toURI().toURL());
            GameConfig.wallPath = String.valueOf(new File("img/wall1.png").toURI().toURL());
            GameConfig.trapPath = String.valueOf(new File("img/trap.png").toURI().toURL());
            GameConfig.heartPath = String.valueOf(new File("img/heart.png").toURI().toURL());
            GameConfig.seedPath = String.valueOf(new File("img/seed.png").toURI().toURL());
            GameConfig.pressbarPath1 = String.valueOf(new File("img/pressbar_empty.png").toURI().toURL());
            GameConfig.pressbarPath2 = String.valueOf(new File("img/pressbar_half.png").toURI().toURL());
            GameConfig.pressbarPath3 = String.valueOf(new File("img/pressbar_full.png").toURI().toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadMap() {
        //解析文本
        if(GameConfig.level==1){
            Game.parseLeveFormFile("level/first.txt");
        }else {
            Game.parseLeveFormFile("level/second.txt");
        }

        //添加图片
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                //找到猫和老鼠的坐标
                for (int i = 0; i < GameConfig.ROW; i++) {
                    for (int j = 0; j < GameConfig.COL; j++) {
                        if(GameConfig.StringMap[i][j]==6){
                            mouseRow=i;
                            mouseCol=j;
                            startmouseRow=i;
                            startmouseCol=j;
                            GameConfig.viewArea.setLayoutX(mouseCol*GameConfig.PIECE_SIZE);
                            GameConfig.viewArea.setLayoutY(mouseRow*GameConfig.PIECE_SIZE);
                            GameConfig.BeforeStringMap[i][j]=1;
                        }else if(GameConfig.StringMap[i][j]==2){
                            catRow=i;
                            catCol=j;
                            GameConfig.BeforeStringMap[i][j]=1;
                        }else{
                            GameConfig.BeforeStringMap[i][j]=GameConfig.StringMap[i][j];
                        }
                    }
                }

                GameConfig.viewArea.setLayoutX(mouseCol*GameConfig.PIECE_SIZE);
                GameConfig.viewArea.setLayoutY(mouseRow*GameConfig.PIECE_SIZE);
                pane.getChildren().add(GameConfig.viewArea);
                refreshGamePane();
                loadingPane.getScene().setOnKeyReleased(k->{
                    if(pause)return;
                    //踩到陷阱暂停
                    if(trapStopCount>0){
                        if(k.getCode().toString().equals("R")){
                            trapStopCount--;
                        }
                        return;
                    }

                    int flag=-1;
                    if(k.getCode().toString().equals("RIGHT")){
                        if((mouseCol+mouseStep)<GameConfig.COL){
                            flag=GameConfig.StringMap[mouseRow][mouseCol+mouseStep];
                            if(flag!=0){
                                GameConfig.viewArea.setLayoutX((mouseCol+mouseStep)*GameConfig.PIECE_SIZE);
                                GameConfig.StringMap[mouseRow][mouseCol+mouseStep]=6;
                                GameConfig.StringMap[mouseRow][mouseCol]=1;
                                mouseCol+=mouseStep;
                            }
                        }
                    }else if(k.getCode().toString().equals("LEFT")){
                        if(mouseCol-mouseStep>=0){
                            flag=GameConfig.StringMap[mouseRow][mouseCol-mouseStep];
                            if(flag!=0){
                                GameConfig.viewArea.setLayoutX((mouseCol-mouseStep-1)*GameConfig.PIECE_SIZE);
                                GameConfig.StringMap[mouseRow][mouseCol-mouseStep]=6;
                                GameConfig.StringMap[mouseRow][mouseCol]=1;
                                mouseCol-=mouseStep;
                            }
                        }

                    }else if(k.getCode().toString().equals("UP")){
                        if((mouseRow-mouseStep)>=0){
                            flag=GameConfig.StringMap[mouseRow-1][mouseCol];
                            if(flag!=0){
                                GameConfig.viewArea.setLayoutY((mouseRow-mouseStep-1)*GameConfig.PIECE_SIZE);
                                GameConfig.StringMap[mouseRow-mouseStep][mouseCol]=6;
                                GameConfig.StringMap[mouseRow][mouseCol]=1;
                                mouseRow-=mouseStep;
                            }
                        }

                    }else if(k.getCode().toString().equals("DOWN")){
                        if((mouseRow+mouseStep)<GameConfig.ROW){
                            flag=GameConfig.StringMap[mouseRow+mouseStep][mouseCol];
                            if(flag!=0){
                                GameConfig.viewArea.setLayoutY((mouseRow+mouseStep)*GameConfig.PIECE_SIZE);
                                GameConfig.StringMap[mouseRow+mouseStep][mouseCol]=6;
                                GameConfig.StringMap[mouseRow][mouseCol]=1;
                                mouseRow+=mouseStep;
                            }
                        }
                    }else if(k.getCode().toString().equals("CONTROL")){
                        if(mouseStep==1){
                            mouseStep=2;
                        }else{
                            mouseStep=1;
                        }
                    }

                    //踩到陷阱,生命减1
                    if(flag==3){
                        trapStopCount=4;
                        playMusic("file://"+directory.getAbsolutePath().toString().replace('\\', '/')+ "/music/meet_trap.MP3");
                        mouseLive--;
                        refreshLiveImage();
                    }else if(flag==4){
                        playMusic("file://"+directory.getAbsolutePath().toString().replace('\\', '/')+ "/music/Heal_seed.MP3");
                        mouseLive++;
                        refreshLiveImage();
                    }else if(flag==5){
                        service.shutdownNow();
                        playMusic("file://"+directory.getAbsolutePath().toString().replace('\\', '/')+ "/music/win_music.MP3");
                        WindowUtil.alertInfo("You Win!");
                        System.exit(0);
                    }
                    refreshGamePane();
                    refreshViewArea();

                });

                loadingPane.getScene().setOnMousePressed(e->{
                    if(pause)return;
                    tempPiece=selectGz(e.getX(), e.getY());
                });
                loadingPane.getScene().setOnMouseDragged(e->{
                    if(pause)return;
                    if(tempPiece!=null){
                        ap.getChildren().remove(tempImage);
                        tempImage=new ImageView(GameConfig.seedPath);
                        tempImage.setLayoutX(e.getX());
                        tempImage.setLayoutY(e.getY());
                        tempImage.setFitHeight(GameConfig.PIECE_SIZE);
                        tempImage.setFitWidth(GameConfig.PIECE_SIZE);
                        ap.getChildren().add(tempImage);
                    }
                });
                loadingPane.getScene().setOnMouseReleased(e->{
                    if(pause)return;
                    ap.getChildren().remove(tempImage);
                    if(mouseEatSeed(e.getX(), e.getY())!=null){
                        playMusic("file://"+directory.getAbsolutePath().toString().replace('\\', '/')+ "/music/Heal_seed.MP3");
                        mouseLive++;
                        GameConfig.StringMap[tempPiece.getRow()][tempPiece.getCol()]=1;
                        refreshLiveImage();
                    }
                });
                refreshLiveImage();
            }
        });


    }
    public Piece mouseEatSeed(double x,double y){
        for (int i = 0; i < GameConfig.ROW; i++) {
            for (int j = 0; j < GameConfig.COL; j++) {
                if(gameMap[i][j].getFlag()==6&&gameMap[i][j].getBoundsInParent().contains(x, y)){
                    return gameMap[i][j];
                }
            }
        }
        return null;
    }
    public Piece selectGz(double x,double y){
        for (int i = 0; i < GameConfig.ROW; i++) {
            for (int j = 0; j < GameConfig.COL; j++) {
                if(gameMap[i][j].getFlag()==4&&gameMap[i][j].getBoundsInParent().contains(x, y)){
                    return gameMap[i][j];
                }
            }
        }
        return null;
    }
    public void refreshViewArea(){
        pane.getChildren().remove(GameConfig.viewArea);
        pane.getChildren().add(GameConfig.viewArea);
    }
    public void refreshLiveImage(){
        for (int i = 0; i < mouseLiveImg.size(); i++) {
            ap.getChildren().remove(mouseLiveImg.get(i));
        }
        mouseLiveImg.clear();
        for (int i = 0; i < mouseLive; i++) {
            ImageView iv=new ImageView(GameConfig.heartPath);
            iv.setLayoutY(133);
            iv.setLayoutX(643+i*60);
            iv.setFitHeight(35);
            iv.setFitWidth(42);
            mouseLiveImg.add(iv);
            ap.getChildren().add(iv);
        }
    }
    public void refreshGamePane(){
        girdPane.getChildren().clear();
        // 打印二维数组
        for (int i = 0; i < GameConfig.ROW; i++) {
            for (int j = 0; j < GameConfig.COL; j++) {
                //girdPane是先列后行！
                Piece piece = new Piece(GameConfig.StringMap[i][j],i,j);
                girdPane.add(piece,j,i);
                gameMap[i][j]= piece;
                if(GameConfig.StringMap[i][j]==6){
                    mouseRow=i;
                    mouseCol=j;
                }
            }
        }

    }

}
