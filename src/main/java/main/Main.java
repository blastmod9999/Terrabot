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
import simulation.Commands;
import simulation.SimulationParams;
import simulation.WeatherConditions;
import terraBot.TerraBot;



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
    private int timeStopWeather;
    //Instantiem robotul
    private TerraBot terraBot; ;
    //public WorldManager(InitializeMap map, Robot robot) {}


    public boolean isSimStarted() {
        return isSimStarted;
    }

    public void setSimStarted(boolean simStarted) {
        isSimStarted = simStarted;
    }

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


    public WorldManager(ArrayList<SimulationParams> simulationParams, int iteration) {

        SimulationParams simulationParam = simulationParams.get(iteration);

        InitializeMap map = new InitializeMap(simulationParam.getTerritoryDim());
        map.populateMap(simulationParam.getTerritorySectionParams());

        this.map = map;
        this.simulationParams = simulationParam;
        this.terraBot = new TerraBot(simulationParam.getEnergyPoints());
    }

    public ObjectNode commandManager(Commands command, ArrayList<SimulationParams> simulationParams) {

        //new WorldManager(simulationParams,1);

        ObjectNode outputNode = OBJECT_MAPPER.createObjectNode();
        outputNode.put("command", command.getCommand());

        if(isSimStarted() && timeStopWeather <= command.getTimestamp()) {
            updateWeather();
        }

        if(isSimStarted()) {
            UpdateScannedBox();
        }

        if(terraBot.isCharging()) {
            if(command.getTimestamp() >= terraBot.getChargeFinTimestamp())
                terraBot.setCharging(false);
            else {
                outputNode.put("message", "ERROR: Robot still charging. Cannot perform action");
                outputNode.put("timestamp", command.getTimestamp());
            }

        }

        if(!terraBot.isCharging())
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
                if (!isSimStarted) {
                    outputNode.put("message", "ERROR: Simulation not started. Cannot perform action");
                } else {
                    IO.println(command.getType());
                    if(ChangeWeather(map,command)) {
                        outputNode.put("message", "The weather has changed.");
                    } else
                        outputNode.put("message", "ERROR: The weather change does not affect the environment. Cannot perform action");
                    //WeatherConditions(map,command.getType(),command.getRainfall());
                    outputNode.put("timestamp", command.getTimestamp());
                    timeStopWeather+=command.getTimestamp()+3;

                }


                break;

            case "moveRobot":
                if(!isSimStarted) {
                    outputNode.put("message", "ERROR: Simulation not started. Cannot perform action");
                } else
                    outputNode.put("message", terraBot.moveRobot(map));
                outputNode.put("timestamp", command.getTimestamp());

                break;

            case "rechargeBattery":
                if (!isSimStarted) {
                    outputNode.put("message", "ERROR: Simulation not started. Cannot perform action");
                    outputNode.put("timestamp", command.getTimestamp());
                } else {
                    terraBot.setChargeFinTimestamp(command.getTimestamp() + command.getTimeToCharge());
                    terraBot.setCharging(true);
                    terraBot.setBatteryCharge(terraBot.getBatteryCharge()+command.getTimeToCharge());
                    outputNode.put("message", "Robot battery is charging.");
                    outputNode.put("timestamp", command.getTimestamp());
                    //IO.println("TIME TO CHARGE BATTERY : " + command.getTimeToCharge());
                }
                break;
            case "scanObject":
                if(!isSimStarted) {
                    outputNode.put("message", "ERROR: Simulation not started. Cannot perform action");
                    outputNode.put("timestamp", command.getTimestamp());
                } else {
                    String result = terraBot.scanObject(map, command.getColor(), command.getSmell(), command.getSound());
                    if(result.equals("ERROR")) {
                        outputNode.put("message", "ERROR: Object not found. Cannot perform action");
                    } else {
                        outputNode.put("message", "The scanned object is "+ result +".");
                        terraBot.setBatteryCharge(terraBot.getBatteryCharge() - 7);
                    }
                    outputNode.put("timestamp", command.getTimestamp());

                }
                break;

            case "learnFact":
                break;

            case "getEnergyStatus":
                if (!isSimStarted) {
                    outputNode.put("message", "ERROR: Simulation not started. Cannot perform action");
                    outputNode.put("timestamp", command.getTimestamp());
                } else {
                    outputNode.put("message", "TerraBot has "+ terraBot.getBatteryCharge() +" energy points left.");
                    outputNode.put("timestamp", command.getTimestamp());
                }
                break;

            default:
                break;
            }



        return outputNode;
    }



    void UpdateScannedBox(){
        MapBox[][] mapBox = map.getEnvMap();
        for(int j=0; j < map.getWidth(); j++) {
            for(int i=0; i < map.getHeight(); i++) {
                if(mapBox[i][j].getPlant() != null) {
                    Plants plant =  mapBox[i][j].getPlant();
                    if(plant.scanned){
                        //IO.println("AICI DOAR DE 3 ORI IN PRINCIPIU");
                        plant.UpdateBox(mapBox[i][j].getAir());
                    }
                }
                if(mapBox[i][j].getWater() != null) {
                    Water water = mapBox[i][j].getWater();
                    if(water.scanned){
                        water.UpdateBox(mapBox[i][j]);
                    }
                }

                if(mapBox[i][j].getAnimal() != null) {
                    Animals animal = mapBox[i][j].getAnimal();
                    if(animal.scanned){
                        //animal.move(map, i, j);
                    }
                }
            }
        }

    }

    boolean ChangeWeather (InitializeMap map, Commands command) {
        boolean result = false;
        MapBox[][] mapBox = map.getEnvMap();

        for(int j=0; j < map.getWidth(); j++) {
            for(int i=0; i < map.getHeight(); i++) {
                if(mapBox[i][j].getAir() != null) {
                    Air air = mapBox[i][j].getAir();
                    if (air.ApplyWeatherConditions(command)) {
                        result = true;
                    }
                }
            }
        }



        return result;
    }

    private void updateWeather() {
        MapBox[][] mapBox = map.getEnvMap();
        for (int j = 0; j < map.getWidth(); j++) {
            for (int i = 0; i < map.getHeight(); i++) {
                if (mapBox[i][j].getAir() != null) {
                    mapBox[i][j].getAir().resetWeather();
                }
            }
        }
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
        //SimulationParams sim = simulationParams.getLast();

        ArrayList<Commands> commands = inputHolder.getCommands();


        //InitializeMap worldMap = new InitializeMap(sim.getTerritoryDim());
        //worldMap.populateMap(sim.getTerritorySectionParams());

        //WorldManager worldManager = new WorldManager(worldMap,sim);

        WorldManager worldManager = new WorldManager(simulationParams,0);

        int iteration = 0;
        for (Commands command : commands) {
            //IO.println(command.getCommand() + " timestamp : " + command.getTimestamp());

            ObjectNode out = worldManager.commandManager(command,simulationParams);

            if(out != null) {
                output.add(out);
            }

            if (command.getCommand().equals("endSimulation") &&
                    out.has("message") &&
                    out.get("message").asText().equals("Simulation has ended.")) {

                iteration++;
                if (iteration < simulationParams.size()) {
                    worldManager = new WorldManager(simulationParams, iteration);
                }
            }
        }




        //worldMap.printMapBox();

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
