package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.air.*;
import entities.animal.Animals;
import entities.plant.Plants;

import entities.soil.Soil;
import entities.water.Water;
import fileio.CommandInput;
import fileio.InputLoader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import map.InitializeMap;
import map.MapBox;
import map.TerritorySectionParams;
import simulation.SimulationParams;
import terraBot.TerraBot;


class Commands {
    private String command;
    private int timestamp;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}

class InputHolder {
    ArrayList<SimulationParams> simulationParams;
    ArrayList<Commands> commands;

    public ArrayList<SimulationParams> getSimulationParams() {
        return simulationParams;
    }

    public void setSimulationParams(ArrayList<SimulationParams> simulationParams) {
        this.simulationParams = simulationParams;
    }

    public ArrayList<Commands> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<Commands> commands) {
        this.commands = commands;
    }
}





class WorldManager {
    private SimulationParams simulationParams;
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private boolean isSimStarted = false;
    private InitializeMap map;
    private int timestamp;
    //Instantiem robotul
    private TerraBot terraBot; ;
    //public WorldManager(InitializeMap map, Robot robot) {}



    void calculateMap(InitializeMap map) {
        MapBox[][] mapBox = map.getEnvMap();
        for(int j=0; j < map.getWidth(); j++) {
            for(int i=0; i < map.getHeight(); i++) {
                if(mapBox[i][j].getWater() != null) {
                    Water water = mapBox[i][j].getWater();
                    water.setWater_quality();
                }

                if(mapBox[i][j].getAir() != null) {
                    Air air = mapBox[i][j].getAir();
                    air.setAirQualityScore();
                }

                if(mapBox[i][j].getSoil() != null) {
                    Soil soil = mapBox[i][j].getSoil();
                    soil.setSoilQualityScore();
                }
            }
        }

    }


    public WorldManager(InitializeMap map, SimulationParams simulationParams) {
        this.map = map;
        this.simulationParams = simulationParams;
        this.terraBot = new TerraBot(simulationParams.getEnergyPoints());
    }

    public ObjectNode commandManager(Commands command) {
        ObjectNode outputNode = OBJECT_MAPPER.createObjectNode();
        outputNode.put("command", command.getCommand());

        switch (command.getCommand()) {
            case "startSimulation":
                if (isSimStarted) {
                    outputNode.put("message", "ERROR: Simulation already started. Cannot perform action");
                } else {
                    isSimStarted = true;
                    calculateMap(map);
                    outputNode.put("message", "Simulation has started.");
                }
                outputNode.put("timestamp", command.getTimestamp());
                break;

            case "printEnvConditions":
                if (!isSimStarted) {
                    outputNode.put("message", "ERROR: Simulation not started. Cannot perform action");
                } else {
                    //curent position of robot ?

                    outputNode.set("output", PrintEnvConditions(map, terraBot.getX(), terraBot.getY()));
                }

                outputNode.put("timestamp", command.getTimestamp());
                break;

            case "printMap":
                if (!isSimStarted) {
                    outputNode.put("message", "ERROR: Simulation not started. Cannot perform action");
                } else {
                    outputNode.set("output",printMap(map));
                }

                outputNode.put("timestamp", command.getTimestamp());
                break;

            case "endSimulation":
                if (!isSimStarted) {
                    outputNode.put("message", "ERROR: Simulation not started. Cannot perform action");
                } else {
                    outputNode.put("message", "Simulation has ended.");
                    isSimStarted = false;
                }
                outputNode.put("timestamp", command.getTimestamp());
                break;

            case "changeWeatherConditions":
                break;

            case "moveRobot":
                if(!isSimStarted) {
                    outputNode.put("message", "ERROR: Simulation not started. Cannot perform action");
                } else
                    outputNode.put("message", terraBot.moveRobot(map));
                outputNode.put("timestamp", command.getTimestamp());

                break;

            case "rechargeBattery":
                break;

            case "scanObject":
                break;

            case "learnFact":
                break;

            case "getEnergyStatus":
                break;

            default:
                break;
        }

        return outputNode;
    }

    ArrayNode printMap(InitializeMap map) {
        ObjectNode outputNode = OBJECT_MAPPER.createObjectNode();
        ArrayNode outputArray = OBJECT_MAPPER.createArrayNode();
        MapBox[][] mapBox = map.getEnvMap();



        for(int j=0; j < map.getWidth(); j++) {
            for(int i=0; i < map.getHeight(); i++) {

                int obj_num = 0;
                if(mapBox[i][j].getWater() != null) {
                    obj_num++;
                }
                if(mapBox[i][j].getAnimal() != null) {
                    obj_num++;
                }
                if(mapBox[i][j].getPlant() != null) {
                    obj_num++;
                }

                ObjectNode cellData = OBJECT_MAPPER.createObjectNode();
                ArrayNode coords = OBJECT_MAPPER.createArrayNode();
                coords.add(i);
                coords.add(j);
                cellData.set("section", coords);
                //cellData.put("section", "[ "+i+", "+j+" ]");
                cellData.put("totalNrOfObjects", obj_num);
                //outputNode.put("output", cellData);
                cellData.put("soilQuality",mapBox[i][j].getSoil().getSoilQuality());
                cellData.put("airQuality",mapBox[i][j].getAir().getAirQuality());

                outputArray.add(cellData);
            }
        }

        //outputNode.add("output", outputArray);
        return  outputArray;
    }

