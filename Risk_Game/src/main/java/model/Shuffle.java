package model;
import java.util.List;
import java.util.Random;

public class ShuffleClass {
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
