package utils;

import model.GameMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class MapReader {

    public static void readMap(GameMatrix p_GameMap, String p_FileName) throws ValidationException {
        try {
            p_GameMap.clearGameMap();
            File l_File = new File("maps/" + p_FileName);
            FileReader l_FileReader = new FileReader(l_File);
            Map<String, List<String>> l_MapFileContents = new HashMap<>();
            String l_CurrentKey = "";
            BufferedReader l_Buffer = new BufferedReader(l_FileReader);
            while (l_Buffer.ready()) {
                String l_Read = l_Buffer.readLine();
                if (!l_Read.isEmpty()) {
                    if (l_Read.contains("[") && l_Read.contains("]")) {
                        l_CurrentKey = l_Read.replace("[", "").replace("]", "");
                        l_MapFileContents.put(l_CurrentKey, new ArrayList<>());
                    } else {
                        l_MapFileContents.get(l_CurrentKey).add(l_Read);
                    }
                }
            }
            readContinentsFromFile(p_GameMap, l_MapFileContents.get("Continents"));
            Map<String,ArrayList<String>> countryMap=new HashMap<>();

            for(String country : l_MapFileContents.get("countries")){
                String[] countryArr=country.split(" ");
                ArrayList<String> list=new ArrayList<>();
                for(String s : countryArr){
                    list.add(s);
                }
                countryMap.put(countryArr[0],list);
            }

            Map<String,ArrayList<String>> borderMap=new HashMap<>();
            for(String border : l_MapFileContents.get("borders")){
                String[] borderArr=border.split(" ");
                ArrayList<String> list=new ArrayList<>();
                for(String s : borderArr){
                    list.add(s);
                }
                borderMap.put(borderArr[0],list);
            }

            int continentIndex=1;
            Map<String,ArrayList<String>> continentMap=new HashMap<>();
            for(String continent : l_MapFileContents.get("Continents")){
                String[] continentArr=continent.split(" ");
                ArrayList<String> list=new ArrayList<>();
                for(String s : continentArr){
                    list.add(s);
                }
                continentMap.put(Integer.toString(continentIndex),list);
                continentIndex++;
            }


            l_MapFileContents.put("Territories",new ArrayList<>());
            for(String countryId: borderMap.keySet()){
                String territoryEntry=   countryMap.get(countryId).get(1)+" "+continentMap.get(countryMap.get(countryId).get(2)).get(0)+" ";
                for(int neighbourIndex=1; neighbourIndex<borderMap.get(countryId).size();neighbourIndex++){
                    String neighbourName=countryMap.get(borderMap.get(countryId).get(neighbourIndex)).get(1);
                    if(neighbourIndex!=borderMap.size()-1){
                        territoryEntry+=neighbourName+" ";
                    }
                    else{
                        territoryEntry+=neighbourName;
                    }
                }
                l_MapFileContents.get("Territories").add(territoryEntry);
            }
            Map<String, List<String>> l_CountryNeighbors = readCountriesFromFile(p_GameMap, l_MapFileContents.get("Territories"));
            addNeighborsFromFile(p_GameMap, l_CountryNeighbors);
        } catch (ValidationException | IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }


    public static void readContinentsFromFile(GameMatrix p_GameMap, List<String> p_ContinentArray) throws ValidationException {
        for (String l_InputString : p_ContinentArray) {
            String[] l_InputArray = l_InputString.split(" ");
            if (l_InputArray.length == 2) {
                p_GameMap.addContinent(l_InputArray[0], l_InputArray[1]);
            }
        }
    }


    public static Map<String, List<String>> readCountriesFromFile(GameMatrix p_GameMap, List<String> p_CountryArray) throws ValidationException {
        Map<String, List<String>> l_CountryNeighbors = new HashMap<>();
        for (String l_InputString : p_CountryArray) {
            List<String> l_InputArray = Arrays.stream(l_InputString.split(" ")).collect(Collectors.toList());
            if (l_InputArray.size() >= 2) {
                p_GameMap.addCountry(l_InputArray.get(0), l_InputArray.get(1));
                l_CountryNeighbors.put(l_InputArray.get(0), l_InputArray.subList(2, l_InputArray.size()));
            }
        }
        return l_CountryNeighbors;
    }

    public static void addNeighborsFromFile(GameMatrix p_GameMap, Map<String, List<String>> p_NeighborList) throws ValidationException {
        for (String l_Country : p_NeighborList.keySet()) {
            for (String l_Neighbor : p_NeighborList.get(l_Country)) {
                p_GameMap.addNeighbor(l_Country, l_Neighbor);
            }
        }
    }
}