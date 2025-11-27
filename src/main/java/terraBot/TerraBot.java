package terraBot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import map.InitializeMap;
import map.MapBox;

public class TerraBot {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    String outputMessage = "ERROR";
    private int batteryCharge;
    private int x=0,y=0; // pozitia curenta
    private boolean isCharging;
    private int chargeFinTimestamp;
    private MoveDecider moveDecider = new MoveDecider();
    //int[] dx = {-1, 0, 1, 0};
    //int[] dy = {0, 1, 0, -1};

    int[] dx = {0,1,0,-1};
    int[] dy = {1,0,-1,0};

    public TerraBot(int batteryCharge) {
        this.batteryCharge = batteryCharge;
        IO.println("Battery Charge: " + batteryCharge);
    }

    public int getChargeFinTimestamp() {
        return chargeFinTimestamp;
    }

    public void setChargeFinTimestamp(int chargeFinTimestamp) {
        this.chargeFinTimestamp = chargeFinTimestamp;
    }

    public boolean isCharging() {
        return isCharging;
    }

    public void setCharging(boolean charging) {
        isCharging = charging;
    }

    public int getBatteryCharge() {
        return batteryCharge;
    }

    public void setBatteryCharge(int batteryCharge) {
        this.batteryCharge = batteryCharge;
    }

    public String moveRobot(InitializeMap map) {
        MapBox[][] mapBox = map.getEnvMap();
        int minimal = 99999999;
        int new_x=0, new_y=0;
        int found = 0;
        for (int i = 0; i < dx.length; i++) {
            int new_i = x + dx[i];
            int new_j = y + dy[i];
            if (new_i >= 0 && new_j >= 0 && new_i < map.getHeight() && new_j < map.getWidth()) {
                int risk = moveDecider.CalculateTotalRisk(mapBox[new_i][new_j]);
                IO.println("Risk la (x,y) :" + new_i + " , "+new_j+" = " + risk);
                //IO.println("COST = = = : "+risk);
                if(risk < minimal){
                    minimal = risk;
                    new_x = new_i;
                    new_y = new_j;
                    //IO.println("AM GASIT : " + new_x + " " + new_y);
                    found++;
                }
            }
        }
        if(found!=0){
            IO.println("X = "+new_x +", Y = "+new_y + " Cost : " + minimal);
            int cost = minimal;
            if(batteryCharge >= cost){
                this.x = new_x;
                this.y = new_y;
                this.batteryCharge -= cost;
                outputMessage = "The robot has successfully moved to position ("+new_x+", "+new_y+").";

                IO.println("AM GASIT : " + new_x + " " + new_y);

            }
            else {
                outputMessage = "ERROR: Not enough battery left. Cannot perform action";
            }
        }
        return outputMessage;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}


