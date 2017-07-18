import java.util.Arrays;

/**
 * Created by zzhfeng on 2017/7/12.
 */
public class FuncHandle {
    public FuncHandle(){

    }
    public static int[][] toArray(String str, int length) {
        int len = str.length();
        int[][] res = new int[length][length];
        String newStr = str.substring(1, len - 1);
        String[] strs = newStr.split(" , ");
        for (int i = 0; i < strs.length; i++) {
            String tempStr = strs[i].substring(1, strs[i].length() - 1);
            String[] tmpStrs = tempStr.split(",");
            for (int j = 0; j < tmpStrs.length; j++) {
                res[i][j] = Integer.parseInt(tmpStrs[j].trim());
            }
        }
        return res;
    }
    public static String toString(int[][] arr){
        String resStr = "[";
        for (int i = 0; i < arr.length; i++) {
            String tmpStr = Arrays.toString(arr[i]);
            resStr = resStr + tmpStr;
            if (i == arr.length - 1) {
                resStr = resStr + "]";
            } else {
                resStr = resStr + " , ";
            }
        }
        return resStr;
    }

    public static int[][] clone(int[][] arr){
        String tmpStr = toString(arr);
        int[][] tmpArr = toArray(tmpStr,80);
        return  tmpArr;
    }

}
