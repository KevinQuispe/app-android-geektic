package com.example.pcquispe.api.fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pcquispe.api.MetodoPostActivity;
import com.example.pcquispe.api.R;
import com.example.pcquispe.api.entidades.Post;
import com.example.pcquispe.api.http.Api;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //declare variables
    EditText txt_titulo,txt_body;
    ProgressDialog progreso,progressDoalog;
    Button btn_register;
    TextView txt_titulo1,txt_body1;

    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        txt_titulo=(EditText) view.findViewById(R.id.txt_titulo);
        txt_body=(EditText) view.findViewById(R.id.txt_body);

        txt_titulo1=(TextView) view.findViewById(R.id.txt_titulopost1);
        txt_body1=(TextView) view.findViewById(R.id.txt_bodypost1);

        btn_register=(Button) view.findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LLAMAE A LOS METODOS
                //Toast.makeText(getContext(),"OnClick",Toast.LENGTH_SHORT).show();
                metodoPost();

            }
        });
        return view;
    }

    ///metodo register
        public void metodoPost () {
            String titulo = txt_titulo.getText().toString();
            String body = txt_body.getText().toString();
            String url = "posts";

            if (TextUtils.isEmpty(titulo)) {
                txt_titulo.setError("No puede ser vacio");
            } else if (TextUtils.isEmpty(body)) {
                txt_body.setError("No puede ser vacio");
            } else {
                Post post = new Post(0, titulo, body);
                Api.post(getActivity(), url, post, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        System.out.println(responseString);
                        Toast.makeText(getActivity(), "error Failure", Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        System.out.println(responseString);
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        txt_titulo1.setText(txt_titulo.getText());
                        txt_body1.setText(txt_body.getText());

                    }
                });
            }

    }
        //metodo loading
    public void loading(){
        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMax(50);
        progressDoalog.setMessage("Procesando...");
        //progressDoalog.setTitle("ProgressDialog");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDoalog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressDoalog.getProgress() <= progressDoalog.getMax()) {Thread.sleep(50);
                        handle.sendMessage(handle.obtainMessage());
                        if (progressDoalog.getProgress() == progressDoalog.getMax()) {
                            progressDoalog.dismiss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDoalog.incrementProgressBy(1);
        }
    };

    //metodo de carga
    public  void process(){
        progreso= new ProgressDialog(getActivity());
        progreso.setMessage("Procesando...");
        progreso.show();
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
