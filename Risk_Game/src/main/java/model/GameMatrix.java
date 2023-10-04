package model;

import utils.MapValidation;
import utils.SaveMap;
import utils.ValidationFailure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class with all the game matrix properties
 *
 * @author Mohammad Ehtesham Arif
 * @author Dhriti Singh
 * @author Rabia Tahir
 * @author Simran Simran
 * @author Ritik Gulati
 * @author Ritika Dhamija
 * @version 1.0.0
 */


public class GameMatrix {
    private static GameMatrix d_GameMap;
    private HashMap<String, Continent> d_Continents = new HashMap<>();
    private HashMap<String, Country> d_Countries = new HashMap<>();
    private HashMap<String, Player> d_Players = new HashMap<>();
    private String d_Name;
    private String d_ErrorMessage;

    /**
     * Default Constructor
     */

    private GameMatrix() {
    }

    /**
     * Method gets Game matrix class instance
     *
     * @return the class obj
     */


    public static GameMatrix getInstance() {
        if (Objects.isNull(d_GameMap)) {
            d_GameMap = new GameMatrix();
        }
        return d_GameMap;
    }

    /**
     * Method to get list of all continents
     *
     * @return d_Continents is continents list
     */


    public HashMap<String, Continent> getContinents() {
        return d_Continents;
    }

    /**
     * Gets one continent
     *
     * @param p_Id Continent specific id
     * @return Continent obj required
     */

    public Continent getContinent(String p_Id) {
        return d_Continents.get(p_Id);
    }

    /**
     * Get List of country
     *
     * @return d_Countries
     */


    public HashMap<String, Country> getCountries() {
        return d_Countries;
    }

    /**
     * Get one country
     *
     * @param p_Id Country id name
     * @return country obj
     */


    public Country getCountry(String p_Id) {
        return d_Countries.get(p_Id);
    }

    /**
     * Get players list
     *
     * @return d_Players players list
     */


    public HashMap<String, Player> getPlayers() {
        return d_Players;
    }

    /**
     * Get a single player
     *
     * @param p_Id id Player name
     * @return Player obj
     */


    public Player getPlayer(String p_Id) {
        return d_Players.get(p_Id);
    }

    /**
     * Method to get error msg
     *
     * @return d_ErrorMessage
     */


    public String getErrorMessage() {
        return d_ErrorMessage;
    }

    /**
     * Method for error msg
     *
     * @param p_ErrorMessage - error msg
     */


    public void setErrorMessage(String p_ErrorMessage) {
        this.d_ErrorMessage = p_ErrorMessage;
    }

    /**
     * reutrn name of map
     *
     * @return name of map
     */


    public String getName() {
        return d_Name;
    }

    /**
     * To set name of map
     *
     * @param p_Name name of map
     */


    public void setName(String p_Name) {
        this.d_Name = p_Name;
    }


    /**
     * Method that sets Gamematrix obj to empty at the end of every phase
     */


    public void clearGameMap() {
        GameMatrix.getInstance().getContinents().clear();
        GameMatrix.getInstance().getCountries().clear();
        GameMatrix.getInstance().getPlayers().clear();
    }

    /**
     * Amend continent to continets list
     *
     * @param p_ContinentName name of continent
     * @param p_ControlValue  control val of continent
     * @throws ValidationFailure incase of I/O problem
     */


    public void addContinent(String p_ContinentName, String p_ControlValue) throws ValidationFailure {

        if (this.getContinents().containsKey(p_ContinentName)) {
            throw new ValidationFailure("There is a Continent that matches with the Continent Entered");
        }
        Continent l_Continent = new Continent();
        l_Continent.setName(p_ContinentName);
        l_Continent.setAwardArmies(Integer.parseInt(p_ControlValue));
        this.getContinents().put(p_ContinentName, l_Continent);
        System.out.println("Continent Successfully added: " + p_ContinentName);
    }

    /**
     * Adds country to the map's country list and continent's
     * country list.
     *
     * @param p_CountryName   Country name
     * @param p_ContinentName Continent name
     * @throws ValidationFailure incase of I/O problem
     */


    public void addCountry(String p_CountryName, String p_ContinentName) throws ValidationFailure {

        if (this.getCountries().containsKey(p_CountryName)) {
            throw new ValidationFailure("Action invalid. This country exists already");
        }
        Country l_Country = new Country();
        l_Country.setName(p_CountryName);
        l_Country.setContinent(p_ContinentName);
        this.getCountries().put(p_CountryName, l_Country);
        this.getContinent(p_ContinentName).getCountries().add(l_Country);
        System.out.println("Addition of country successful " + p_CountryName);
    }

    /**
     * Continent removed from all lists
     *
     * @param p_ContinentName Name of country
     * @throws utils.ValidationFailure incase of I/O problem
     */


