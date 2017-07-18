import java.util.Arrays;

/**
 * Created by zzhfeng on 2017/7/12.
 */
public class Demo {
    private static int[][][] demo = new int[20][80][80];
    private static int[][][] demoPartner = new int[20][80][80];
    private static Config cfg;
    private static final FuncHandle funcHandle = new FuncHandle();

    public Demo(String fpath) {
        initDemo(fpath);
    }

    private void initDemo(String fpath) {
        getDemoConfig(fpath);
    }
    public static int[][][] getDemo(){
        return demo;
    }
    public static int[][] getDemoByIndex(int index){
        return demo[index];
    }
    public static int[][] getDemoPartnerByIndex(int index){
        return demoPartner[index];
    }

    public void addDemoConfig(int[][] newDemo, int[][] newDemoPartner) {
        String demoStr = funcHandle.toString(newDemo);
        String demoPartnerStr = funcHandle.toString(newDemoPartner);
        String index = cfg.getProperty("index");
        if(index == null){
            index = "0";
        }
        cfg.setProp("demo" + index, demoStr);
        cfg.setProp("demoPartner" + index, demoPartnerStr);
        cfg.setProp("index",Integer.toString(Integer.parseInt(index) + 1));
        demo[Integer.parseInt(index)] = newDemo;
        demoPartner[Integer.parseInt(index)] = newDemoPartner;
        cfg.store();
    }

    private void getDemoConfig(String fpath) {
        cfg = new Config(fpath);
        int i = 0;
        while (true) {
            String tmpStr = cfg.getProperty("demo" + Integer.toString(i));
            String partnerStr = cfg.getProperty("demoPartner" + Integer.toString(i));
            if (tmpStr == null) {
                break;
            } else {
                int len = cfg.getPropertyLen("demo" + Integer.toString(i));
                demo[i] = funcHandle.toArray(tmpStr, len);
                demoPartner[i] = funcHandle.toArray(partnerStr, len);
            }
            i++;
        }
    }
}
