package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.air.Air;
import entities.animal.Animals;
import entities.plant.Plants;
import entities.soil.Soil;
import entities.water.Water;
import fileio.InputLoader;
import lombok.Getter;
import lombok.Setter;
import map.InitializeMap;
import map.MapBox;
import simulation.Commands;
import simulation.SimulationParams;
import terrabot.LearnFact;
import terrabot.TerraBot;
import utils.MagicNumbers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class InputHolder {
    private ArrayList<SimulationParams> simulationParams;
    private ArrayList<Commands> commands;

    /**
     * Javadoc for method getSimulationParams.
     */
    public ArrayList<SimulationParams> getSimulationParams() {
        return simulationParams;
    }

    /**
     * Javadoc for method setSimulationParams.
     */
    public void setSimulationParams(final ArrayList<SimulationParams> simulationParams) {
        this.simulationParams = simulationParams;
    }

    /**
     * Javadoc for method getCommands.
     */
    public ArrayList<Commands> getCommands() {
        return commands;
    }

    /**
     * Javadoc for method setCommands.
     */
    public void setCommands(final ArrayList<Commands> commands) {
        this.commands = commands;
    }
}


class WorldManager {
    private final SimulationParams simulationParams;
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final InitializeMap map;
    //Instantiem robotul
    private final TerraBot terraBot;
    /**
     * -- GETTER --
     *  Javadoc for method isSimStarted.
     * -- SETTER --
     *  Javadoc for method setSimStarted.

     */
    @Setter
    @Getter
    private boolean isSimStarted = false;
    private int timeStopWeather;
    private LearnFact learnFact;
    private int lastProcessedTime = 0;

    WorldManager(final ArrayList<SimulationParams> simulationParams, final int iteration) {

        final SimulationParams simulationParam = simulationParams.get(iteration);

        final InitializeMap initializeMap = new InitializeMap(simulationParam.getTerritoryDim());
        initializeMap.populateMap(simulationParam.getTerritorySectionParams());

        this.map = initializeMap;
        this.simulationParams = simulationParam;
        this.terraBot = new TerraBot(simulationParam.getEnergyPoints());
    }

    void calculateMap(final InitializeMap initializeMap) {
        final MapBox[][] mapBox = initializeMap.getEnvMap();
        for (int j = 0; j < initializeMap.getWidth(); j++) {
            for (int i = 0; i < initializeMap.getHeight(); i++) {
                if (mapBox[i][j].getWater() != null) {
                    final Water water = mapBox[i][j].getWater();
                    water.setWaterQuality();
                }

                if (mapBox[i][j].getAir() != null) {
                    final Air air = mapBox[i][j].getAir();
                    air.setAirQualityScore();
                }

                if (mapBox[i][j].getSoil() != null) {
                    final Soil soil = mapBox[i][j].getSoil();
                    soil.setSoilQualityScore();
                }
            }
        }

    }

