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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;



class TerritorySectionParams {
    private ArrayList<Soil> soil;
    private ArrayList<Plants> plants;
    private ArrayList<Animals> animals;
    private ArrayList<Water> water;
    private ArrayList<Air> air;

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

        for (Commands command : commands) {
            IO.println(command.getCommand() + " timestamp : " + command.getTimestamp());
        }

        ObjectNode objectNode = MAPPER.createObjectNode();
        objectNode.put(sim.getTerritoryDim(), sim.getEnergyPoints());
        ArrayNode arrayNode = MAPPER.createArrayNode();
        arrayNode.add(objectNode);
        output.add(arrayNode);
        output.add(objectNode);

        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs();
        WRITER.writeValue(outputFile, output);
    }
}
