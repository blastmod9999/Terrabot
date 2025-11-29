package terrabot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Entities;
import entities.air.Air;
import entities.animal.Animals;
import entities.plant.Plants;
import entities.soil.Soil;
import entities.water.Water;
import lombok.Getter;
import lombok.Setter;
import map.InitializeMap;
import map.MapBox;
import simulation.Commands;
import utils.MagicNumbers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public final class TerraBot {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final MoveDecider moveDecider = new MoveDecider();
    private String outputMessage = "ERROR";
    private int[] dx = {0, 1, 0, -1};
    private int[] dy = {1, 0, -1, 0};
    /**
     * -- SETTER --
     *  Javadoc for method setLearnMap.
     */
    @Setter
    @Getter
    private LinkedHashMap<String, String> learnMap = new LinkedHashMap<>();
    /**
     * -- GETTER --
     *  Javadoc for method getInventory.
     * -- SETTER --
     *  Javadoc for method setInventory.

     */
    @Setter
    @Getter
    private ArrayList<Entities> inventory = new ArrayList<Entities>();
    /**
     * -- GETTER --
     *  Javadoc for method getLearnFact.
     * -- SETTER --
     *  Javadoc for method setLearnFact.

     */
    @Setter
    @Getter
    private LearnFact learnFact = new LearnFact();
    /**
     * -- GETTER --
     *  Javadoc for method getBatteryCharge.
     * -- SETTER --
     *  Javadoc for method setBatteryCharge.

     */
    @Setter
    @Getter
    private int batteryCharge;
    /**
     * -- GETTER --
     *  Javadoc for method getX.
     * -- SETTER --
     *  Javadoc for method setX.

     */
    @Setter
    @Getter
    private int x = 0;
    /**
     * -- GETTER --
     *  Javadoc for method getY.
     * -- SETTER --
     *  Javadoc for method setY.

     */
    @Setter
    @Getter
    private int y = 0; // pozitia curenta
    /**
     * -- GETTER --
     *  Javadoc for method isCharging.
     * -- SETTER --
     *  Javadoc for method setCharging.

     */
    @Setter
    @Getter
    private boolean isCharging;
    /**
     * -- SETTER --
     *  Javadoc for method setChargeFinTimestamp.
     * -- GETTER --
     *  Javadoc for method getChargeFinTimestamp.

     */
    @Getter
    @Setter
    private int chargeFinTimestamp;

    public TerraBot(final int batteryCharge) {
        this.batteryCharge = batteryCharge;
        IO.println("Battery Charge: " + batteryCharge);
    }


    /**
     * Javadoc for method moveRobot.
     */
    public String moveRobot(final InitializeMap map) {
        final MapBox[][] mapBox = map.getEnvMap();
        int minimal = MagicNumbers.INFINITY;
        int newX = 0, newY = 0;
        int found = 0;
        for (int i = 0; i < dx.length; i++) {
            final int newI = x + dx[i];
            final int newJ = y + dy[i];
            if (newI >= 0 && newJ >= 0 && newI < map.getHeight() && newJ < map.getWidth()) {
                final int risk = moveDecider.calculateTotalRisk(mapBox[newI][newJ]);
                IO.println("Risk la (x,y) :" + newI + " , " + newJ + " = " + risk);
                //IO.println("COST = = = : "+risk);
                if (risk < minimal) {
                    minimal = risk;
                    newX = newI;
                    newY = newJ;
                    //IO.println("AM GASIT : " + new_x + " " + new_y);
                    found++;
                }
            }
        }
        if (found != 0) {
            IO.println("X = " + newX + ", Y = " + newY + " Cost : " + minimal);
            final int cost = minimal;
            if (batteryCharge >= cost) {
                this.x = newX;
                this.y = newY;
                this.batteryCharge -= cost;
                outputMessage =
                        "The robot has successfully moved to position (" + newX + ", "
                                + newY + ").";
                //IO.println("AM GASIT : " + newX + " " + newY);
            } else {
                outputMessage = "ERROR: Not enough battery left. Cannot perform action";
            }
        }
        return outputMessage;
    }

    /**
     * Javadoc for method scanObject.
     */
    public String scanObject(final InitializeMap map, final String color,
                             final String smell, final String sound) {
        final MapBox[][] mapBox = map.getEnvMap();
        String result = "ERROR";
        final Water water;
        final Animals animal;
        final Plants plant;
        if (sound.equals("none") && smell.equals("none")) {
            if (mapBox[this.x][this.y].getWater() != null) {
                result = "water";
                water = mapBox[this.x][this.y].getWater();
                water.setScanned(true);
                water.setX(this.x);
                water.setY(this.y);
                inventory.add(water);
            }
        } else if (sound.equals("none") && !smell.equals("none")) {
            if (mapBox[this.x][this.y].getPlant() != null) {
                result = "a plant";
                plant = mapBox[this.x][this.y].getPlant();
                plant.setScanned(true);
                plant.setX(this.x);
                plant.setY(this.y);
                inventory.add(plant);
            }
        } else if (!sound.equals("none")) {
            if (mapBox[this.x][this.y].getAnimal() != null) {
                animal = mapBox[this.x][this.y].getAnimal();
                result = "an animal";
                animal.setScanned(true);
                animal.setX(this.x);
                animal.setY(this.y);
                inventory.add(animal);
            }
        }

        return result;
    }

    /**
     * Javadoc for method PrintKnowledge.
     */
    public ArrayNode printKnowledge() {
        final ArrayNode out = MAPPER.createArrayNode();

        final LinkedHashMap<String, ArrayList<String>> result = new LinkedHashMap<>();
        final ArrayList<ObjectNode> objects = new ArrayList<>();
        //IO.println(learnMap.toString());

        for (final Map.Entry<String, String> entry : learnMap.entrySet()) {
            final String fact = entry.getKey();
            final String topic = entry.getValue();

            if (!result.containsKey(topic)) {
                result.put(topic, new ArrayList<>());
            }

            result.get(topic).add(fact);
        }

        for (final String topic : result.keySet()) {
            final ObjectNode obj = MAPPER.createObjectNode();
            obj.put("topic", topic);

            final ArrayNode factsArray = MAPPER.createArrayNode();
            for (final String fact : result.get(topic)) {
                factsArray.add(fact);
            }

            obj.set("facts", factsArray);
            objects.add(obj);
            //out.add(obj);
        }

        for (final ObjectNode ob : objects) {
            out.add(ob);
        }

        return out;
    }

    /**
     * Javadoc for method improvementType.
     */
    public String improvementType(final Commands command, final InitializeMap map) {
        final MapBox[][] mapBox = map.getEnvMap();

        final String result = "ERROR";
        final String type;
        final String name;
        Entities object = null;

        Entities itemInInventory = null;

        for (final Entities e : inventory) {
            if (e.getName().equals(command.getName())) {
                itemInInventory = e;
                break;
            }
        }

        //IO.println("TYPE : " + command.getImprovementType() + "  NAME : " + command.getName());

        if (itemInInventory == null) {
            return "ERROR 1";
        }
        String requiredFact = "";
        if ("plantVegetation".equals(command.getImprovementType())) {
            requiredFact = "Method to plant " + command.getName();
        } else if ("fertilizeSoil".equals(command.getImprovementType())) {
            requiredFact = "Method to fertilize with "
                    + command.getName(); // Verifică formatul exact!

        } else if ("increaseHumidity".equals(command.getImprovementType())) {
            requiredFact = "Method to increase humidity.";
        } else if ("increaseMoisture".equals(command.getImprovementType())) {
            requiredFact = "Method to increase moisture.";
        }

        //IO.println("required fact : " + requiredFact);
        //IO.println(learnMap.toString());

        if (!learnMap.containsKey(requiredFact)) {
            return "ERROR 2";
        }
        //IO.println("required fact : " + requiredFact);
        //IO.println(learnMap.toString());
        switch (command.getImprovementType()) {
            case "plantVegetation" -> {

                type = command.getType();
                name = command.getName();
                object = scanInventory(type, name);
                if (object != null) {
                    //mapBox[x][y].setPlant((Plants)object);

                    final Air air = mapBox[x][y].getAir();
                    air.setOxygenLevel(air.getOxygenLevel() + MagicNumbers.POINT_THREE);
                    //air.updateAirQualityScore(air.getOxygenLevel());
                    removeFromInventory(object);
                    //mapBox[object.getX()][object.getY()].setPlant(null);
                    return "The " + name + " was planted successfully.";
                }
            }
            case "fertilizeSoil" -> {
                type = command.getType();
                name = command.getName();

                object = scanInventory(type, name);
                if (object != null) {
                    //mapBox[x][y].setAnimal((Animals)object);

                    final Soil soil = mapBox[x][y].getSoil();
                    soil.setOrganicMatter(soil.getOrganicMatter() + MagicNumbers.POINT_THREE);
                    //soil.updateAirQualityScore(soil.getOxygenLevel());
                    removeFromInventory(object);
                    return "The soil was successfully fertilized using " + name;
                }
            }
            case "increaseHumidity" -> {

                type = command.getType();
                name = command.getName();
                object = scanInventory(type, name);
                if (object != null) {
                    //mapBox[x][y].setPlant((Plants)object);

                    final Air air = mapBox[x][y].getAir();
                    air.setHumidity(air.getHumidity() + MagicNumbers.POINT_TWO);
                    air.updateAirQualityScore(air.getOxygenLevel());
                    removeFromInventory(object);
                    return "The humidity was successfully increased using " + name;
                }
            }
            case "increaseMoisture" -> {
                type = command.getType();
                name = command.getName();
                object = scanInventory(type, name);
                if (object != null) {
                    //mapBox[x][y].setPlant((Plants)object);

                    final Soil soil = mapBox[x][y].getSoil();
                    soil.setWaterRetention(soil.getWaterRetention() + MagicNumbers.POINT_TWO);
                    //soil.updateAirQualityScore(soil.getOxygenLevel());
                    removeFromInventory(object);
                    return "The moisture was successfully increased using " + name;
                }
            }
            default -> {
                break;
            }
        }

        return result;
    }

    void removeFromInventory(final Entities ob) {
        inventory.remove(ob);
    }

    Entities scanInventory(final String type, final String name) {
        Entities object = null;

        for (final Entities entity : inventory) {
            //IO.println("TYPE : " + entity.getType() + " NAME : " + entity.getName());
            if (entity.getType().equals(type) && entity.getName().equals(name)) {
                object = entity;
            }
        }
        return object;
    }

}


