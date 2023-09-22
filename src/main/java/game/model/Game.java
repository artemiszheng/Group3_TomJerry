package game.model;

import game.config.GameConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
游戏类：封装了一些游戏相关的方法
 **/
public class Game {

    //解析文本
    public static void parseLeveFormFile(String filePath){
        try {

            //指定路径
            File file = new File(filePath);


            // 打开文件并创建 BufferedReader 对象
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // 计算行数和列数
            int numRows = 0;
            int numCols = 0;

            String line;
            while ((line = reader.readLine()) != null) {
                numRows++;
                if (numCols == 0) {
                    numCols = line.length();
                }
            }

            // 关闭文件并重新打开以读取数据
            reader.close();
            reader = new BufferedReader(new FileReader(file));

            // 创建二维数组
            int[][] array = new int[numRows][numCols];

            // 读取文件并解析数据
            int row = 0;
            while ((line = reader.readLine()) != null) {
                for (int col = 0; col < numCols; col++) {
                    char c = line.charAt(col);
                    array[row][col] = Character.getNumericValue(c);

                }
                row++;
            }

            GameConfig.StringMap =array;

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
