package pdesigns.com.lastorders.Adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pdesigns.com.lastorders.DTO.Comments;
import pdesigns.com.lastorders.R;

/**
 * Created by Patrick on 01/04/2016.
 */
public class CommentCustomListAdapter extends ArrayAdapter<Comments> {

    private final Activity context;
    private final ArrayList<Comments> commentsArrayList;
    //private final ArrayList<String> comment;
    // private final ArrayList<String> username;


    public CommentCustomListAdapter(Activity context, ArrayList<Comments> itemList) {
        super(context, R.layout.fragment_each_comment, itemList);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.commentsArrayList = itemList;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.fragment_each_comment, null, true);

        TextView txtUName = (TextView) rowView.findViewById(R.id.usernamecomment);
        TextView txtComment = (TextView) rowView.findViewById(R.id.usernamecommenttext);
        txtUName.setTypeface(null, Typeface.BOLD);
        txtUName.setText(commentsArrayList.get(position).getFname() + " " + commentsArrayList.get(position).getLname());
        txtComment.setText(commentsArrayList.get(position).getCommentText());

        return rowView;

    }
}
