package com.givenhansco.a20220218_andrewhawkins_whitenycschools.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.givenhansco.a20220218_andrewhawkins_whitenycschools.Activity.MainActivity;
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.R;
import com.givenhansco.a20220218_andrewhawkins_whitenycschools.UIHelper.ViewDialog;

public class School extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View _view;
    ViewDialog viewDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public School() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LOGS.
     */
    // TODO: Rename and change types and number of parameters
    public static School newInstance(String param1, String param2) {
        School fragment = new School();
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
        viewDialog = new ViewDialog(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_school, container, false);
        TextView fileN = (TextView) _view.findViewById(R.id.tvSchoolName);
        if(mParam1!=null) {
            fileN.setText(mParam1);
        }
        else{
            fileN.setText("error loading file");
        }

        Context _context = getActivity().getApplicationContext();
        LinearLayout llh = _view.findViewById(R.id.llhFile);


        llh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get meta data
                ((MainActivity)getActivity()).grabMeta(mParam2);
            }
        });

        // Inflate the layout for this fragment
        return _view;
    }

    public void showCustomLoadingDialog() {

        //..show gif
        viewDialog.showDialog();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //...here i'm waiting 5 seconds before hiding the custom dialog
                //...you can do whenever you want or whenever your work is done
                try {
                    viewDialog.hideDialog();
                }catch (Exception es){}
            }
        }, 5000);
    }
}