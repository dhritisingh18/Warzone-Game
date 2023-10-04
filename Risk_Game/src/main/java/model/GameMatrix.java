package model;

import utils.MapValidation;
import utils.SaveMap;
import utils.ValidationException;
import java.util.*;
import java.util.stream.Collectors;

import model.order.Country;
import model.order.Player;


public class GameMatrix {
    private static GameMatrix d_GameMap;
    private HashMap<String, Continent> d_Continents = new HashMap<>();
    private HashMap<String, Country> d_Countries = new HashMap<>();
    private HashMap<String, Player> d_Players = new HashMap<>();
    private String d_Name;
    private String d_ErrorMessage;


    private GameMatrix() {
    }


    public static GameMatrix getInstance() {
        if (Objects.isNull(d_GameMap)) {
            d_GameMap = new GameMatrix();
        }
        return d_GameMap;
    }


    public HashMap<String, Continent> getContinents() {
        return d_Continents;
    }


    public Continent getContinent(String p_Id) {
        return d_Continents.get(p_Id);
    }



    public HashMap<String, Country> getCountries() {
        return d_Countries;
    }



    public Country getCountry(String p_Id) {
        return d_Countries.get(p_Id);
    }



    public HashMap<String, Player> getPlayers() {
        return d_Players;
    }


    public Player getPlayer(String p_Id) {
        return d_Players.get(p_Id);
    }


    public String getErrorMessage() {
        return d_ErrorMessage;
    }


    public void setErrorMessage(String p_ErrorMessage) {
        this.d_ErrorMessage = p_ErrorMessage;
    }


    public String getName() {
        return d_Name;
    }


    public void setName(String p_Name) {
        this.d_Name = p_Name;
    }


    public void clearGameMap() {
        GameMatrix.getInstance().getContinents().clear();
        GameMatrix.getInstance().getCountries().clear();
        GameMatrix.getInstance().getPlayers().clear();
    }


    public void addContinent(String p_ContinentName, String p_ControlValue) throws ValidationException {

        if (this.getContinents().containsKey(p_ContinentName)) {
            throw new ValidationException("There is a Continent that matches with the Continent Entered");
        }
        Continent l_Continent = new Continent();
        l_Continent.setName(p_ContinentName);
        l_Continent.setAwardArmies(Integer.parseInt(p_ControlValue));
        this.getContinents().put(p_ContinentName, l_Continent);
        System.out.println("Continent Successfully added: " + p_ContinentName);
    }


    public void addCountry(String p_CountryName, String p_ContinentName) throws ValidationException {

        if (this.getCountries().containsKey(p_CountryName)) {
            throw new ValidationException("Oops! This Country exists already");
        }
        Country l_Country = new Country();
        l_Country.setName(p_CountryName);
        l_Country.setContinent(p_ContinentName);
        this.getCountries().put(p_CountryName, l_Country);
        this.getContinent(p_ContinentName).getCountries().add(l_Country);
        System.out.println("Country Successfully added: " + p_CountryName);
    }


    public void removeContinent(String p_ContinentName) throws ValidationException {

        if (!this.getContinents().containsKey(p_ContinentName)) {
            throw new ValidationException("There is no such continent");
        }
        Set<String> l_CountrySet = this.getContinents().remove(p_ContinentName)
                .getCountries()
                .stream().map(Country::getName)
                .collect(Collectors.toSet());
        for (String l_CountryName : l_CountrySet) {
            this.getCountries().remove(l_CountryName);
        }
        System.out.println("Kudos! The Continent deleted is: " + p_ContinentName);
    }


    public void removeCountry(String p_CountryName) throws ValidationException {
        Country l_Country = this.getCountry(p_CountryName);
        if (Objects.isNull(l_Country)) {
            throw new ValidationException("No such Country exists: " + p_CountryName);
        }
        this.getContinent(l_Country.getContinent()).getCountries().remove(l_Country);
        this.getCountries().remove(l_Country.getName());
        System.out.println("Country successfully deleted");
    }


    public void addNeighbor(String p_CountryName, String p_NeighborCountryName) throws ValidationException {
        Country l_Country1 = this.getCountry(p_CountryName);
        Country l_Country2 = this.getCountry(p_NeighborCountryName);
        if (Objects.isNull(l_Country1) || Objects.isNull(l_Country2)) {
            throw new ValidationException(" One of the mentioned Countries does not exist");
        }
        l_Country1.getNeighbors().add(l_Country2);
        System.out.printf("Successfully connected routes between mentioned Countries: %s - %s\n", p_CountryName, p_NeighborCountryName);
    }



    public void removeNeighbor(String p_CountryName, String p_NeighborCountryName) throws ValidationException {
        Country l_Country1 = this.getCountry(p_CountryName);
        Country l_Country2 = this.getCountry(p_NeighborCountryName);
        if (Objects.isNull(l_Country1)) {
            throw new ValidationException("Atleast one of the mentioned Countries does not exist");
        } else if (!l_Country1.getNeighbors().contains(l_Country2) || !l_Country2.getNeighbors().contains(l_Country1)) {
            throw new ValidationException("Mentioned Countries are not neighbors");
        } else {
            this.getCountry(p_CountryName).getNeighbors().remove(l_Country2);
            System.out.printf("Successfully removed routes between mentioned Countries: %s - %s\n", p_CountryName, p_NeighborCountryName);
        }
    }


