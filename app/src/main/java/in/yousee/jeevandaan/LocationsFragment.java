package in.yousee.jeevandaan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import in.yousee.jeevandaan.util.LogUtil;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationsFragment extends SupportMapFragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private GoogleMap mMap;
    private MapView mapView;


    private OnFragmentInteractionListener mListener;

    public LocationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationsFragment newInstance(String param1, String param2) {
        LocationsFragment fragment = new LocationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_locations, container, false);
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return  view;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        //mapView.onStart
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng ananthapur = new LatLng(14.6712833, 77.5960872);
        mMap.addMarker(new MarkerOptions().position(ananthapur).title("Government General Hospital  Ananthapur"));

        LatLng guntur = new LatLng(16.2997797, 80.4434242);
        mMap.addMarker(new MarkerOptions().position(guntur).title("Government General Hospital Guntur"));

        LatLng kakinada = new LatLng(16.956221, 82.2270345);
        mMap.addMarker(new MarkerOptions().position(kakinada).title("Government General Hospital Kakinada"));

        LatLng kurnool = new LatLng(15.8206345, 78.0379509);
        mMap.addMarker(new MarkerOptions().position(kurnool ).title("Government General Hospital Kurnool"));

        LatLng vijayawada = new LatLng(16.5131537, 80.6188823);
        mMap.addMarker(new MarkerOptions().position(vijayawada ).title("Government General Hospital Vijayawada "));

        LatLng visakhapatnam = new LatLng(17.7066865, 83.3031901);
        mMap.addMarker(new MarkerOptions().position(visakhapatnam).title("King George Hospital Visakhapatnam"));

        LatLng kadapa = new LatLng(14.4331925, 78.8639711);
        mMap.addMarker(new MarkerOptions().position(kadapa ).title("RIMS Kadapa"));

        LatLng ongole = new LatLng(15.4879182, 80.046731);
        mMap.addMarker(new MarkerOptions().position(ongole ).title("RIMS Ongole"));

        LatLng srikakulam  = new LatLng(18.3148669, 83.8941662);
        mMap.addMarker(new MarkerOptions().position(srikakulam ).title("RIMS Srikakulam"));

        LatLng tirupathi  = new LatLng(13.6427232, 79.4068301);
        mMap.addMarker(new MarkerOptions().position(tirupathi ).title("SVRR Govenrment General Hospital Tirupathi"));

        LogUtil.print("adding markers");
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(kurnool)      // Sets the center of the map to Mountain View
                .zoom(5)                   // Sets the zoom
                //.bearing(90)                // Sets the orientation of the camera to east
                //.tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
