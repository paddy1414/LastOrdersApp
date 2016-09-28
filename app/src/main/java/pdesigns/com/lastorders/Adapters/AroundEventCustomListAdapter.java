package pdesigns.com.lastorders.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pdesigns.com.lastorders.DTO.Event;
import pdesigns.com.lastorders.ImageHandlers.loadTheBloodyImage;
import pdesigns.com.lastorders.R;

/**
 * The type Around custom list adapter.
 */
public class AroundEventCustomListAdapter extends ArrayAdapter<Event> {

    private final Activity context;
    private final ArrayList<Event> itemname;


    private loadTheBloodyImage loadTheBloodyImage;

    /**
     * Instantiates a new Around custom list adapter.
     *
     * @param context  the context
     * @param itemname the itemname
     */
    public AroundEventCustomListAdapter(Activity context, ArrayList<Event> itemname) {
        super(context, R.layout.each_event, itemname);
        // TODO Auto-generated constructor stu
        this.context = context;

        this.itemname = itemname;


    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.each_event, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.single_event_title);
        TextView txtDis = (TextView) rowView.findViewById(R.id.event_distance);
        TextView txtStart = (TextView) rowView.findViewById(R.id.event_start);

        txtTitle.setText(itemname.get(position).getTitle());
        txtDis.setText(itemname.get(position).getDistanceAway() + "km");
        txtStart.setText(itemname.get(position).getStartTime());


        return rowView;

    }


}