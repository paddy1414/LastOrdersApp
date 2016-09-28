package pdesigns.com.lastorders.ClientSide;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.HashMap;

import pdesigns.com.lastorders.DTO.User;
import pdesigns.com.lastorders.ImageHandlers.ImageCache;
import pdesigns.com.lastorders.ImageHandlers.ImageFetcher;
import pdesigns.com.lastorders.ImageHandlers.loadTheBloodyImage;
import pdesigns.com.lastorders.R;
import pdesigns.com.lastorders.provider.SessionManager;

/**
 * The type Profile fragement.
 */
public class ProfileFragement extends Fragment {

    private static final String TAG_FBPAGE = "barFBPage";
    private static final String IMAGE_CACHE_DIR = "thumbs";
    /**
     * The Bars.
     */
// images JSON Array
    JSONArray bars = null;

    /**
     * The User o.
     */
//   Bar bar;
    User userO;
    /**
     * The Session.
     */
// Session Manager Class
    SessionManager session;
    private ProgressDialog myDialog;
    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private ImageFetcher mImageFetcher;
    private loadTheBloodyImage loadTheBloodyImage;

    //  Bar b ;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Session Manager
        session = new SessionManager(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_around_you, parent, false);

        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);


        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);

        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
        mImageFetcher.setLoadingImage(R.drawable.no);

        mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);


        View rowView = inflater.inflate(R.layout.fragment_profile, null, true);


        TextView txtId = (TextView) rowView.findViewById(R.id.special_id);

        TextView txtFname = (TextView) rowView.findViewById(R.id.fname);

        TextView txtlName = (TextView) rowView.findViewById(R.id.lname);

        TextView txtName = (TextView) rowView.findViewById(R.id.username1);

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        txtId.setText(user.get(SessionManager.KEY_ID));
        txtFname.setText(user.get(SessionManager.KEY_FNAME));
        txtlName.setText(user.get(SessionManager.KEY_LNAME));
        txtName.setText(user.get(SessionManager.KEY_EMAIL));


        return rowView;

    }


}
