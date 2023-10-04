package utils;

import model.Country;
import model.GameMatrix;

import java.io.PrintWriter;
import java.util.Set;

/**
 * This class is used to save map as a text file
 *
 * @author Dhriti Singh
 */
public class SaveMap {

    private GameMatrix gameMap;

    public String createANeighborList(Set<Country> p_Neighbors) {

        StringBuilder l_result = new StringBuilder("");
        for (Country l_Neighbor : p_Neighbors) {
            l_result.append(l_Neighbor.getName());
            l_result.append(" ");
        }
        if (l_result.length() > 0) {
            return l_result.substring(0, l_result.length() - 1);
        }
        return "";
    }

    public boolean saveMapIntoFile(GameMatrix p_GameMap, String name) {
        try (PrintWriter writeData = new PrintWriter("maps/" + name + ".map")) {
            StringBuilder mapDataBuilder = new StringBuilder();

            mapDataBuilder.append("[The Map]\n\n[Continents Involved]\n");
            p_GameMap.getContinents().values().forEach(continent -> {
                mapDataBuilder.append(continent.getName()).append(" ").append(continent.getAwardArmies()).append("\n");
            });

            mapDataBuilder.append("[Territories]\n");

            p_GameMap.getContinents().values().forEach(continent -> {
                p_GameMap.getCountries().values().forEach(country -> {
                    mapDataBuilder.append(country.getName()).append(" ").append(country.getContinent()).append(" ")
                            .append(createANeighborList(country.getNeighbors())).append("\n");
                });

            });

            writeData.println(mapDataBuilder.toString());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