    public void removeContinent(String p_ContinentName) throws ValidationFailure {

        if (!this.getContinents().containsKey(p_ContinentName)) {
            throw new ValidationFailure("Continent not found");
        }
        Set<String> l_CountrySet = this.getContinents().remove(p_ContinentName)
                .getCountries()
                .stream().map(Country::getName)
                .collect(Collectors.toSet());
        for (String l_CountryName : l_CountrySet) {
            this.getCountries().remove(l_CountryName);
        }
        System.out.println("Continent is deleted " + p_ContinentName);
    }

    /**
     * Country removed from all lists
     *
     * @param p_CountryName Name of country
     * @throws ValidationFailure incase of I/O problem
     */


    public void removeCountry(String p_CountryName) throws ValidationFailure {
        Country l_Country = this.getCountry(p_CountryName);
        if (Objects.isNull(l_Country)) {
            throw new ValidationFailure("This country is not found: " + p_CountryName);
        }
        this.getContinent(l_Country.getContinent()).getCountries().remove(l_Country);
        this.getCountries().remove(l_Country.getName());
        System.out.println("Deleted country!");
    }

    /**
     * Adds the neighbor to particular country
     *
     * @param p_CountryName         Name of country
     * @param p_NeighborCountryName Name of neighbouring country
     * @throws ValidationFailure incase of I/O problem
     */


    public void addNeighbor(String p_CountryName, String p_NeighborCountryName) throws ValidationFailure {
        Country l_Country1 = this.getCountry(p_CountryName);
        Country l_Country2 = this.getCountry(p_NeighborCountryName);
        if (Objects.isNull(l_Country1) || Objects.isNull(l_Country2)) {
            throw new ValidationFailure(" One of the mentioned Countries does not exist");
        }
        l_Country1.getNeighbors().add(l_Country2);
        System.out.printf("Successfully connected routes between mentioned Countries: %s - %s\n", p_CountryName, p_NeighborCountryName);
    }

    /**
     * for neighbour removal
     *
     * @param p_CountryName         name of country
     * @param p_NeighborCountryName name of neighbouring country
     * @throws ValidationFailure incase of I/O problem
     */


    public void removeNeighbor(String p_CountryName, String p_NeighborCountryName) throws ValidationFailure {
        Country l_Country1 = this.getCountry(p_CountryName);
        Country l_Country2 = this.getCountry(p_NeighborCountryName);
        if (Objects.isNull(l_Country1)) {
            throw new ValidationFailure("Either one or both countries do not exist");
        } else if (!l_Country1.getNeighbors().contains(l_Country2) || !l_Country2.getNeighbors().contains(l_Country1)) {
            throw new ValidationFailure("These are not neighbouring countries ");
        } else {
            this.getCountry(p_CountryName).getNeighbors().remove(l_Country2);
            System.out.printf("Route removal successful: %s - %s\n", p_CountryName, p_NeighborCountryName);
        }
    }


    /**
     * For addition of player
     *
     * @param p_PlayerName Name of player
     * @throws ValidationFailure incase of I/O problem
     */


    public void addPlayer(String p_PlayerName) throws ValidationFailure {
        if (this.getPlayers().containsKey(p_PlayerName)) {
            throw new ValidationFailure("This player exists already");
        }
        Player l_Player = new Player();
        l_Player.setName(p_PlayerName);
        this.getPlayers().put(p_PlayerName, l_Player);
        System.out.println("Added player succesfully " + p_PlayerName);
    }

    /**
     * For player removal
     *
     * @param p_PlayerName name of player
     * @throws ValidationFailure incase of I/O problem
     */

    public void removePlayer(String p_PlayerName) throws ValidationFailure {
        Player l_Player = this.getPlayer(p_PlayerName);
        if (Objects.isNull(l_Player) || l_Player == null) {
            throw new ValidationFailure("Player Not found " + p_PlayerName);
        }
        this.getPlayers().remove(l_Player.getName());
        System.out.println("Player deletion successful: " + p_PlayerName);
    }

    /**
     * if valid, map saved as file
     *
     * @throws ValidationFailure incase I/O problem.
     */


    public void saveMap() throws ValidationFailure {

        //Depending on player demand p_size for min no of countries 
        if (MapValidation.validateMap(d_GameMap, 0)) {
            SaveMap d_SaveMap = new SaveMap();
            boolean bool = true;
            while (bool) {
                if (!Objects.isNull(d_GameMap.getName()) && !d_GameMap.getName().isEmpty()) {
                    if (d_SaveMap.saveMapIntoFile(d_GameMap, d_GameMap.getName())) {
                        System.out.println("Map validated and saved");
                    } else {
                        throw new ValidationFailure("Load a new name as Map with this name exists already");
                    }
                    bool = false;
                } else {

                    throw new ValidationFailure("Enter: file name");

                }
            }
        } else {
            throw new ValidationFailure("Cannot save. Invalid map.");
        }
    }

