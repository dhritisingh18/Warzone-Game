package controller;

import model.GameHandler;
import model.GameMatrix;
import model.GameCycle;
import utils.MapReader;
import utils.MapValidation;
import utils.ValidationFailure;
import java.util.*;
import java.util.stream.Collectors;

import constants.Consts;

/**
 * This class is used to create map using game console Cmds.
 *
 * @author Mohammad Ehtesham Arif
 * @author Dhriti Singh
 * @author Rabia Tahir
 * @author Simran Simran
 * @author Ritik Gulati 
 * @author Ritika Dhamija
 * @version 1.0.0
 */
public class MapEditor implements GameHandler {
    private final Scanner SCANNER = new Scanner(System.in);
    private final List<String> CLI_CmdS = Arrays.asList("editcontinent", "editcountry", "editneighbor", "showmap", "savemap", "editmap", "validatemap");
    GameMatrix d_GameMatrix;
    GameCycle d_NextState = GameCycle.GameLoad;

    /**
     * This is the default constructor
     */
    public MapEditor() {
        this.d_GameMatrix = GameMatrix.getInstance();
    }

    /**
     * The start method of MapEditor phase that handles creation, validation
     * save of map from console Cmds.
     *
     * @param p_GamePhase Parameter of the enum GamePhase is passed
     * @throws ValidationFailure when validation fails
     */
    @Override
    public GameCycle begin(GameCycle p_GamePhase) throws ValidationFailure {
        while (true) {
            System.out.println("Please Enter map operation:" + "\n" + "1. Enter 1 to view the list of operations" + "\n" + "2. Enter 2 to end map creation and move to game phase");
            String l_UserInput = SCANNER.nextLine();
            List<String> l_UserInputList = null;
            if (l_UserInput.contains("-")) {
                l_UserInputList = Arrays.stream(l_UserInput.split("-")).filter(s -> !s.isEmpty()).map(String::trim).collect(Collectors.toList());
            } else {
                l_UserInputList = Arrays.stream(l_UserInput.split(" ")).collect(Collectors.toList());
            }

            if (!inputValidatorFunc(l_UserInputList)) {
                if (l_UserInput.startsWith("2")) {
                    l_UserInputList.add(0, "2");
                } else {
                    l_UserInputList.clear();
                    // if not available in Cmd list forcing to call help
                    l_UserInputList.add("1");
                    l_UserInputList.add("dummy");
                }
            }

            /**
             * Handle editcontinent Cmd from console
             */

            String l_MainCmd = l_UserInputList.get(0);
            l_UserInputList.remove(l_MainCmd);
            for (String l_Cmd : l_UserInputList) {
                String[] l_InputCmdArray = l_Cmd.split(" ");
                switch (l_MainCmd.toLowerCase()) {
                    case "editcontinent": {
                        if (l_InputCmdArray.length > 0) {
                            switch (l_InputCmdArray[0]) {
                                case Consts.ADD: {
                                    if (l_InputCmdArray.length == 3) {
                                        d_GameMatrix.addContinent(l_InputCmdArray[1], l_InputCmdArray[2]);
                                    } else {
                                        throw new ValidationFailure();
                                    }
                                    break;
                                }
                                case Consts.REMOVE: {
                                    if (l_InputCmdArray.length == 2) {
                                        d_GameMatrix.removeContinent(l_InputCmdArray[1]);
                                    } else {
                                        throw new ValidationFailure();
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    }

                    /**
                     * Handle editcountry Cmd from console
                     */

                    case "editcountry": {
                        switch (l_InputCmdArray[0]) {
                            case Consts.ADD: {
                                if (l_InputCmdArray.length == 3) {
                                    d_GameMatrix.addCountry(l_InputCmdArray[1], l_InputCmdArray[2]);
                                } else {
                                    throw new ValidationFailure();
                                }
                                break;
                            }
                            case Consts.REMOVE: {
                                if (l_InputCmdArray.length == 2) {
                                    d_GameMatrix.removeCountry(l_InputCmdArray[1]);
                                } else {
                                    throw new ValidationFailure();
                                }
                                break;
                            }
                        }
                        break;
                    }

                    /**
                     * Handle editneighbor Cmd from console
                     */

                    case "editneighbor": {
                        switch (l_InputCmdArray[0]) {
                            case Consts.ADD: {
                                if (l_InputCmdArray.length == 3) {
                                    d_GameMatrix.addNeighbor(l_InputCmdArray[1], l_InputCmdArray[2]);
                                } else {
                                    throw new ValidationFailure();
                                }
                                break;
                            }
                            case Consts.REMOVE: {
                                if (l_InputCmdArray.length == 3) {
                                    d_GameMatrix.removeNeighbor(l_InputCmdArray[1], l_InputCmdArray[2]);
                                } else {
                                    throw new ValidationFailure();
                                }
                                break;
                            }
                        }
                        break;
                    }

                    /**
                     * Handle showmap Cmd from console
                     */

                    case "showmap": {
                        d_GameMatrix.showMap();
                        break;
                    }
                    //Handle validatemap Cmd from console
                    case "validatemap": {
                        if (MapValidation.validateMap(d_GameMatrix, 0)) {
                            System.out.println("Validation successful");
                        } else {
                            System.out.println("Validation failed");
                        }
                        break;
                    }

                    /**
                     * Handle savemap Cmd from console
                     */

                    case "savemap": {
                        if (l_InputCmdArray.length == 1) {
                            d_GameMatrix.setName(l_InputCmdArray[0]);
                            d_GameMatrix.saveMap();
                        }
                        break;
                    }

                    /**
                     * Handle editmap Cmd from console
                     */

                    case "editmap": {
                        if (l_InputCmdArray.length == 1) {
                            MapReader.readMap(d_GameMatrix, l_InputCmdArray[0]);
                        }
                        break;
                    }

                    /**
                     * To exit the map creation phase type "exit"
                     */

                    case "exit": {
                        d_GameMatrix.clearGameMap();
                        return p_GamePhase.nextState(d_NextState);
                    }
                    //Print the Cmds for help

                    default: {
                        System.out.println("List of User map editor Cmds from console:");
                        System.out.println("To add or remove a continent : editcontinent -add continentID continentvalue -remove continentID");
                        System.out.println("To add or remove a country : editcountry -add countryID continentID -remove countryID");
                        System.out.println("To add or remove a neighbor to a country : editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("Read/Update existing map Cmds:");
                        System.out.println("To edit map: editmap filename");
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("Additional map Cmds:");
                        System.out.println("To show the map: showmap");
                        System.out.println("To validate map: validatemap");
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.println("Note: To save the created map use the Cmd:");
                        System.out.println("To save map: savemap filename");
                        System.out.println("================================End of Map Editor Phase==================================");
                    }
                }
            }
        }
    }

    /**
     * This method validates to check if the current cli Cmd is executable
     * in the current phase
     *
     * @param p_InputList the Cmd list from console
     * @return true if Cmd is executable else false
     */
    public boolean inputValidatorFunc(List<String> p_InputList) {
        if (p_InputList.size() > 0) {
            String l_MainCmd = p_InputList.get(0);
            if (p_InputList.size() == 1) {
                p_InputList.add("dummy");
            }
            return CLI_CmdS.contains(l_MainCmd.toLowerCase());
        }
        return false;
    }

}
