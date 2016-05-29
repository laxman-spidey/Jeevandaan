package in.yousee.jeevandaan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import in.yousee.jeevandaan.model.CustomException;
import in.yousee.jeevandaan.model.Donation;
import in.yousee.jeevandaan.model.SummaryModel;
import in.yousee.jeevandaan.util.LogUtil;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SummaryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SummaryFragment extends Fragment implements OnResponseRecievedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SummaryFragment() {
        // Required empty public constructor
    }

    private Context context;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SummaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SummaryFragment newInstance(Context context) {
        SummaryFragment fragment = new SummaryFragment();

        Bundle args = new Bundle();
        //args.
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    SummaryMiddleware summaryMiddleware;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


    }
    public void sendRequest()
    {
        try {
            summaryMiddleware.sendRequest();
        } catch (CustomException e)
        {

        }
    }
    private ListView listView;
    private ListAdapter listAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        listView = (ListView) view.findViewById(R.id.donationlistview);
        SummaryModel model = createDumpData();
        listAdapter = new ListAdapter(getContext(),R.layout.donation_row,model.donations);



        listView.setAdapter(listAdapter);

        return view;
    }

    public SummaryModel createDumpData()
    {

        SummaryModel summary = new SummaryModel(null);
        summary.bloodGroup = "O+";
        summary.donorName = "Captain America";
        summary.location = "Brroklyn";
        Donation dontation1 = new Donation(summary.location);
        dontation1.bloodBankName = "SHEILD BloodBank";
        dontation1.date = "2016-05-24";
        //dontation1.unitsOfBlood = 4;
        dontation1.location = "Brooklyn";
        summary.addDonation(dontation1);
        return  summary;

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
            summaryMiddleware = new SummaryMiddleware(this);
            summaryMiddleware.assembleRequest();
            sendRequest();
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

    @Override
    public void onResponseRecieved(Object response, int requestCode) {
        LogUtil.print("ksjsfkjhsd");
        SummaryModel model = (SummaryModel) response;
        ArrayList<Donation> donations = model.donations;
        listAdapter.addAll(donations);
        MainActivity activity = (MainActivity)getActivity();
        activity.onResponseRecieved(response,requestCode);
    }



    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
        //return App.getContext();
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
