import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zzhfeng on 2017/7/7.
 */
public class Main {
    private static GamePanel gamePanel;
    private static FrameBase frameBase;
    private static int[][] cellKey;         //格子细胞存活状态列表
    private static int[][] partner;             //每个格子细胞伙伴数列表
    private static int cellRows = 16;           //格子的行和列数
    private static boolean gameActive = false;  //是否在游戏中
    private static boolean startFlag = true;    //
    private static Timer paintTimer;
    private static int paintTimerState = 1;
    private static int cellSideLength = 15;     //格子边长
    private static boolean gameExist = false;
    private static Demo demo = new Demo("config.properties");
    private static final FuncHandle funchandle = new FuncHandle();
    public static void main(String []args){
        GameMenu gameMenu = new GameMenu("menu",240,200);
    }
    public static class GameMenu {
        private FrameBase frameBase;
        public GameMenu(String title,int width, int height){
            frameBase = new FrameBase(title,width,height);
            JPanel jp = new JPanel();
            MyButton create = new MyButton("create",0,30,80,30);
            MyButton start = new MyButton("start",0,0,80,30);
            MyButton reset = new MyButton("reset",80,0,80,30);
            MyButton pause = new MyButton("pause",160,0,80,30);
            MyButton add = new MyButton("add",80,30,80,30);
            TextField textField = new TextField();
            textField.setBounds(80,30,80,30);
            jp.add(start);
            jp.add(reset);
            jp.add(pause);
            jp.add(create);
            jp.add(textField);
            jp.add(add);
            frameBase.add(jp);
            jp.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
            JLabel label=new JLabel("预设demo:");
            jp.add(label);
            JComboBox comboBox=new JComboBox();
            comboBox.addItem("----");
            int[][][] demos = demo.getDemo();
            for(int i =0;i<demos.length;i++){
                comboBox.addItem("demo" + Integer.toString(i + 1));
            }
            jp.add(comboBox);
            frameBase.setLocation(1000,500);
            frameBase.setVisible(true);
            start.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    start();
                }
            });
            reset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    reset(pause);
                }
            });
            pause.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pause(pause);
                }
            });
            create.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int num = Integer.parseInt(textField.getText());
                        if(!gameExist){
                            create(num);
                            gameExist = true;
                        }
                    }catch (NumberFormatException err){
                        if(!gameExist){
                            create(80);
                            gameExist = true;
                        }
                    }
                }
            });
            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    add();
                }
            });
            comboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = comboBox.getSelectedIndex() - 1;
                    if(index != -1){
                        cellKey = funchandle.clone(demo.getDemoByIndex(index));
                        partner = funchandle.clone(demo.getDemoPartnerByIndex(index));
                    }else{
                        cellKey = new int[80][80];
                        partner = new int[80][80];
                    }
                    gamePanel.setCellKey(cellKey);
                    gamePanel.repaint();
                }
            });
        }
    }
    private static void create(int num){
        initCell(num);
        int WIDTH = num * cellSideLength + 50;
        int HEIGHT = num * cellSideLength + 50;
        frameBase = new FrameBase("game",WIDTH,HEIGHT);
        gamePanel = new GamePanel(frameBase);
        frameBase.add(gamePanel);
        frameBase.setLocation(50,50);
        frameBase.setVisible(true);
        frameBase.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                gameExist = false;
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        gamePanel.setCellSideLength(cellSideLength);
        gamePanel.setCellKey(cellKey);
        gamePanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(gameActive == false){
                    int x = e.getX();
                    int y = e.getY();
                    int indexY = x/cellSideLength;
                    int indexX = y/cellSideLength;
                    if(cellKey[indexX][indexY] == 1){
                        cellKey[indexX][indexY] = 0;
                        changePartner(indexX,indexY,-1);
                    }else{
                        cellKey[indexX][indexY] = 1;
                        changePartner(indexX,indexY,1);
                    }
                    gamePanel.repaint();
                }
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
    }
    private static void initCell(int num){
        cellRows = num;
        cellKey = new int[cellRows][cellRows];
        partner = new int[cellRows][cellRows];
        for(int i=0;i<cellRows;i++){
            for(int j=0;j<cellRows;j++){
                cellKey[i][j] = 0;
                partner[i][j] = 0;
            }
        }
    }
    private static void reset(MyButton bt){
        cellKey = new int[cellRows][cellRows];
        partner = new int[cellRows][cellRows];
        gameActive = false;
        startFlag = true;
        paintTimer.cancel();
        gamePanel.setCellKey(cellKey);
        gamePanel.repaint();
        bt.setText("stop");
    }
    private static void start(){
        if(startFlag) {
            gameActive = true;
            new paintTimer();
            startFlag = false;
        }
    }
    private static void pause(MyButton bt){
        if(paintTimerState == 1 && gameActive == true){
            paintTimer.cancel();
            bt.setText("continue");
            paintTimerState = 0;
        }else{
            new paintTimer();
            bt.setText("stop");
            paintTimerState = 1;
        }
    }
    private static void add(){
        if(!gameActive){
            demo.addDemoConfig(cellKey,partner);
        }
    }
    public static class paintTimer{
        public paintTimer(){
            paintTimer = new Timer();
            paintTimer.schedule(new paintTask(),0, 200);
        }

        public class paintTask extends TimerTask {
            public void run(){
                changeCellKey();
                gamePanel.repaint();
            }
        }
    }
    private static void changeCellKey(){
        int[][] toBeChane = new int[cellRows * cellRows][cellRows];
        int index = 0;
        for(int i=0;i<cellRows;i++){
            for(int j=0;j<cellRows;j++){
                if(partner[i][j] > 3 || partner[i][j] < 2){
                    if(cellKey[i][j] == 1){
                        int[] tempArr = {i,j,-1};
                        toBeChane[index] = tempArr;
                        index++;
                    }
                    cellKey[i][j] = 0;
                }else if(partner[i][j] == 3){
                    if(cellKey[i][j] == 0){
                        int[] tempArr = {i,j,1};
                        toBeChane[index] = tempArr;
                        index++;
                    }
                    cellKey[i][j] = 1;
                }else{
                    continue;
                }
            }
        }
        for(int i = 0; i < toBeChane.length; i++){
            if(toBeChane[i].length == 0){
                break;
            }else{
                changePartner(toBeChane[i][0],toBeChane[i][1],toBeChane[i][2]);
            }
        }
    }
    private static void changePartner(int i, int j, int inc){
        try {
            partner[i][j-1] = (partner[i][j-1] + inc >= 0 )? partner[i][j-1] + inc : 0;
        }catch (ArrayIndexOutOfBoundsException e){}
        try {
            partner[i-1][j-1] = (partner[i-1][j-1] + inc >= 0 )? partner[i-1][j-1] + inc : 0;
        }catch (ArrayIndexOutOfBoundsException e){}
        try {
            partner[i-1][j] = (partner[i-1][j] + inc >= 0 )? partner[i-1][j] + inc : 0;
        }catch (ArrayIndexOutOfBoundsException e){}
        try {
            partner[i-1][j+1] = (partner[i-1][j+1] + inc >= 0 )? partner[i-1][j+1] + inc : 0;
        }catch (ArrayIndexOutOfBoundsException e){}
        try {
            partner[i][j+1] = (partner[i][j+1] + inc >= 0 )? partner[i][j+1] + inc : 0;
        }catch (ArrayIndexOutOfBoundsException e){}
        try {
            partner[i+1][j+1] = (partner[i+1][j+1] + inc >= 0 )? partner[i+1][j+1] + inc : 0;
        }catch (ArrayIndexOutOfBoundsException e){}
        try {
            partner[i+1][j] = (partner[i+1][j] + inc >= 0 )? partner[i+1][j] + inc : 0;
        }catch (ArrayIndexOutOfBoundsException e){}
        try {
            partner[i+1][j-1] = (partner[i+1][j-1] + inc >= 0 )? partner[i+1][j-1] + inc : 0;
        }catch (ArrayIndexOutOfBoundsException e){}
    }
}