    /**
     * Javadoc for method commandManager.
     */
    public ObjectNode commandManager(final Commands command) {

        //new WorldManager(simulationParams,1);

        final ObjectNode outputNode = OBJECT_MAPPER.createObjectNode();
        outputNode.put("command", command.getCommand());

        //prindem din urma timpestamp pt plante etc.. acestea dau oxigen si cand
        //se incarca robotul sau sare de la un timestamp la altul;
        final int currentTimestamp = command.getTimestamp();
        final int timeToCatch = currentTimestamp - lastProcessedTime;

        if (isSimStarted && timeToCatch > 1) {
            for (int i = 1; i < timeToCatch; i++) {
                //updateWeather();
                updateScannedBox();
            }
        }
        lastProcessedTime = currentTimestamp;

        //verificam mereu vremea sa fie tot la timpul potrivit +2 timestamp
        if (isSimStarted() && timeStopWeather <= command.getTimestamp()) {
            updateWeather();
        }

        if (isSimStarted()) {
            updateScannedBox();
        }

        if (terraBot.isCharging()) {
            if (command.getTimestamp() >= terraBot.getChargeFinTimestamp()) {
                terraBot.setCharging(false);
            } else {
                outputNode.put("message", "ERROR: Robot still charging. Cannot perform action");
                outputNode.put("timestamp", command.getTimestamp());
            }

        }

        if (!terraBot.isCharging()) {
            switch (command.getCommand()) {
                case "startSimulation":
                    if (isSimStarted) {
                        outputNode.put("message",
                                       "ERROR: Simulation already started. Cannot perform action");
                    } else {
                        isSimStarted = true;
                        calculateMap(map);
                        outputNode.put("message", "Simulation has started.");
                    }
                    outputNode.put("timestamp", command.getTimestamp());
                    break;

                case "printEnvConditions":
                    if (!isSimStarted) {
                        outputNode.put("message",
                                       "ERROR: Simulation not started. Cannot perform action");
                    } else {
                        outputNode.set("output",
                                       printEnvConditions(map, terraBot.getX(), terraBot.getY()));
                    }

                    outputNode.put("timestamp", command.getTimestamp());
                    break;

                case "printMap":
                    if (!isSimStarted) {
                        outputNode.put("message",
                                       "ERROR: Simulation not started. Cannot perform action");
                    } else {
                        outputNode.set("output", printMap(map));
                    }

                    outputNode.put("timestamp", command.getTimestamp());
                    break;

                case "endSimulation":
                    if (!isSimStarted) {
                        outputNode.put("message",
                                       "ERROR: Simulation not started. Cannot perform action");
                    } else {
                        outputNode.put("message", "Simulation has ended.");
                        isSimStarted = false;
                    }
                    outputNode.put("timestamp", command.getTimestamp());
                    break;

                case "changeWeatherConditions":
                    if (!isSimStarted) {
                        outputNode.put("message",
                                       "ERROR: Simulation not started. Cannot perform action");
                        outputNode.put("timestamp", command.getTimestamp());
                    } else {
                        IO.println(command.getType());
                        if (changeWeather(map, command)) {
                            outputNode.put("message", "The weather has changed.");
                        } else {
                            outputNode.put("message",
                                           "ERROR: The weather change does not affect "
                                                   + "the environment. Cannot perform action");
                        }
                        outputNode.put("timestamp", command.getTimestamp());
                        timeStopWeather += command.getTimestamp() + MagicNumbers.THREE;

                    }
                    break;

                case "moveRobot":
                    if (!isSimStarted) {
                        outputNode.put("message",
                                       "ERROR: Simulation not started. Cannot perform action");
                    } else {
                        outputNode.put("message", terraBot.moveRobot(map));
                    }
                    outputNode.put("timestamp", command.getTimestamp());

                    break;

                case "rechargeBattery":
                    if (!isSimStarted) {
                        outputNode.put("message",
                                       "ERROR: Simulation not started. Cannot perform action");
                        outputNode.put("timestamp", command.getTimestamp());
                    } else {
                        terraBot.setChargeFinTimestamp(
                                command.getTimestamp() + command.getTimeToCharge());
                        terraBot.setCharging(true);
                        terraBot.setBatteryCharge(
                                terraBot.getBatteryCharge() + command.getTimeToCharge());
                        lastProcessedTime = command.getTimestamp();
                        outputNode.put("message", "Robot battery is charging.");
                        outputNode.put("timestamp", command.getTimestamp());
                    }
                    break;
                case "scanObject":
                    if (!isSimStarted) {
                        outputNode.put("message",
                                       "ERROR: Simulation not started. Cannot perform action");
                        outputNode.put("timestamp", command.getTimestamp());
                    } else {
                        if (terraBot.getBatteryCharge() - MagicNumbers.SCAN_COST <= 0) {
                            outputNode.put("message", "ERROR: Not enough energy to perform action");
                            outputNode.put("timestamp", command.getTimestamp());
                            break;
                        }
                        final String result = terraBot.scanObject(map, command.getColor(),
                                                                  command.getSmell(),
                                                                  command.getSound());
                        if (result.equals("ERROR")) {
                            outputNode.put("message",
                                           "ERROR: Object not found. Cannot perform action");
                        } else {
                            outputNode.put("message", "The scanned object is " + result + ".");
                            terraBot.setBatteryCharge(
                                    terraBot.getBatteryCharge() - MagicNumbers.SCAN_COST);
                        }
                        outputNode.put("timestamp", command.getTimestamp());

                    }
                    break;

                case "learnFact":
                    if (!isSimStarted) {
                        outputNode.put("message",
                                       "ERROR: Simulation not started. Cannot perform action");
                        outputNode.put("timestamp", command.getTimestamp());
                    } else {
                        if (terraBot.getBatteryCharge() - 2 <= 0) {
                            outputNode.put("message",
                                           "ERROR: Not enough battery left. Cannot perform action");
                            outputNode.put("timestamp", command.getTimestamp());
                            break;
                        }
                        final String result = terraBot.getLearnFact()
                                .addFactToDatabase(command.getSubject(), command.getComponents(),
                                                   terraBot.getLearnMap(), terraBot.getInventory());
                        if (result.equals("ERROR")) {
                            outputNode.put("message",
                                           "ERROR: Subject not yet saved. Cannot perform action");
                            outputNode.put("timestamp", command.getTimestamp());
                        } else {
                            outputNode.put("message",
                                           "The fact has been successfully saved in the database.");
                            terraBot.setBatteryCharge(terraBot.getBatteryCharge() - 2);
                        }
                        outputNode.put("timestamp", command.getTimestamp());
                    }
                    break;
                case "printKnowledgeBase":
                    if (!isSimStarted) {
                        outputNode.put("message",
                                       "ERROR: Simulation not started. Cannot perform action");
                    } else {
                        outputNode.set("output", terraBot.printKnowledge());
                        outputNode.put("timestamp", command.getTimestamp());
                    }

                    break;

                case "improveEnvironment":
                    if (!isSimStarted) {
                        outputNode.put("message",
                                       "ERROR: Simulation not started. Cannot perform action");
                        outputNode.put("timestamp", command.getTimestamp());
                    } else {
                        if (terraBot.getBatteryCharge() - MagicNumbers.IMPROVE_COST <= 0) {
                            outputNode.put("message",
                                           "ERROR: Not enough battery left. Cannot perform action");
                            outputNode.put("timestamp", command.getTimestamp());
                            break;
                        }
                        final String result = terraBot.improvementType(command, map);
                        if (result.equals("ERROR 1")) {
                            outputNode.put("message",
                                           "ERROR: Subject not yet saved. Cannot perform action");
                        } else if (result.equals("ERROR 2")) {
                            outputNode.put("message",
                                           "ERROR: Fact not yet saved. Cannot perform action");
                        } else {
                            outputNode.put("message", result);
                            terraBot.setBatteryCharge(
                                    terraBot.getBatteryCharge() - MagicNumbers.IMPROVE_COST);

                        }
                        outputNode.put("timestamp", command.getTimestamp());
                    }
                    break;

                case "getEnergyStatus":
                    if (!isSimStarted) {
                        outputNode.put("message",
                                       "ERROR: Simulation not started. Cannot perform action");
                        outputNode.put("timestamp", command.getTimestamp());
                    } else {
                        outputNode.put("message", "TerraBot has " + terraBot.getBatteryCharge()
                                + " energy points left.");
                        outputNode.put("timestamp", command.getTimestamp());
                    }
                    break;

                default:
                    break;
            }
        }
        return outputNode;
    }

