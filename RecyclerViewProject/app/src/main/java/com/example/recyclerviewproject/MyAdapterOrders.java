package com.example.recyclerviewproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterOrders extends RecyclerView.Adapter<MyAdapterOrders.MyViewHolderOrders>{

    private ArrayList<Order> orderList;
    private OnItemClickListener myListener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        myListener = listener;
    }

    public static class MyViewHolderOrders extends RecyclerView.ViewHolder{
        public TextView name, description, position;

        public MyViewHolderOrders(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewTitle2);
            description = itemView.findViewById(R.id.textViewDescription2);
            position = itemView.findViewById(R.id.textViewPosition2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public MyAdapterOrders(ArrayList<Order> myOrderList){orderList = myOrderList;}

    @NonNull
    @Override
    public MyViewHolderOrders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_orders, parent, false);
        MyViewHolderOrders mvh = new MyViewHolderOrders(v, myListener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderOrders holder, int position) {
        Order currentOrder = orderList.get(position);

        holder.name.setText(currentOrder.getName());
        holder.description.setText(currentOrder.getDescription());
        holder.position.setText(Integer.toString(position+1));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


}