    ObjectNode PrintEnvConditions(InitializeMap  map, int x, int y) {
        ObjectNode outputNode = OBJECT_MAPPER.createObjectNode();
        MapBox[][] mapBox = map.getEnvMap();

        ObjectNode plantData = OBJECT_MAPPER.createObjectNode();

        if(mapBox[x][y].getSoil() != null) {
            Soil soil = mapBox[x][y].getSoil();
            //folosim valuetotree pt a nu sta sa le punem manual
            ObjectNode soilData = OBJECT_MAPPER.valueToTree(soil);
            soilData.remove("sections");
            soilData.remove("soilQuality");
            soilData.remove("soilQualityScore");
            soilData.put("soilQuality", soil.getSoilQualityScore());
            outputNode.set("soil", soilData);
        }

        if(mapBox[x][y].getPlant() != null) {
            Plants plants = mapBox[x][y].getPlant();
            plantData.put("type", plants.getType());
            plantData.put("name", plants.getName());
            plantData.put("mass", plants.getMass());
            outputNode.set("plants", plantData);
        }

        if(mapBox[x][y].getAnimal() != null) {
            Animals animal = mapBox[x][y].getAnimal();
            //folosim valuetotree pt a nu sta sa le punem manual
            ObjectNode animalData = OBJECT_MAPPER.valueToTree(animal);
            animalData.remove("sections");
            outputNode.set("animals", animalData);
        }


        if(mapBox[x][y].getWater() != null) {
            Water water = mapBox[x][y].getWater();
            ObjectNode waterData = OBJECT_MAPPER.createObjectNode();
            waterData.put("type", water.getType());
            waterData.put("name", water.getName());
            waterData.put("mass", water.getMass());
            //waterData.put("water_quality", water.getWater_quality());
            outputNode.set("water", waterData);
        }

        if(mapBox[x][y].getAir() != null) {
            Air air = mapBox[x][y].getAir();
            ObjectNode airData = OBJECT_MAPPER.valueToTree(air);
            airData.remove("airQuality");
            airData.remove("airQualityScore");
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

    private Main() {
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final ObjectWriter WRITER = MAPPER.writer().withDefaultPrettyPrinter();
    public static final ObjectNode OBJECT_MAPPER = MAPPER.createObjectNode();

    /**
     * @param inputPath input file path
     * @param outputPath output file path
     * @throws IOException when files cannot be loaded.
     */
    public static void action(final String inputPath,
                              final String outputPath) throws IOException {

        InputLoader inputLoader = new InputLoader(inputPath);
        ArrayNode output = MAPPER.createArrayNode();

        /*
         * TODO Implement your function here
         *
         * How to add output to the output array?
         * There are multiple ways to do this, here is one example:
         *
         *
         ObjectNode objectNode = MAPPER.createObjectNode();
         objectNode.put("field_name", "field_value");
         *
         ArrayNode arrayNode = MAPPER.createArrayNode();
         arrayNode.add(objectNode);
         *
         output.add(arrayNode);
         output.add(objectNode);
         *
         */
        //inputLoader.getSimulations();

        //ArrayList<CommandInput> commands = inputLoader.getCommands();


        InputHolder inputHolder = new InputHolder();
        inputHolder = MAPPER.readValue(new  File(inputPath), InputHolder.class);



        ArrayList<SimulationParams> simulationParams = inputHolder.getSimulationParams();
        SimulationParams sim = simulationParams.getFirst();

        ArrayList<Commands> commands = inputHolder.getCommands();


        InitializeMap worldMap = new InitializeMap(sim.getTerritoryDim());
        worldMap.populateMap(sim.getTerritorySectionParams());

        WorldManager worldManager = new WorldManager(worldMap,sim);

        for (Commands command : commands) {
            //IO.println(command.getCommand() + " timestamp : " + command.getTimestamp());
            ObjectNode out = worldManager.commandManager(command);

            if(out != null) {
                output.add(out);
            }
        }




        worldMap.printMapBox();

        //IO.println(sim.getTerritoryDim() + sim.getEnergyPoints() + sim.getTerritorySectionParams());

//        ObjectNode objectNode = MAPPER.createObjectNode();
//        objectNode.put("output", "value");
//        ArrayNode arrayNode = MAPPER.createArrayNode();
//        arrayNode.add(objectNode);
//        output.add(arrayNode);
//        output.add(objectNode);
//
//
//
//        ObjectNode plants = MAPPER.createObjectNode();
//        //plants.set("plants", plants);
//        plants.put("type", "Algae");
//        plants.put("name", "EmeraldAlgae");
//        plants.put("mass", 0.4);
//        objectNode.set("Plants",plants);
//
//        arrayNode.add(plants);


        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs();
        WRITER.writeValue(outputFile, output);
    }
}
