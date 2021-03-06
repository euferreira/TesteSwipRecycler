package com.br.testeswiprecycler;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    List<RecyclerEntity> list;
    Context context;
    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;

    public RecyclerAdapter(Context context, List<RecyclerEntity> articlesList) {
        this.list = articlesList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).isShowMenu()){
            return SHOW_MENU;
        }else{
            return HIDE_MENU;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if(viewType==SHOW_MENU){
            v = null;

            try {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_menu, parent, false);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return new MenuViewHolder(v);
        }else{
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list, parent, false);
            return new MyViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        RecyclerEntity entity = list.get(position);
        if(holder instanceof MyViewHolder){
            ((MyViewHolder)holder).title.setText(entity.getTitle());
            ((MyViewHolder)holder).imageView.setImageDrawable(context.getResources().getDrawable(entity.getImage()));

            ((MyViewHolder)holder).container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showMenu(position);
                    return true;
                }
            });

        }
        if(holder instanceof MenuViewHolder){
            //Menu Actions
            try {
                ((MenuViewHolder)holder).imgdelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                    }
                });

                ((MenuViewHolder)holder).imgedit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
                    }
                });

                ((MenuViewHolder)holder).imgshare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "share", Toast.LENGTH_SHORT).show();
                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void showMenu(int position) {
        for(int i=0; i<list.size(); i++){
            list.get(i).setShowMenu(false);
        }
        list.get(position).setShowMenu(true);
        notifyDataSetChanged();
    }

    public boolean isMenuShown() {
        for(int i=0; i<list.size(); i++){
            if(list.get(i).isShowMenu()){
                return true;
            }
        }
        return false;
    }

    public void closeMenu() {
        for(int i=0; i<list.size(); i++){
            list.get(i).setShowMenu(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;
        ConstraintLayout container;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.imageView);
            container = itemView.findViewById(R.id.container);

        }
    }
    public class MenuViewHolder extends RecyclerView.ViewHolder{
        ImageView imgshare, imgdelete, imgedit;
        public MenuViewHolder(View view){
            super(view);
            imgdelete = itemView.findViewById(R.id.imgdelete);
            imgedit = itemView.findViewById(R.id.imgedit);
            imgshare = itemView.findViewById(R.id.imgshare);
        }
    }
}
