package utils;

import model.Continent;
import model.Country;
import model.GameMatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * This class is used to save map as a text file and to check if the map borders are connected or not.
 * It also checks for countries without any connected neighbours.
 *
 * @author Dhriti Singh
 */

public class MapValidation {

    /**
     * Method to check continent status.
     *
     * @param p_GameMap Obj GameMap has all data
     * @return true if empty continent else false
     */

    public static boolean checkEmptyContinents(GameMatrix p_GameMap) {
        if (p_GameMap.getContinents().isEmpty()) {
            return true;
        }

        for (Continent continent : p_GameMap.getContinents().values()) {
            if (continent.getCountries().isEmpty()) {
                System.out.println("Continent " + continent.getName() + " does not have any countries");
                return true;
            }
        }


        return false;
    }

    /**
     * Method to check if country count meets requirement
     *
     * @param p_GameMap      GameMap obj has all data
     * @param p_CountryCount Min country requirement
     * @return returns true if country count equal or more than min req if not false
     */

    public static boolean checkCountOfCountry(GameMatrix p_GameMap, int p_CountryCount) {
        if (p_GameMap.getCountries().size() < p_CountryCount) {
            System.out.println("No of countries are less than what meets requirement" + p_CountryCount + ", Function Invalid.");
            return false;
        }
        return true;
    }

    /**
     * Method to check for neighbor in list of countries
     *
     * @param p_GameMap GameMap obj has all data
     * @return will return true if neighbour in country list otherwise false
     */


