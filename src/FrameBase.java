import javax.swing.*;

/**
 * Created by zzhfeng on 2017/7/7.
 */
class FrameBase extends JFrame{
    FrameBase(String title,int width, int height){
        //设置窗口title
        setTitle(title);
        //设置窗口大小
        setSize(width, height);
        // 设置窗口关闭按钮的默认操作(点击关闭时退出进程)
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // 把窗口位置设置到屏幕的中心
//        setLocationRelativeTo(null);
    }
}
