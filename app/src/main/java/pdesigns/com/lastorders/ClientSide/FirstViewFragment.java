package pdesigns.com.lastorders.ClientSide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import pdesigns.com.lastorders.R;
import pdesigns.com.lastorders.provider.SessionManager;

/**
 * The type First view fragment.
 */
public class FirstViewFragment extends ListFragment implements AdapterView.OnItemClickListener {
    // Session Manager Class
    SessionManager session;
    private int items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_view_list, container, false);

        session = new SessionManager(this.getContext());
        session.checkLogin();


        // Session class instance
        session = new SessionManager(this.getContext());
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Items, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getActivity(), "Item: " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
        String item = adapterView.getItemAtPosition(i).toString();
        if (l == 0) {

            Intent i1 = new Intent(this.getContext(), ProfileActivity.class);
            startActivity(i1);
        } else if (l == 1) {
            Intent intent = new Intent(this.getContext(), MainActivity.class);
            startActivity(intent);


        } else if (l == 2) {
            Intent intent = new Intent(this.getContext(), MainEvent.class);
            startActivity(intent);
        } else if (l == 3) {
            session.logoutUser();
        }


    }
}
