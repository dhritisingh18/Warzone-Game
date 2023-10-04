package controller;

import model.*;
import utils.InvalidExecutionFailure;
import utils.ValidationFailure;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class for Reinforcement - assignment of armies
 * @author Mohammad Ehtesham Arif
 * @author Dhriti Singh
 * @author Rabia Tahir
 * @author Simran Simran
 * @author Ritik Gulati
 * @author Ritika Dhamija
 * @version 1.0.0
 */
public class Reinforcement implements GameController {
    /**
     * next phase of game variable
     */
    GamePhase d_NextGamePhase = GamePhase.IssueOrder;
    /**
     *  current phase of game variable
     */
    GamePhase d_GamePhase;
    /**
     * Game map variable
     */
    GameMap d_GameMap;

    /**
     *  Current Player variable
     */
    Player d_CurrentPlayer;

    /**
     * Default constructor initialising the Game map data member with
     * {@code GameMap} singleton object
     */
    public Reinforcement() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Initiates the Reinforcement phase of the game.
     *
     * @param currentGamePhase The current phase of the game.
     * @return The next phase of the game.
     * @throws ValidationFailure       If there are validation errors.
     * @throws InvalidExecutionFailure If there's an invalid execution.
     */
    public GamePhase start(GamePhase currentGamePhase) throws ValidationFailure, InvalidExecutionFailure {
        d_GamePhase = currentGamePhase;
        calculateReinforcements();
        return d_NextGamePhase;
    }

    /**
     * Calculate and set reinforcement armies for each player.
     *
     * @throws InvalidExecutionFailure If there's an invalid execution.
     */
    public void calculateReinforcements() throws InvalidExecutionFailure {
        Iterator<Player> playerIterator = d_GameMap.getPlayers().values().iterator();
        while (playerIterator.hasNext()) {
            d_CurrentPlayer = playerIterator.next();
            setReinforcement();
        }
    }


    /**
     * Calculate and set reinforcement armies for the current player.
     *
     * @throws InvalidExecutionFailure If there's an invalid execution.
     */
    public void setReinforcement() throws InvalidExecutionFailure {
        if (!d_GamePhase.equals(GamePhase.Reinforcement)) {
            throw new InvalidExecutionFailure();
        }

        int reinforcements = calculateReinforcementsForPlayer();

        // Ensure the player receives at least 3 armies
        d_CurrentPlayer.setReinforcementArmies(Math.max(reinforcements, 3));
        System.out.println("Player " + d_CurrentPlayer.getName() + " has been given " + d_CurrentPlayer.getReinforcementArmies() + " armies.");
    }

    /**
     * Calculate reinforcements based on the number of countries and continents owned by the player.
     *
     * @return The calculated reinforcement armies.
     */
    private int calculateReinforcementsForPlayer() {
        int reinforcements = d_CurrentPlayer.getCapturedCountries().size() / 3;

        Map<String, List<Country>> countryMap = d_CurrentPlayer.getCapturedCountries()
                .stream()
                .collect(Collectors.groupingBy(Country::getContinent));

        for (String continent : countryMap.keySet()) {
            Continent currentContinent = d_GameMap.getContinent(continent);
            List<Country> playerCountriesInContinent = countryMap.get(continent);

            if (currentContinent.getCountries().containsAll(playerCountriesInContinent)) {
                reinforcements += currentContinent.getAwardArmies();
            }
        }

        return reinforcements;
    }

}
