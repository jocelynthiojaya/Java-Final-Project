package com.example.recyclerviewproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Item> itemList;
    private OnItemClickListener myListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        myListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView name, description, position, stock;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textViewTitle);
            description = itemView.findViewById(R.id.textViewDescription);
            position = itemView.findViewById(R.id.textViewPosition);
            stock = itemView.findViewById(R.id.textViewStock);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public MyAdapter(ArrayList<Item> myItemList){
        itemList = myItemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        MyViewHolder mvh = new MyViewHolder(v, myListener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item currentItem = itemList.get(position);

        holder.image.setImageResource(currentItem.getImageResource());
        holder.name.setText(currentItem.getName());
        holder.description.setText(currentItem.getDescription());
        holder.position.setText(Integer.toString(position+1));
        holder.stock.setText(Integer.toString(currentItem.getStock()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
