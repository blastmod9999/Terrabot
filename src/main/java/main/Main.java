package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.air.*;
import entities.animal.Animals;
import entities.plant.Plants;
import entities.soil.*;
import entities.water.Water;
import fileio.CommandInput;
import fileio.InputLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

class TerritorySectionParams {
    private ArrayList<Soil> soil;
    private ArrayList<Plants> plants;
    private ArrayList<Animals> animals;
    private ArrayList<Water> water;
    private ArrayList<Air> air;
        //test push pc buc
    public ArrayList<Soil> getSoil() {
        return soil;
    }

    public void setSoil(ArrayList<Soil> soil) {
        this.soil = soil;
    }

    public ArrayList<Plants> getPlants() {
        return plants;
    }

    public void setPlants(ArrayList<Plants> plants) {
        this.plants = plants;
    }

    public ArrayList<Animals> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animals> animals) {
        this.animals = animals;
    }

    public ArrayList<Water> getWater() {
        return water;
    }

    public void setWater(ArrayList<Water> water) {
        this.water = water;
    }

    public ArrayList<Air> getAir() {
        return air;
    }

    public void setAir(ArrayList<Air> air) {
        this.air = air;
    }
}

class SimulationParams {

    private String territoryDim;
    private int energyPoints;
    private TerritorySectionParams territorySectionParams;

    public TerritorySectionParams getTerritorySectionParams() {
        return territorySectionParams;
    }

    public void setTerritorySectionParams(TerritorySectionParams territorySectionParams) {
        this.territorySectionParams = territorySectionParams;
    }

    public String getTerritoryDim() {
        return territoryDim;
    }

    public void setTerritoryDim(String territoryDim) {
        this.territoryDim = territoryDim;
    }

    public int getEnergyPoints() {
        return energyPoints;
    }
    public void setEnergyPoints(int energyPoints) {
        this.energyPoints = energyPoints;
    }
}

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


class MapBox {
    private Animals animal;
    private Plants plant;
    private Air air;
    private Soil soil;
    private Water water;

    public Animals getAnimal() {
        return animal;
    }

    public void setAnimal(Animals animal) {
        this.animal = animal;
    }

    public Plants getPlant() {
        return plant;
    }

    public void setPlant(Plants plant) {
        this.plant = plant;
    }

    public Air getAir() {
        return air;
    }

    public void setAir(Air air) {
        this.air = air;
    }

    public Soil getSoil() {
        return soil;
    }

    public void setSoil(Soil soil) {
        this.soil = soil;
    }

    public Water getWater() {
        return water;
    }

    public void setWater(Water water) {
        this.water = water;
    }
}

class InitializeMap {
    private MapBox[][] envMap;
    private int width;
    private int height;


    public MapBox[][] getEnvMap() {
        return envMap;
    }

    InitializeMap(String dims) {
        //https://www.geeksforgeeks.org/java/java-program-to-convert-string-to-integer-array
        int[] dims2 = Arrays.stream(dims.split("x")).mapToInt(Integer::parseInt).toArray();
        this.width = dims2[0];
        this.height = dims2[1];
        //IO.println("Dims: " + dims2[1] + "x" + dims2[0]);
        this.envMap = new MapBox[dims2[0]][dims2[1]];

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.envMap[i][j] = new MapBox();
            }
        }
    }


    void populateMap(TerritorySectionParams params) {
        if(params.getSoil() != null) {
            for(int i = 0; i < params.getSoil().size(); i++) {
                Soil soil = params.getSoil().get(i);
                for(int j=0 ; j < soil.getSections().size(); j++) {
                    int x = soil.getSections().get(j).getX();
                    int y = soil.getSections().get(j).getY();

                    if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
                        envMap[x][y].setSoil(soil);
                    }
                }
            }
        }

        if(params.getPlants() != null) {
            for(int i = 0; i < params.getPlants().size(); i++) {
                Plants plant = params.getPlants().get(i);
                for(int j=0 ; j < plant.getSections().size(); j++) {
                    int x = plant.getSections().get(j).getX();
                    int y = plant.getSections().get(j).getY();

                    if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
                        envMap[x][y].setPlant(plant);
                    }
                }
            }
        }

        if(params.getAnimals() != null) {
            for(int i = 0; i < params.getAnimals().size(); i++) {
                Animals animal = params.getAnimals().get(i);
                for(int j=0 ; j < animal.getSections().size(); j++) {
                    int x = animal.getSections().get(j).getX();
                    int y = animal.getSections().get(j).getY();

                    if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
                        envMap[x][y].setAnimal(animal);
                    }
                }
            }
        }

        if(params.getWater() != null) {
            for(int i = 0; i < params.getWater().size(); i++) {
                Water water = params.getWater().get(i);
                for(int j=0 ; j < water.getSections().size(); j++) {
                    int x = water.getSections().get(j).getX();
                    int y = water.getSections().get(j).getY();

                    if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
                        envMap[x][y].setWater(water);
                    }
                }
            }
        }

        if(params.getAir() != null) {
            for(int i = 0; i < params.getAir().size(); i++) {
                Air air = params.getAir().get(i);
                for(int j=0 ; j < air.getSections().size(); j++) {
                    int x = air.getSections().get(j).getX();
                    int y = air.getSections().get(j).getY();

                    if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
                        envMap[x][y].setAir(air);
                    }
                }
            }
        }

    }


    void printMapBox() {
        for(int x = 0; x < this.width; x++) {
            for(int y = 0; y < this.height; y++) {
                IO.print("["+x+" "+ y + "]" + " Box contains : " + envMap[x][y].getAir() + " " + envMap[x][y].getWater() + " " + envMap[x][y].getAnimal() + " " + envMap[x][y].getPlant() + " " + "\n");
            }
        }
    }

}


class WorldManager {
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private boolean isSimStarted = false;
    private InitializeMap map;
    private int timestamp;


    public WorldManager(InitializeMap map) {
        this.map = map;
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
                    outputNode.put("message", "Simulation has started.");
                }
                outputNode.put("timestamp", command.getTimestamp());
                break;

            case "printEnvConditions":
                break;

            case "printMap":
                break;

            case "endSimulation":
                break;

            case "changeWeatherConditions":
                break;

            case "moveRobot":
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

        WorldManager worldManager = new WorldManager(worldMap);

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
//        objectNode.put(sim.getTerritoryDim(), sim.getEnergyPoints());
//        ArrayNode arrayNode = MAPPER.createArrayNode();
//        arrayNode.add(objectNode);
//        output.add(arrayNode);
//        output.add(objectNode);

        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs();
        WRITER.writeValue(outputFile, output);
    }
}
