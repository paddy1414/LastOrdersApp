package pdesigns.com.lastorders.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import pdesigns.com.lastorders.DTO.Bar;
import pdesigns.com.lastorders.ImageHandlers.loadTheBloodyImage;
import pdesigns.com.lastorders.R;

/**
 * The type Around custom list adapter.
 */
public class AroundCustomListAdapter extends ArrayAdapter<Bar> {

    private final Activity context;
    private final ArrayList<Bar> itemname;


    private loadTheBloodyImage loadTheBloodyImage;

    /**
     * Instantiates a new Around custom list adapter.
     *
     * @param context  the context
     * @param itemname the itemname
     */
    public AroundCustomListAdapter(Activity context, ArrayList<Bar> itemname, boolean answer) {
        super(context, R.layout.each_venue, itemname);
        // TODO Auto-generated constructor stu
        this.context = context;

        this.itemname = itemname;
        if (answer == true) {
            Collections.sort(itemname);
        }


    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.each_venue, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.singleBar);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageFOrItem);
        TextView txtDis = (TextView) rowView.findViewById(R.id.disBox);
        RatingBar ratingBar = (RatingBar) rowView.findViewById(R.id.ratingsbarsingle);

        txtTitle.setText(itemname.get(position).getBarName());
        txtDis.setText(itemname.get(position).getDistanceAway() + "km");
        ratingBar.setRating(Float.parseFloat(itemname.get(position).getAverageRating()));

        loadTheBloodyImage = new loadTheBloodyImage(context);
        loadTheBloodyImage.DisplayImage(itemname.get(position).getBarPic(), imageView);

        return rowView;

    }


}