    /// Aceasta functie face update urile obiectelor scanate / interactiunile lor cu mediul
    void updateScannedBox() {
        final MapBox[][] mapBox = map.getEnvMap();
        for (int j = 0; j < map.getWidth(); j++) {
            for (int i = 0; i < map.getHeight(); i++) {
                if (mapBox[i][j].getPlant() != null) {
                    final Plants plant = mapBox[i][j].getPlant();
                    if (plant.isScanned()) {
                        plant.updateBox(mapBox[i][j].getAir());
                        if (plant.getGrowth() > MagicNumbers.GROW_LIMIT) {
                            mapBox[i][j].setPlant(null);
                        }
                    }
                }
                if (mapBox[i][j].getWater() != null) {
                    final Water water = mapBox[i][j].getWater();
                    if (water.isScanned()) {
                        water.updateBox(mapBox[i][j]);
                    }
                }

                if (mapBox[i][j].getAnimal() != null) {
                    final Animals animal = mapBox[i][j].getAnimal();

                    if (animal.isScanned()) {
                        animal.animalMove(map, i, j);
                    }
                }
            }
        }

    }

    boolean changeWeather(final InitializeMap initializeMap, final Commands command) {
        boolean result = false;
        final MapBox[][] mapBox = initializeMap.getEnvMap();

        for (int j = 0; j < initializeMap.getWidth(); j++) {
            for (int i = 0; i < initializeMap.getHeight(); i++) {
                if (mapBox[i][j].getAir() != null) {
                    final Air air = mapBox[i][j].getAir();
                    if (air.applyWeatherConditions(command)) {
                        result = true;
                    }
                }
            }
        }


        return result;
    }
/// Functia de update a vremii / interactiunea sa cu mediul
    private void updateWeather() {
        final MapBox[][] mapBox = map.getEnvMap();
        for (int j = 0; j < map.getWidth(); j++) {
            for (int i = 0; i < map.getHeight(); i++) {
                if (mapBox[i][j].getAir() != null) {
                    mapBox[i][j].getAir().resetWeather();
                }
            }
        }
    }

