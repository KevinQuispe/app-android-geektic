package com.example.pcquispe.api.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pcquispe.api.R;
public class DetalleEditFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //DECLARACION DE VARIABLES
    EditText txt_titulo,txt_body;
    public DetalleEditFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DetalleEditFragment newInstance(String param1, String param2) {
        DetalleEditFragment fragment = new DetalleEditFragment();
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
        View view= inflater.inflate(R.layout.fragment_detalle_edit, container, false);
        txt_titulo=(EditText) view.findViewById(R.id.txt_tituloedit);
        txt_body=(EditText) view.findViewById(R.id.txt_bodyedit);
        txt_titulo.setText(getArguments().getString("nombreKey"));
        txt_body.setText(getArguments().getString("bodyKey"));
        return view;
    }

}