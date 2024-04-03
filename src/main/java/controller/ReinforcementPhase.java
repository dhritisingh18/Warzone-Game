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
 * @author Dhriti Singh
 * @version 1.0.0
 */
public class ReinforcementPhase implements GameHandler {
    /**
     * next phase of game variable
     */
    GameCycle d_NextGamePhase = GameCycle.OrderIssuance;
    /**
     *  current phase of game variable
     */
    GameCycle d_GamePhase;
    /**
     * Game map variable
     */
    GameMatrix d_GameMap;

    /**
     *  Current Player variable
     */
    Player d_CurrentPlayer;

    /**
     * Default constructor initialising the Game map data member with
     * {@code GameMap} singleton object
     */
    public ReinforcementPhase() {
        d_GameMap = GameMatrix.getInstance();
    }

    /**
     * Initiates the Reinforcement phase of the game.
     *
     * @param currentGamePhase The current phase of the game.
     * @return The next phase of the game.
     * @throws ValidationFailure       If there are validation errors.
     * @throws InvalidExecutionFailure If there's an invalid execution.
     */
    public GameCycle begin(GameCycle currentGamePhase) throws ValidationFailure, InvalidExecutionFailure {
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
        if (!d_GamePhase.equals(GameCycle.Reinforcement)) {
            throw new InvalidExecutionFailure();
        }

        int reinforcements = calculateReinforcementsForPlayer();

        // Ensure the player receives at least 3 armies
        d_CurrentPlayer.setReinforcementArmies(Math.max(reinforcements, 5));
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