    ArrayNode printMap(final InitializeMap initializeMap) {
        final ObjectNode outputNode = OBJECT_MAPPER.createObjectNode();
        final ArrayNode outputArray = OBJECT_MAPPER.createArrayNode();
        final MapBox[][] mapBox = initializeMap.getEnvMap();

//        IO.println("/////////START PRINT MAP/////////");
        for (int j = 0; j < initializeMap.getWidth(); j++) {
            for (int i = 0; i < initializeMap.getHeight(); i++) {

                int objNum = 0;
                if (mapBox[i][j].getWater() != null) {
                    objNum++;
//                    IO.println("water=" + mapBox[i][j].getWater().getName());
                }
                if (mapBox[i][j].getAnimal() != null) {
                    objNum++;
//                    IO.println("animal=" + mapBox[i][j].getAnimal().getName());
                }
                if (mapBox[i][j].getPlant() != null) {
                    objNum++;
//                    IO.println("plant=" + mapBox[i][j].getPlant().getName());
                }


                final ObjectNode cellData = OBJECT_MAPPER.createObjectNode();
                final ArrayNode coords = OBJECT_MAPPER.createArrayNode();
                coords.add(i);
                coords.add(j);
                cellData.set("section", coords);
                cellData.put("totalNrOfObjects", objNum);
                cellData.put("soilQuality", mapBox[i][j].getSoil().getSoilQuality());
                cellData.put("airQuality", mapBox[i][j].getAir().getAirQuality());

                outputArray.add(cellData);
            }
        }
//        IO.println("/////////END PRINT MAP/////////");
        return outputArray;
    }

    ObjectNode printEnvConditions(final InitializeMap initializeMap, final int x, final int y) {
        final ObjectNode outputNode = OBJECT_MAPPER.createObjectNode();
        final MapBox[][] mapBox = initializeMap.getEnvMap();

        final ObjectNode plantData = OBJECT_MAPPER.createObjectNode();

        if (mapBox[x][y].getSoil() != null) {
            final Soil soil = mapBox[x][y].getSoil();
            ///folosim valuetotree pt a nu sta sa le punem manual , referinta in README
            final ObjectNode soilData = OBJECT_MAPPER.valueToTree(soil);
            soilData.remove("sections");
            soilData.remove("soilQuality");
            soilData.remove("soilQualityScore");
            soilData.put("soilQuality", soil.getSoilQualityScore());
            outputNode.set("soil", soilData);
        }

        if (mapBox[x][y].getPlant() != null) {
            final Plants plants = mapBox[x][y].getPlant();
            plantData.put("type", plants.getType());
            plantData.put("name", plants.getName());
            plantData.put("mass", plants.getMass());
            outputNode.set("plants", plantData);
        }

        if (mapBox[x][y].getAnimal() != null) {
            final Animals animal = mapBox[x][y].getAnimal();
            final ObjectNode animalData = OBJECT_MAPPER.valueToTree(animal);
            animalData.remove("sections");
            outputNode.set("animals", animalData);
        }


        if (mapBox[x][y].getWater() != null) {
            final Water water = mapBox[x][y].getWater();
            final ObjectNode waterData = OBJECT_MAPPER.createObjectNode();
            waterData.put("type", water.getType());
            waterData.put("name", water.getName());
            waterData.put("mass", water.getMass());
            outputNode.set("water", waterData);
        }

        if (mapBox[x][y].getAir() != null) {
            final Air air = mapBox[x][y].getAir();
            final ObjectNode airData = OBJECT_MAPPER.valueToTree(air);
            airData.remove("airQuality");
            airData.remove("airQualityScore");
            airData.remove("dustParticles");
            airData.put("airQuality", air.getAirQualityScore());
            airData.remove("sections");
            outputNode.set("air", airData);
        }

        return outputNode;
    }

}


/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final ObjectWriter WRITER = MAPPER.writer().withDefaultPrettyPrinter();
    public static final ObjectNode OBJECT_MAPPER = MAPPER.createObjectNode();

    private Main() {
    }

    /**
     * @param inputPath  input file path
     * @param outputPath output file path
     * @throws IOException when files cannot be loaded.
     */
    public static void action(final String inputPath,
                              final String outputPath) throws IOException {

        final InputLoader inputLoader = new InputLoader(inputPath);
        final ArrayNode output = MAPPER.createArrayNode();

        InputHolder inputHolder = MAPPER.readValue(new File(inputPath), InputHolder.class);

        final ArrayList<SimulationParams> simulationParams = inputHolder.getSimulationParams();
        //SimulationParams sim = simulationParams.getLast();

        final ArrayList<Commands> commands = inputHolder.getCommands();

        WorldManager worldManager = new WorldManager(simulationParams, 0);

        int iteration = 0;
        for (final Commands command : commands) {
            //IO.println(command.getCommand() + " timestamp : " + command.getTimestamp());
            final ObjectNode out = worldManager.commandManager(command);

            if (out != null) {
                output.add(out);
            }

            if (command.getCommand().equals("endSimulation") && out.has("message") && out.get(
                    "message").asText().equals("Simulation has ended.")) {

                iteration++;
                if (iteration < simulationParams.size()) {
                    worldManager = new WorldManager(simulationParams, iteration);
                }
            }
        }


        final File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs();
        WRITER.writeValue(outputFile, output);
    }
}
