package com.example.pcquispe.api.fragmentos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pcquispe.api.R;
import com.example.pcquispe.api.adaptadores.PostAdapter;
import com.example.pcquispe.api.entidades.Post;
import com.example.pcquispe.api.http.Api;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Properties;

public class ListaElementosFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //variables
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ListView listaelementos;
    ProgressDialog progressDoalog, progreso;
    ArrayList<Post> lista=new ArrayList<>();

    public ListaElementosFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static ListaElementosFragment newInstance(String param1, String param2) {
        ListaElementosFragment fragment = new ListaElementosFragment();
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
        View view = inflater.inflate(R.layout.fragment_lista_elementos, container, false);
        listaelementos = (ListView) view.findViewById(R.id.text_listado);
        //call methods
        metodoGet();
        return view;

    }
    //metodo register
    public void metodoGet() {
        String url = "posts";
        process();
        Api.get(getActivity(), url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println(responseString);
                Toast.makeText(getActivity(), "error Failure", Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                System.out.println(responseString);
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                lista=new Gson().fromJson(responseString,new TypeToken<ArrayList<Post>>(){}.getType());
                listaelementos.setAdapter(new PostAdapter(getActivity(),lista));
                listaelementos.setOnItemClickListener(ListaElementosFragment.this);

                //metodo
                listaelementos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //call metodod
                        Toast.makeText(getActivity(), "item" +i, Toast.LENGTH_SHORT).show();
                        onItemLongCLick(i);
                    }
                });
                progreso.hide();
            }
        });
    }
    //metodo onItemLongCLick
    private void onItemLongCLick(int position) {
        final int pos = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("¿Que desea hacer?")
                .setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editarDetalle(lista.get(pos));
                    }

                })
                .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminar(lista.get(pos));
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    //metodo edit
    public void editarDetalle(final Post post){
        Fragment fragment = null;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragment = new DetalleEditFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("Post",post);
        bundle.putString("nombreKey",post.getTitle().toString());
        bundle.putString("bodyKey",post.getBody().toString());
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.contenedorFragment, fragment).addToBackStack(null).commit();
        //call methods
    }

    //edit
    public void editar(final Post post) {
        System.out.println("Editar" + post.getId());
        DetalleEditFragment fragment  = new DetalleEditFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("editar",true);
        bundle.putSerializable("post",post);
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedorFragmentedit, fragment).addToBackStack(null)
                .commit();
    }
    public void eliminar(final Post post) {
        System.out.println("Se eliminara " + post.getId());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("¿Esta seguro que desea eliminar?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Eliminando...");
                        progressDialog.show();

                        Api.post(getContext(), "posts/"+post.getId(), post, new TextHttpResponseHandler() {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                progressDialog.dismiss();
                                System.out.println(responseString);
                            }
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                progressDialog.dismiss();
                                Properties response = new Gson().fromJson(responseString, Properties.class);
                                String mensaje = response.getProperty("mensaje","Se eliminó.");
                                Toast.makeText(getContext(),mensaje,Toast.LENGTH_SHORT).show();
                                metodoGet();
                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
    }
    //metodo loading
    public void loading() {
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
                    while (progressDoalog.getProgress() <= progressDoalog.getMax()) {
                        Thread.sleep(50);
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
    public void process() {
        progreso = new ProgressDialog(getActivity());
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

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
