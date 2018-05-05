package com.example.pcquispe.api.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pcquispe.api.R;
import com.example.pcquispe.api.entidades.Post;

import java.util.ArrayList;

/**
 * Created by PCQUISPE on 2/20/2018.
 */

public class PostAdapter  extends BaseAdapter{

    protected Activity activity;
    protected ArrayList<Post> lista;
    
    public PostAdapter(Activity activity, ArrayList<Post> lista) {
        this.activity = activity;
        this.lista = lista;
    }
    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return lista.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1= view;
        if (view1==null){
            LayoutInflater layoutInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view1=layoutInflater.inflate(R.layout.fragment_list_detalle,null);
        }
        Post post=lista.get(i);
        TextView titulo=(TextView) view1.findViewById(R.id.tex_titulopost);
        TextView bcdy=(TextView) view1.findViewById(R.id.tex_bodypost);
        titulo.setText(post.getTitle());
        bcdy.setText(post.getBody());
        return view1;
    }
}
