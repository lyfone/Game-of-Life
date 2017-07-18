import javax.swing.*;
import java.awt.*;
/**
 * Created by zzhfeng on 2017/7/7.
 */
public class GamePanel extends JPanel{
    private FrameBase frame;
    private int[][] cellKey;
    private int cellSideLength;
    public GamePanel(FrameBase frame){
        super();
        this.frame = frame;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 重新调用 Graphics 的绘制方法绘制时将自动擦除旧的内容
        drawRect(g);
    }
    private void drawRect(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        for(int i=0;i<cellKey[0].length;i++){
            for(int j=0;j<cellKey[0].length;j++){
                int x = j*cellSideLength;
                int y = i*cellSideLength;
                if(cellKey[i][j] == 1){
                    g2d.fillRect(x,y,cellSideLength,cellSideLength);
                }else{
                    g2d.drawRect(x,y,cellSideLength,cellSideLength);
                }
            }
        }
        g2d.dispose();
    }
    public void setCellKey(int [][] cellKey){
        this.cellKey = cellKey;
    }
    public void  setCellSideLength(int cellSideLength){
        this.cellSideLength = cellSideLength;
    }
}
