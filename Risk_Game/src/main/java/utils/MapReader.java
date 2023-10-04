package utils;

import model.GameMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is used to save map as a text file
 *
 * @author Dhriti Singh
 */
public class MapReader {

    /**
     * A function to read the map.
     *
     * @param p_GameMatrix The GameMatrix object which contains all the data
     * @param p_FileName   The data in file
     */
    public static void readMap(GameMatrix p_GameMatrix, String p_FileName) throws ValidationFailure {
        try {
            p_GameMatrix.clearGameMap();
            File l_File = new File("maps/" + p_FileName);
            FileReader l_FileReader = new FileReader(l_File);
            Map<String, List<String>> l_MapFileContents = new HashMap<>();
            String l_CurrentKey = "";
            BufferedReader l_Buffer = new BufferedReader(l_FileReader);
            while (l_Buffer.ready()) {
                String l_Read = l_Buffer.readLine();
                if (l_Read.isEmpty()) {
                    System.out.println("");
                } else {
                    if (l_Read.contains("[") && l_Read.contains("]")) {
                        if (l_Read.equals("[continents]")) {
                            l_CurrentKey = "Continents";
                        } else {
                            l_CurrentKey = l_Read.replace("[", "").replace("]", "");
                        }


                        l_MapFileContents.put(l_CurrentKey, new ArrayList<>());
                    } else {
                        l_MapFileContents.get(l_CurrentKey).add(l_Read);
                    }
                }
            }
            readContinentsFromFile(p_GameMatrix, l_MapFileContents.get("Continents"));
            Map<String, ArrayList<String>> countryMap = new HashMap<>();

            for (String country : l_MapFileContents.get("countries")) {
                String[] countryArr = country.split(" ");
                ArrayList<String> list = new ArrayList<>();
                for (String s : countryArr) {
                    list.add(s);
                }
                countryMap.put(countryArr[0], list);
            }

            Map<String, ArrayList<String>> borderMap = new HashMap<>();
            for (String border : l_MapFileContents.get("borders")) {
                String[] borderArr = border.split(" ");
                ArrayList<String> list = new ArrayList<>();
                for (String s : borderArr) {
                    list.add(s);
                }
                borderMap.put(borderArr[0], list);
            }

            int continentIndex = 1;
            Map<String, ArrayList<String>> continentMap = new HashMap<>();
            for (String continent : l_MapFileContents.get("Continents")) {
                String[] continentArr = continent.split(" ");
                ArrayList<String> list = new ArrayList<>();
                for (String s : continentArr) {
                    list.add(s);
                }
                continentMap.put(Integer.toString(continentIndex), list);
                continentIndex++;
            }


            l_MapFileContents.put("Territories", new ArrayList<>());
            for (String countryId : borderMap.keySet()) {
                String territoryEntry = countryMap.get(countryId).get(1) + " " + continentMap.get(countryMap.get(countryId).get(2)).get(0) + " ";
                for (int neighbourIndex = 1; neighbourIndex < borderMap.get(countryId).size(); neighbourIndex++) {
                    String neighbourName = countryMap.get(borderMap.get(countryId).get(neighbourIndex)).get(1);
                    if (neighbourIndex != borderMap.size() - 1) {
                        territoryEntry += neighbourName + " ";
                    } else {
                        territoryEntry += neighbourName;
                    }
                }
                l_MapFileContents.get("Territories").add(territoryEntry);
            }
            Map<String, List<String>> l_CountryNeighbors = readCountriesFromFile(p_GameMatrix, l_MapFileContents.get("Territories"));
            addNeighborsFromFile(p_GameMatrix, l_CountryNeighbors);
        } catch (ValidationFailure | IOException e) {
            throw new ValidationFailure(e.getMessage());
        }
    }

    /**
     * A function to read the continents from file.
     *
     * @param p_GameMatrix     The GameMatrix object which contains all the data
     * @param p_ContinentArray The array of strings containing continents
     */
    public static void readContinentsFromFile(GameMatrix p_GameMatrix, List<String> p_ContinentArray) throws ValidationFailure {
        p_ContinentArray.stream()
                .map(l_InputString -> l_InputString.split(" "))
                .filter(l_InputArray -> l_InputArray.length == 2)
                .forEach(l_InputArray -> {
                    try {
                        p_GameMatrix.addContinent(l_InputArray[0], l_InputArray[1]);
                    } catch (ValidationFailure e) {
                        e.printStackTrace();
                    }

                });
    }

    /**
     * A function to read the countries from file.
     *
     * @param p_GameMatrix   The GameMatrix object which contains all the data
     * @param p_CountryArray The array of strings containing countries
     */
    public static Map<String, List<String>> readCountriesFromFile(GameMatrix p_GameMatrix, List<String> p_CountryArray) throws ValidationFailure {
        Map<String, List<String>> l_CountryNeighbors = new HashMap<>();
        return p_CountryArray.stream()
                .map(l_InputString -> Arrays.asList(l_InputString.split(" ")))
                .filter(l_InputArray -> l_InputArray.size() >= 2)
                .peek(l_InputArray -> {
                    String countryName = l_InputArray.get(0);
                    String continentName = l_InputArray.get(1);

                    try {
                        p_GameMatrix.addCountry(countryName, continentName);
                    } catch (ValidationFailure e) {
                        e.printStackTrace();
                    }

                    List<String> neighbors = l_InputArray.subList(2, l_InputArray.size());
                    l_CountryNeighbors.put(countryName, neighbors);
                })
                .collect(Collectors.toMap(
                        l_InputArray -> l_InputArray.get(0),
                        l_InputArray -> l_InputArray.subList(2, l_InputArray.size())
                ));
    }

    /**
     * A function to add neighbours.
     *
     * @param p_GameMatrix The GameMatrix object which contains all the data
     */
    public static void addNeighborsFromFile(GameMatrix p_GameMatrix, Map<String, List<String>> p_NeighborList) throws ValidationFailure {

        for (String l_Country : p_NeighborList.keySet()) {
            for (String l_Neighbor : p_NeighborList.get(l_Country)) {
                p_GameMatrix.addNeighbor(l_Country, l_Neighbor);
            }
        }


    }
}
