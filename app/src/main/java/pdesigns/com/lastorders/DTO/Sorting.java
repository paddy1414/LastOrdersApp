package pdesigns.com.lastorders.DTO;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Patrick on 03/05/2016.
 */
public class Sorting {


    public ArrayList<Bar> bubbleSortNumbers(ArrayList<Bar> nums) {

        Bar[] tmps = new Bar[nums.size()];
        for (int i = 0; i < nums.size(); i++) {
            tmps[i] = nums.get(i);

            Log.d("loopingaroundtheplace", tmps[i].getAverageRating());
        }
        for (int x = 0; x < tmps.length; x++) {
            for (int y = 1; y < tmps.length - x; y++) {
                if (Double.parseDouble(tmps[y].getAverageRating()) > Double.parseDouble(tmps[y - 1].getAverageRating())) {
                    Bar temp = tmps[y - 1];
                    tmps[y - 1] = tmps[y];
                    tmps[y] = temp;
                }
            }
        }
        for (int i = 0; i < tmps.length; i++) {
            Log.d("tmps", tmps[i].toString());
        }
        nums = new ArrayList<Bar>(Arrays.asList(tmps));
        return nums;
    }
}