    public void addPlayer(String p_PlayerName) throws ValidationException {
        if (this.getPlayers().containsKey(p_PlayerName)) {
            throw new ValidationException("Player already exists");
        }
        Player l_Player = new Player();
        l_Player.setName(p_PlayerName);
        this.getPlayers().put(p_PlayerName, l_Player);
        System.out.println("Successfully added Player: " + p_PlayerName);
    }

    public void removePlayer(String p_PlayerName) throws ValidationException {
        Player l_Player = this.getPlayer(p_PlayerName);
        if (Objects.isNull(l_Player) || l_Player==null) {
            throw new ValidationException("No such Player exists: " + p_PlayerName);
        }
        this.getPlayers().remove(l_Player.getName());
        System.out.println("Successfully deleted the player: " + p_PlayerName);
    }


    public void saveMap() throws ValidationException {
        //Ask p_size for minimum number of countries based on player
        if (MapValidation.validateMap(d_GameMap, 0)) {
            SaveMap d_SaveMap = new SaveMap();
            boolean bool = true;
            while (bool) {
                if (!Objects.isNull(d_GameMap.getName()) && !d_GameMap.getName().isEmpty()) {
                    if (d_SaveMap.saveMapIntoFile(d_GameMap, d_GameMap.getName())) {
                        System.out.println("The map has been validated and saved.");
                    } else {
                        throw new ValidationException("This Map name already exists, enter a different name.");
                    }
                    bool = false;
                } else {

                    throw new ValidationException("Kindly enter the file name:");

                }
            }
        } else {
            throw new ValidationException("Invalid Map therefore can not be saved.");
        }
    }

    /**
     * Assign countries to each player of the game in random.
     */
    public void assignCountries() {
        int d_player_index = 0;
        List<Player> d_players = d_GameMap.getPlayers().values().stream().collect(Collectors.toList());


        List<Country> d_countryList = d_GameMap.getCountries().values().stream().collect(Collectors.toList());
        ShuffleClass.knuthShuffle(d_countryList);

        int d_country=0;
        while (d_country < d_countryList.size()) {
            Country d_c = d_countryList.get(d_country);                // loop for get each country of the map
            Player d_p = d_players.get(d_player_index);          // finding the corresponding player with the help of the order of the player
            d_p.getCapturedCountries().add(d_c);
            d_c.setPlayer(d_p);
            System.out.println(d_c.getName() + "has been assigned to " + d_p.getName());
            if (d_player_index < d_GameMap.getPlayers().size() - 1) {     //if not all players get a new country in this round
                d_player_index++;
            } else {                                         //if all players get a new counter in this round, start from player 1
                d_player_index = 0;
            }
            d_country++;
        }

    }


    /**
     * A function to display the map chosen, its continents, countries, neighbours,
     * players and their ownership
     */

    public void showMap() {
        System.out.println("\nMap Details Shown : \n");

        // Showing Continents in Map
        System.out.println("\nThe Continents present in this Map are : \n");
        Iterator<Map.Entry<String, Continent>> d_iteratorForContinents = d_GameMap.getContinents().entrySet()
                .iterator();

        String table = "|%-18s|%n";

        System.out.format("+------------------+%n");
        System.out.format("| Continent's name |%n");
        System.out.format("+------------------+%n");

        while (d_iteratorForContinents.hasNext()) {
            Map.Entry<String, Continent> continentMap = (Map.Entry<String, Continent>) d_iteratorForContinents.next();
            String d_continentId = (String) continentMap.getKey();
            Continent d_continent = d_GameMap.getContinents().get(d_continentId); //Get the particular continent by its ID(Name)

            System.out.format(table, d_continent.getName());
        }
        System.out.format("+------------------+%n");


        // Showing Countries in the Continent and their details
        System.out.println("\nThe countries in this Map and their details are : \n");

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
            Continent d_continent = d_GameMap.getContinents().get(d_continentId); // to get the continent by its ID(Name)
            //ListIterator<Country> listIterator = continent.getCountries().listIterator();
            Iterator<Country> d_listIterator = d_continent.getCountries().iterator();

            while (d_listIterator.hasNext()) {

                Country d_country = (Country) d_listIterator.next();
                System.out.format(table, d_country.getName(), d_continent.getName(), d_country.createANeighborList(d_country.getNeighbors()), d_country.getArmies());
            }
        }

        System.out.format(
                "+--------------+-----------------------+------------------+----------------------------+---------------+---------------+%n");

        // Showing the players in game. Have to modify

        HashMap<String, Player> d_players = d_GameMap.getPlayers();
        System.out.println("\n\n\n\nPlayers in this game on the start of game : ");
        if (d_players != null) {
            d_players.forEach((key, value) -> System.out.println((String) key));  // will slightly modify the output after testing with the entire project
            System.out.println();
        }


        //Showing the Ownership of the players
        System.out.println("\nThe Map ownership of the players are : \n");
        //  String table1 = "|%-15s|%-30s|%-21d|%n";

        System.out.format(
                "+---------------+-----------------------+----------------------------+%n");
        System.out.format(
                "| Name of Player |    Controlling Continents   | Armies Owned |%n");
        System.out.format(
                "+---------------+-----------------------+---------------------------+%n");


        List<Player> d_playerss = d_GameMap.getPlayers().values().stream().collect(Collectors.toList());
        String table1 = "|%-15s|%-30s|%-21d|%n";


        for (Player d_player : d_playerss) {

            //Iterator<Country> listIterator = continent.getCountries().iterator();

            System.out.format(table1, d_player.getName(), d_player.buildCapturedCountriesList(d_player.getCapturedCountries()), d_player.getReinforcementArmies());


        }

        System.out.format(
                "+---------------+-----------------------+----------------------------+%n");

    }


}