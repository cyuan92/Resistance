package com.steveandconnie.projects.resistance.screens;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.steveandconnie.projects.resistance.R;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MissionHistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MissionHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MissionHistoryFragment extends Fragment {

    private int currentMission;
    private boolean[] missionHistory;

    static HashMap<Integer, Integer> missionNumToMissionBtnId = new HashMap<Integer, Integer>();
    static {
        missionNumToMissionBtnId.put(1, R.id.mission1Btn);
        missionNumToMissionBtnId.put(2, R.id.mission2Btn);
        missionNumToMissionBtnId.put(3, R.id.mission3Btn);
        missionNumToMissionBtnId.put(4, R.id.mission4Btn);
        missionNumToMissionBtnId.put(5, R.id.mission5Btn);
    }


        private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static MissionHistoryFragment newInstance(int currentMission, boolean[] missionHistory) {
        MissionHistoryFragment fragment = new MissionHistoryFragment();
        Bundle args = new Bundle();
        args.putInt("currentMission", currentMission);
        args.putBooleanArray("missionHistory", missionHistory);
        fragment.setArguments(args);
        return fragment;
    }

    public MissionHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            this.currentMission = args.getInt("currentMission");
            this.missionHistory = args.getBooleanArray("missionHistory");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rtnView = inflater.inflate(R.layout.fragment_mission_history, container, false);
        Log.d("currentMission", currentMission + "");

        for(int i=1; i<=currentMission; i++) {
            int id = missionNumToMissionBtnId.get(i);
            Log.d("id", id + "");
            View missionBtn = rtnView.findViewById(id);
            if (i == currentMission) {
                missionBtn.setBackgroundResource(R.drawable.current_button);
            } else {
                if (missionHistory[i-1]) {
//                missionBtn.setBackgroundResource(R.color.pass);
                    missionBtn.setBackgroundResource(R.drawable.pass_button);
                } else {
//                missionBtn.setBackgroundResource(R.color.fail);
                    missionBtn.setBackgroundResource(R.drawable.fail_button);
                }
            }

        }

        return rtnView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        public void onFragmentInteraction(Uri uri);
    }

}
