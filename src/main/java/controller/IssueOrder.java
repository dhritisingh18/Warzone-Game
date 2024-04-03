package controller;

import model.*;

import java.util.Scanner;

/**
 * Class with all the ways to issue order
 *
 * @author Dhriti Singh
 * @version 1.0.0
 */
public class IssueOrder implements GameHandler {
    GameCycle d_NextGameCycle = GameCycle.OrderExecution;
    GameCycle d_GameCycle = GameCycle.OrderIssuance;
    GameMatrix d_GameMatrix;

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructor to get the GameMatrix instance
     */
    public IssueOrder() {
        d_GameMatrix = GameMatrix.getInstance();
    }

    /**
     * A function to for starting thr order issue phase
     *
     * @param p_GameCycle The current phase which is executing
     * @return the next phase to be executed
     * @throws Exception when execution fails
     */

    @Override
    public GameCycle begin(GameCycle p_GameCycle) throws Exception {
        d_GameCycle = p_GameCycle;
        for (int l_Counter = 0; l_Counter < d_GameMatrix.getPlayers().size(); l_Counter++) {
            for (Player l_Player : d_GameMatrix.getPlayers().values()) {
                if (l_Player.getReinforcementArmies() <= 0) {
                    continue;
                }
                System.out.println("Player:" + l_Player.getName() + "; Armies assigned are: " + l_Player.getReinforcementArmies());
                System.out.println("The countries that require armies to be assigned are: ");
                l_Player.getCapturedCountries().forEach(country -> {
                    System.out.println(country.getName() + " ");
                });
                System.out.println("=========================================================================================");
                String l_Commands = readFromPlayer();
                l_Player.issueOrder(l_Commands);
            }
        }

        System.out.println("Alas! All Armies have been exhausted. Move to the next phase.");
        System.out.println("=========================================================================================");
        return p_GameCycle.nextState(d_NextGameCycle);
    }

    /**
     * A function to read all the commands from player
     *
     * @return command entered by the player
     */
    private String readFromPlayer() {
        String l_Command;
        System.out.println("Inorder to issue your orders: ");
        System.out.println("1. Enter help to view the set of command");
        while (true) {
            l_Command = scanner.nextLine();
            if ("deploy".equalsIgnoreCase(l_Command.split(" ")[0])) {
                if (checkIfCommandIsDeploy(l_Command.toLowerCase())) {
                    return l_Command;
                }
            } else {
                System.out.println("List of game loop commands");
                System.out.println("To deploy the armies : deploy countryID armies");
                System.out.println("Please enter the correct command");
            }
        }
    }

    /**
     * A function for command validation
     *
     * @param p_Command The command entered by player
     * @return true if the format is valid else false
     */
    private boolean checkIfCommandIsDeploy(String p_Command) {
        String[] l_Commands = p_Command.split(" ");
        if (l_Commands.length != 3) {
            return false;
        } else {
            return l_Commands[0].equals("deploy");
        }

    }

}



