package controller;

import model.*;
import utils.MapReader;
import utils.MapValidation;
import utils.ValidationFailure;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class executes the present phases and implementation of the Game Controller
 *
 *  * @author Mohammad Ehtesham Arif
 *  * @author Dhriti Singh
 *  * @author Rabia Tahir
 *  * @author Simran Simran
 *  * @author Ritik Gulati
 *  * @author Ritika Dhamija
 *  * @version 1.0.0
 *  */
public class GamePlay implements GameHandler {
    GameMatrix d_GameMatrix;
    GameCycle d_NextState = GameCycle.Reinforcement;
    private final Scanner SCANNER = new Scanner(System.in);
    private final List<String> CLI_COMMANDS = Arrays.asList("showmap", "loadmap", "gameplayer", "assigncountries");

    /**
     * This is the default constructor
     */
    public GamePlay() {
        d_GameMatrix = GameMatrix.getInstance();
    }

    /**
     * This function begins game phase and reads the tasks of the game phase based on the command given
     *
     * @param p_GameCycle present Game Phase
     * @return the following Game Phase
     * @throws ValidationFailure at validation failure
     */
    public GameCycle start(GameCycle p_GameCycle) throws ValidationFailure {
        while (true) {
            System.out.println("1. Enter 1 to view the set of commands" + "\n" + "2. Enter 2 to end");
            String l_Input = SCANNER.nextLine();
            List<String> l_InputList = null;
            if (l_Input.contains("-")) {
                l_InputList = Arrays.stream(l_Input.split("-")).filter(s -> !s.isEmpty()).map(String::trim).collect(Collectors.toList());
            } else {
                l_InputList = Arrays.stream(l_Input.split(" ")).collect(Collectors.toList());
            }

            if (!inputValidator(l_InputList)) {
                if (l_Input.startsWith("2")) {
                    l_InputList.add(0, "2");
                } else {
                    l_InputList.clear();
                    // if not available in command list forcing to call help
                    l_InputList.add("1");
                    l_InputList.add("dummy");
                }
            }
            //Handles command loadmap

            String l_MainCmd = l_InputList.get(0);
            l_InputList.remove(l_MainCmd);
            for (String l_Command : l_InputList) {
                String[] l_CmdArray = l_Command.split(" ");
                switch (l_MainCmd.toLowerCase()) {
                    case "loadmap": {
                        if (l_CmdArray.length == 1) {
                            loadMap(l_MainCmd);
                        }
                        break;
                    }
                    //Handles command gameplayer

                    case "gameplayer": {
                        if (l_CmdArray.length > 0) {
                            switch (l_CmdArray[0]) {
                                case "add": {
                                    if (l_CmdArray.length == 2) {
                                        d_GameMatrix.addPlayer(l_CmdArray[1]);
                                    } else {
                                        throw new ValidationFailure();
                                    }
                                    break;
                                }
                                case "remove": {
                                    if (l_CmdArray.length == 2) {
                                        d_GameMatrix.removePlayer(l_CmdArray[1]);
                                    } else {
                                        throw new ValidationFailure();
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    }
                    //Handles command assigncountries

                    case "assigncountries": {
                        if (d_GameMatrix.getPlayers().size() > 1) {
                            d_GameMatrix.assignCountries();
                            System.out.println("----------------------------Load Game Phase is over----------------------------");
                            return p_GameCycle.nextState(d_NextState);
                        } else {
                            throw new ValidationFailure("Sorry! At least two players are required to proceed");
                        }
                    }
                    //Handles command showmap

                    case "showmap": {
                        d_GameMatrix.showMap();
                        break;
                    }
                    case "exit": {
                        return p_GameCycle.nextState(d_NextState);
                    }
                    //Print help commands
                    default: {
                        System.out.println("List of Game Play commands:");
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("Load the map : loadmap filename");
                        System.out.println("Show the loaded map : showmap");
                        System.out.println("Add or remove a player : gameplayer -add playername -remove playername");
                        System.out.println("Assignment of countries to players: assigncountries");
                        System.out.println("-----------------------------------------------------------------------------------------");

                    }
                }
            }
        }
    }

    /**
     * This method reads the game map from the map file
     *
     * @param p_Filename file name of the map
     * @throws ValidationFailure at validation failure
     */
    private void loadMap(String p_Filename) throws ValidationFailure {
        MapReader.readMap(d_GameMatrix, p_Filename);
        if (MapValidation.validateMap(d_GameMatrix, 0)==false) {
            throw new ValidationFailure("Map is Invalid");
        }
    }

    /**
     * This method validates to check if the present cli command is executable
     * in the current phase
     *
     * @param p_InputList the command list
     * @return true if command is executable else false
     */
    public boolean inputValidator(List<String> p_InputList) {
        if (p_InputList.size() > 0) {
            String l_MainCommand = p_InputList.get(0);
            if (p_InputList.size() == 1) {
                p_InputList.add("dummy");
            }
            return CLI_COMMANDS.contains(l_MainCommand.toLowerCase());
        }
        return false;
    }
}