    private static boolean checkExistenceOfNeighbour(GameMatrix p_GameMap) {
        Map<String, Country> l_Countries = p_GameMap.getCountries();
        Set<String> l_ListOfCountries = new HashSet<>(l_Countries.keySet());

        for (Continent l_Continent : p_GameMap.getContinents().values()) {
            for (Country l_Country : l_Continent.getCountries()) {
                for (Country l_Neighbour : l_Country.getNeighbors()) {
                    if (!l_ListOfCountries.contains(l_Neighbour.getName().toLowerCase())) {
                        System.out.println("Neighbor not found in the countries list - " + l_Neighbour.getName() + ":neighbour");
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Method to check for copies of continents
     *
     * @param p_GameMap GameMap obj has all data
     * @return true if continents copies found otherwise false
     */

    public static boolean checkDuplicateContinents(GameMatrix p_GameMap) {
        HashMap<String, Continent> continentNames = p_GameMap.getContinents();
        HashSet<String> uniqueContinentNames = new HashSet<>(continentNames.keySet());

        if (uniqueContinentNames.size() < continentNames.size()) {
            System.out.println("There is presence of duplicate continents in map.");
            return true;
        }

        return false;
    }

    /**
     * Method to check for copies of countries
     *
     * @param p_GameMap GameMap obj has all data
     * @return true if continent copies found else false
     */


    public static boolean checkDuplicateCountries(GameMatrix p_GameMap) {
        HashMap<String, Country> p_CountryNames = p_GameMap.getCountries();
        HashSet<String> p_Set = new HashSet<>(p_CountryNames.keySet());

        if (p_Set.size() < p_CountryNames.size()) {
            System.out.println("There is presence of duplicate countries in map.");
            return true;
        }

        return false;
    }

    /**
     * Method to check for copies of Neighbors
     *
     * @param p_GameMap GameMap obj has all data
     * @return true if neighbour copies found else false
     */

    public static boolean checkDuplicateNeighbours(GameMatrix p_GameMap) {
        for (Continent l_Continent : p_GameMap.getContinents().values()) {
            for (Country l_Country : l_Continent.getCountries()) {
                Set<Country> l_Neighbours = l_Country.getNeighbors();

                if (l_Neighbours.contains(l_Country)) {
                    System.out.println("There is presence of duplicate neighbours in map.");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method to check Map validitiy
     *
     * @param p_GameMap GameMap obj has all data
     * @param p_Size    Minimum country required to play
     * @return true if conditions are met else false
     */


    public static boolean validateMap(GameMatrix p_GameMap, int p_Size) {
        if (checkIfMapIsInvalid(p_GameMap, p_Size)) {
            return false;
        }

        if (checkIfMapIsConnected(p_GameMap)) {
            return true;
        }

        return false;
    }

    /**
     * Method to check Map invalid
     *
     * @param p_GameMap GameMap obj has all data
     * @param p_Size    Minimum country requirements to play
     * @return true if conditions are met else false
     */

    private static boolean checkIfMapIsInvalid(GameMatrix p_GameMap, int p_Size) {
        return checkEmptyContinents(p_GameMap) ||
                checkDuplicateContinents(p_GameMap) ||
                checkDuplicateCountries(p_GameMap) ||
                !checkCountOfCountry(p_GameMap, p_Size) ||
                checkDuplicateNeighbours(p_GameMap) ||
                (checkExistenceOfNeighbour(p_GameMap) && !checkIfContinentIsConnected(p_GameMap));
    }

    /**
     * Check connectivity of graph
     */

    static class ConnectedGraph {
        private int d_Vertices;
        private ArrayList[] d_Edges;

        ConnectedGraph(int val) {
            this.d_Vertices = val;
            d_Edges = new ArrayList[val];
            for (int l_Index = 0; l_Index < val; ++l_Index) {
                d_Edges[l_Index] = new ArrayList();
            }
        }

        /**
         * Method to add directions b/w nodes
         *
         * @param p_Egde1 first vertex
         * @param p_Edge2 second vertex
         */


        void addEdge(int p_Egde1, int p_Edge2) {
            d_Edges[p_Egde1].add(p_Edge2);
        }

        /**
         * Method for DFS Traversal
         *
         * @param p_Node    Starting node for DFS Traversal
         * @param p_Visited Boolean value array for node visied check
         */


        private void dfsTraversal(int p_Node, Boolean[] p_Visited) {
            Stack<Integer> stack = new Stack<>();
            stack.push(p_Node);

            while (!stack.isEmpty()) {
                int currentNode = stack.pop();
                p_Visited[currentNode] = true;

                for (Integer neighbor : (Iterable<Integer>) d_Edges[currentNode]) {
                    if (!p_Visited[neighbor]) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        /**
         * Method to get graph Transpose
         *
         * @return graph revered
         */


        private ConnectedGraph getTranspose() {
            ConnectedGraph l_Graph = new ConnectedGraph(d_Vertices);

            for (int l_Vertex = 0; l_Vertex < d_Vertices; l_Vertex++) {
                for (Integer integer : (Iterable<Integer>) d_Edges[l_Vertex]) {
                    l_Graph.d_Edges[integer].add(l_Vertex);
                }
            }

            return l_Graph;
        }

        /**
         * Method to check graph connection strength
         *
         * @return true if connected else false
         */


        Boolean checkIfGraphIsStronglyConnected() {
            Boolean[] l_Visited = new Boolean[d_Vertices];

            for (int l_Index = 0; l_Index < d_Vertices; l_Index++) {
                l_Visited[l_Index] = false;
            }

            dfsTraversal(0, l_Visited);

            for (int l_Index = 0; l_Index < d_Vertices; l_Index++) {
                if (!l_Visited[l_Index]) {
                    return false;
                }
            }

            ConnectedGraph l_Graph = getTranspose();

            for (int l_Index = 0; l_Index < d_Vertices; l_Index++) {
                l_Visited[l_Index] = false;
            }

            l_Graph.dfsTraversal(0, l_Visited);

            for (int i = 0; i < d_Vertices; i++) {
                if (!l_Visited[i]) {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * Method to see the continent is connected through graph
     *
     * @param gameMap GameMap obj has all data
     * @return true if connected else false
     */


    public static boolean checkIfContinentIsConnected(GameMatrix gameMap) {
        Set<String> countryNames = gameMap.getCountries().keySet();

        List<String> listOfCountries = new ArrayList<>();
        for (String name : countryNames) {
            listOfCountries.add(name.toLowerCase());
        }

        int numVertices = listOfCountries.size();

        ConnectedGraph graph = new ConnectedGraph(numVertices);

        for (int vertex = 0; vertex < numVertices; vertex++) {
            int finalVertex = vertex;
            gameMap.getContinents().values().stream()
                    .flatMap(continent -> continent.getCountries().stream())
                    .filter(country -> listOfCountries.contains(country.getName().toLowerCase()))
                    .forEach(country -> {
                        Set<Country> neighbors = country.getNeighbors();
                        neighbors.stream()
                                .map(neighbor -> listOfCountries.indexOf(neighbor.getName().toLowerCase()))
                                .forEach(index -> graph.addEdge(finalVertex, index));
                    });

        }

        return checkMapConnectivity(graph);
    }

    /**
     * Method to see if the Whole Map is connected graph
     *
     * @param gameMap GameMap obj has all data
     * @return true for connected else false
     */


    public static boolean checkIfMapIsConnected(GameMatrix gameMap) {
        Set<String> countryNames = gameMap.getCountries().keySet();
        List<String> listOfCountries = new ArrayList<>(countryNames.size());

        for (String name : countryNames) {
            listOfCountries.add(name.toLowerCase());
        }

        int numVertices = listOfCountries.size();
        ConnectedGraph graph = new ConnectedGraph(numVertices);

        IntStream.range(0, numVertices)
                .forEach(vertex -> gameMap.getCountries().values().forEach(country -> {
                    Set<Country> neighbors = country.getNeighbors();
                    neighbors.stream()
                            .map(neighbor -> listOfCountries.indexOf(neighbor.getName().toLowerCase()))
                            .forEach(index -> graph.addEdge(vertex, index));
                }));

        return checkMapConnectivity(graph);
    }

    /**
     * Method to see connectivity with graph
     *
     * @param p_Graph Connected class obj checks connected/not connected
     * @return true for connected else false
     */

    private static boolean checkMapConnectivity(ConnectedGraph p_Graph) {
        return p_Graph.checkIfGraphIsStronglyConnected();
    }
}
