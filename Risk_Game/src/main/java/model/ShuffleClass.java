package model;
import java.util.List;
import java.util.Random;

/**
 * Class with shuffle method implementation
 * @author Dhriti Singh
 */


public class ShuffleClass {
    /**
     * A function to implement shuffle operation and use of swapping as well for the same.
     */
    public static <T> void knuthShuffle(List<T> list) {
        int n = list.size();
        Random random = new Random();

        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            swap(list, i, j);
        }
    }

    private static <T> void swap(List<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}