    /**
     * Randomly assign countries to players
     */

    public void assignCountries() {
        int d_player_index = 0;
        List<Player> d_players = d_GameMap.getPlayers().values().stream().collect(Collectors.toList());


        List<Country> d_countryList = d_GameMap.getCountries().values().stream().collect(Collectors.toList());
        ShuffleClass.knuthShuffle(d_countryList);

        int d_country = 0;
        while (d_country < d_countryList.size()) {
            Country d_c = d_countryList.get(d_country);           // to get all countries of map
            Player d_p = d_players.get(d_player_index);          // use order of player to find relevant player
            d_p.getCapturedCountries().add(d_c);
            d_c.setPlayer(d_p);
            System.out.println(d_c.getName() + "has been assigned to " + d_p.getName());
            if (d_player_index < d_GameMap.getPlayers().size() - 1) {     //players get new country
                d_player_index++;
            } else {                                              //New counter given to players, start with player 1
                d_player_index = 0;
            }
            d_country++;
        }

    }


    /**
     * Method to show picked map along with its continents/countries/neighbours/players
     */

    public void showMap() {
        System.out.println("\nDetails of map : \n");

        // Showing Continents in Map
        System.out.println("\nMaps Continents: \n");
        Iterator<Map.Entry<String, Continent>> d_iteratorForContinents = d_GameMap.getContinents().entrySet()
                .iterator();

        String table = "|%-18s|%n";

        System.out.format("+------------------+%n");
        System.out.format("| Name of Continent |%n");
        System.out.format("+------------------+%n");

        while (d_iteratorForContinents.hasNext()) {
            Map.Entry<String, Continent> continentMap = (Map.Entry<String, Continent>) d_iteratorForContinents.next();
            String d_continentId = (String) continentMap.getKey();
            // Use ID to get continent
            Continent d_continent = d_GameMap.getContinents().get(d_continentId);

            System.out.format(table, d_continent.getName());
        }
        System.out.format("+------------------+%n");


        // Details of countries in continent

        System.out.println("\nCountry Details in map : \n");

        Iterator<Map.Entry<String, Continent>> d_iteratorForContinent = d_GameMap.getContinents().entrySet()
                .iterator();

        table = "|%-23s|%-18s|%-60s|%-15s|%n";

        System.out.format(
                "+--------------+-----------------------+------------------+----------------------------+---------------+---------------+%n");
        System.out.format(
                "     Name of Country      | Name of Continent |   Neighbouring Countries                                      | No. of armies |%n");
        System.out.format(
                "+--------------+-----------------------+------------------+----------------------------+---------------+---------------+%n");

        while (d_iteratorForContinent.hasNext()) {
            Map.Entry<String, Continent> d_continentMap = (Map.Entry<String, Continent>) d_iteratorForContinent.next();
            String d_continentId = (String) d_continentMap.getKey();
            // Use ID to get continent
            Continent d_continent = d_GameMap.getContinents().get(d_continentId);
            Iterator<Country> d_listIterator = d_continent.getCountries().iterator();

            while (d_listIterator.hasNext()) {

                Country d_country = (Country) d_listIterator.next();
                System.out.format(table, d_country.getName(), d_continent.getName(), d_country.createANeighborList(d_country.getNeighbors()), d_country.getArmies());
            }
        }

        System.out.format(
                "+--------------+-----------------------+------------------+----------------------------+---------------+---------------+%n");

        // To be modified, players in game

        HashMap<String, Player> d_players = d_GameMap.getPlayers();
        System.out.println("\n\n\n\nGame players at the start of game : ");
        if (d_players != null) {
            d_players.forEach((key, value) -> System.out.println((String) key));
            System.out.println();
        }


        //Shows players property 

        System.out.println("\nOwnership of players: \n");


        System.out.format(
                "+---------------+-----------------------+----------------------------+%n");
        System.out.format(
                "| Name of Player |    Controlling Continents   | Armies Owned |%n");
        System.out.format(
                "+---------------+-----------------------+---------------------------+%n");


        List<Player> d_playerss = d_GameMap.getPlayers().values().stream().collect(Collectors.toList());
        String table1 = "|%-15s|%-30s|%-21d|%n";


        for (Player d_player : d_playerss) {

            System.out.format(table1, d_player.getName(), d_player.buildCapturedCountriesList(d_player.getCapturedCountries()), d_player.getReinforcementArmies());


        }

        System.out.format(
                "+---------------+-----------------------+----------------------------+%n");

    }


}
