package Strategy;

import org.inlaming3.PlantBase;

import java.util.Collections;
import java.util.List;
public class QuickSortStrategy implements ISortStrategy {
   @Override
    public void sort(List<PlantBase> plants) {
        quickSort(plants, 0, plants.size() - 1);

    }
    private void quickSort(List<PlantBase> plants, int low, int high) {
        if (low < high) {
            int pi = partition(plants, low, high);
            quickSort(plants, low, pi -1);
            quickSort(plants, pi + 1, high);
        }
    }
    private int partition(List<PlantBase> plants, int low, int high){
        PlantBase pivot = plants.get(high);
        int i = (low -1);
        for(int j = low; j < high; j++){
            if(plants.get(j).getName().compareTo(pivot.getName()) <= 0){
                i++;
                Collections.swap(plants, i, j);
            }
        }
        Collections.swap(plants, i + 1, high);
        return i + 1;
    }
}
