package game.config;

/**
 * 游戏配置类：存放一些游戏相关的全局变量和常亮
 **/


public class GameConfig {

    //游戏面板的行数和列数
    public static final int ROW = 20;
    public static final int COL = 20;

    public static final int PIECE_SIZE = 30;    // 每块大小

    public static int[][] StringMap = new int[ROW][COL];

    public static int level = 1;

    //游戏图片路径

    public static String catPath = null;
    public static String cheesePath = null;

    public static String mouseWalkNormalPath = null;

    public static String wallPath = null;

    public static String trapPath = null;

    public static String seedPath = null;
}
