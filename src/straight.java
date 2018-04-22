/**
 * Created by azama on 4/17/2017.
 */
import java.util.Timer;
import java.util.TimerTask;

public class straight extends map {
    public static boolean throwPixel = false;
    public static int inWhichPartAmI(int x, int y){
        int[] myPosition = {x + 16, y + 25};
        int[] distances = {MainClass.WIDTH / 6, MainClass.HEIGHT / 4};
        int c = 0;
        for(int g = 0; g < 4; g++){
            for(int s = 0; s < 6; s++){
                c++;
                if ((s * distances[0] < myPosition[0] && myPosition[0] < (s + 1) * distances[0]) && (myPosition[1] > g * distances[1] &&  myPosition[1] < (g + 1) * distances[1])){
                    //System.out.println("the place is: " + c);
                    return c;
                }
            }
        }
        return c;
    }
    public static int[] getCoinsRealPosition(int x, int y){
        return new int[]{x + 2, y + 2};
    }
    public static void eatCoins(int[][] realPosition){
        int part = inWhichPartAmI(MainClass.x + 10, MainClass.y + 4) - 1;
        //System.out.println(part);
        //int[][] realPosition = limiter.getRealPosition(MainClass.x,MainClass.y);
        int[][] coinsInPart = limiter.coinsInParts[part];
        int p = 0;
        for (int f = 0; f < limiter.coinsInParts[part].length; f++){

            if (coinsInPart[f] != null){
                int[] CoinsRealPos = getCoinsRealPosition(coinsInPart[f][0], coinsInPart[f][1]);
                if (((CoinsRealPos[0] > realPosition[0][0]) && (CoinsRealPos[0] < realPosition[0][4])) &&  ((CoinsRealPos[1] > realPosition[1][0]) && (CoinsRealPos[1] < realPosition[1][4]))){
                    MainClass.coins[part][f].setVisible(false);
                    limiter.coinsInParts[part][f] = null;
                    MainClass.points++;
                    MainClass.updateScreen();
                    //System.out.println(MainClass.points);
                }
                p++;
            }
        }
    }
    public static void go() {

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (MainClass.isGoing) {
                        //int part = inWhichPartAmI(MainClass.x, MainClass.y) - 1;
/*
                        //System.out.println(limiter.getRealPosition(MainClass.x,MainClass.y)[0][0] + ", " + limiter.getRealPosition(MainClass.x,MainClass.y)[0][4] + " | " + limiter.getRealPosition(MainClass.x,MainClass.y)[1][0] + ", " + limiter.getRealPosition(MainClass.x,MainClass.y)[1][4]);

                        System.out.println("Part: "+part);
                        for (int f = 0; f < limiter.coinsInParts[part].length; f++){
                            if (MainClass.x > limiter.coinsInParts[part][f][0] && limiter.coinsInParts[part][f][0] < MainClass.x + 32 && limiter.coinsInParts[part][f][1] > MainClass.y + 10 && limiter.coinsInParts[part][f][1] < MainClass.x + 42) {
                                MainClass.coins[part][f].setVisible(false);
                            }
                        }
*/
                        /*if (!MainClass.listened.equals("") && limiter.checkWall(MainClass.listened, 0, true, 7)){
                            MainClass.side = MainClass.listened;
                            MainClass.listened = "";
                        }*/
                        //System.out.println(MainClass.listened);
                        if (throwPixel){
                            throwPixel = false;
                            if (!MainClass.listened.isEmpty()){
                                if (limiter.checkWall(MainClass.listened, 0, true, 7)){
                                    MainClass.side = MainClass.listened;
                                    MainClass.listened = "";
                                }
                            }
                        } else throwPixel = true;

                        if (limiter.checker(MainClass.side,0,true)) {
                            int[][] realPosition = limiter.getRealPosition(MainClass.x, MainClass.y);
                            //int[][] coinsInPart = limiter.coinsInParts[part];
                            switch (MainClass.side) {
                                case "r":
                                    eatCoins(realPosition);

                                    if (realPosition[0][4] >= 999) MainClass.x = -32;
                                    MainClass.x += 1;
                                    break;
                                case "l":

                                    eatCoins(realPosition);

                                    if (realPosition[0][0] <= 1) MainClass.x = 999;
                                    MainClass.x -= 1;
                                    break;
                                case "u":

                                    eatCoins(realPosition);

                                    if (realPosition[1][0] <= 1) MainClass.y = 635;
                                    MainClass.y -= 1;
                                    break;
                                case "d":

                                    eatCoins(realPosition);

                                    if (realPosition[1][4] >= 645) MainClass.y = -32;
                                    MainClass.y += 1;
                                    break;
                            }
                            MainClass.label.setBounds(MainClass.x, MainClass.y + MainClass.forInfoPanel, 33, 50);
                        } else {
                            //System.out.println("Stopped");
                            MainClass.isGoing = false;
                        }
                    }
                }
            }, 1000, 10);

    }

}

