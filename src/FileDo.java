import java.io.*;
import java.util.Arrays;

/**
 * Created by azama on 5/7/2017.
 */
public class FileDo {

    public static void writeFile(String nickname, int scores){
        try {
            BufferedWriter fileW = new BufferedWriter(new FileWriter("db/DataBase",true));
            fileW.newLine();
            fileW.write(nickname + "~" + scores);
            fileW.close();
        } catch (IOException e){
            System.out.println(e);
        }
    }
    public static String[] readFile(String directory){
        String line;
        String[] arr = new String[0];
        try {
            int c = 0;
            BufferedReader fileR = new BufferedReader(new FileReader(directory));
            while ((fileR.readLine()) != null){
                c++;
            }
            arr = new String[c];
            fileR = new BufferedReader(new FileReader(directory));
            c = 0;
            while ((line = fileR.readLine()) != null){
                arr[c] = line;
                c++;
            }
            fileR.close();
        } catch (IOException e){
            System.out.println(e);
        }
        return arr;
    }
    public static String[][] readFileRecords(String directory){
        String line;
        String[][] arr = new String[0][2];
        try {
            int c = 0;
            BufferedReader fileR = new BufferedReader(new FileReader(directory));
            while ((fileR.readLine()) != null){
                c++;
            }
            arr = new String[c][2];
            fileR = new BufferedReader(new FileReader(directory));
            c = 0;
            while ((line = fileR.readLine()) != null){
                arr[c][0] = line.split("~")[0];
                arr[c][1] = line.split("~")[1];
                c++;
            }
            fileR.close();
        } catch (IOException e){
            System.out.println(e);
        }
        return arr;
    }
    public static int[] readFileRecordsON(String directory){
        String line;
        int[] arr = new int[0];
        try {
            int c = 0;
            BufferedReader fileR = new BufferedReader(new FileReader(directory));
            while ((fileR.readLine()) != null){
                c++;
            }
            arr = new int[c];
            fileR = new BufferedReader(new FileReader(directory));
            c = 0;
            while ((line = fileR.readLine()) != null){
                if (!line.isEmpty()) {
                    arr[c] = Integer.parseInt(line.split("~")[1]);
                    c++;
                }
            }
            fileR.close();
        } catch (IOException e){
            System.out.println(e);
        }
        return arr;
    }
    public static int[] sorting(int[] arr){
        Arrays.sort(arr);
        return arr;
    }
    public static String[][] getLastTenRecords(){
        int[] array_ = readFileRecordsON("db/DataBase");
        array_ = sorting(array_);
        String[][] arrayFileRecords = readFileRecords("db/DataBase");
        String[][] TenRecords = new String[10][2];
        int c = 0;
        for (int g = array_.length - 1; g >= array_.length - 10; g--){
            for (String[] sd: arrayFileRecords) {
                if (g >= 0 ) {
                    if (sd[1].equals(Integer.toString(array_[g]))) {
                        TenRecords[c] = sd;
                        c++;
                        break;
                    }
                }
            }
        }
        return TenRecords;
    }

    public static void main(String[] args) {
        for (String[] d: getLastTenRecords()){
            System.out.println(d[0] + ", " + d[1]);
        }
    }
}
