package Strategy;

import org.inlaming3.PlantBase;

import java.util.ArrayList;
import java.util.List;

public class MergeSortStrategy implements ISortStrategy{
    @Override
    public void sort(List<PlantBase> plants) {
        if(plants.size() < 2){
            return;
        }
        int mid = plants.size() / 2;
        List<PlantBase> leftHalf = new ArrayList<>(plants.subList(0, mid));
        List<PlantBase> rightHalf = new ArrayList<>(plants.subList(mid, plants.size()));
        sort(leftHalf);
        sort(rightHalf);

        merge(plants, leftHalf, rightHalf);
    }
    private void merge(List<PlantBase> plants, List<PlantBase> leftHalf, List<PlantBase> rightHalf){
        int i = 0, j = 0, k = 0;
        while(i < leftHalf.size() && j < rightHalf.size()){
            if(leftHalf.get(i).getSortKey().compareTo(rightHalf.get(j).getSortKey()) <= 0){
                plants.set(k++, leftHalf.get(i++));
            }else{
                plants.set(k++, rightHalf.get(j++));
            }
        }
        while(i < leftHalf.size()){
            plants.set(k++, leftHalf.get(i++));
        }
        while(j < rightHalf.size()){
            plants.set(k++, rightHalf.get(j++));
        }
    }
}
