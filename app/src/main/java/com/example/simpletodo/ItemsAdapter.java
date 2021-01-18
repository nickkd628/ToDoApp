package com.example.simpletodo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    public interface OnClickListener{
        void onItemClicked (int position);
    }

    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;
    OnClickListener clickListener;

    public ItemsAdapter(List<String> items,OnLongClickListener longClickListener, OnClickListener clickListener) {
        this.items =items;
        this.longClickListener = longClickListener;
        this.clickListener= clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // use layout to inflate a view

       View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
       //wrap it inside a View holder
        return new ViewHolder(todoView);
    }

    //
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //grab item at the pos
        String item = items.get(position);
        //bind the item
        holder.bind(item);

    }

    //tells the rv how many items are in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    //container to provide easy acces to view that represent each row of list
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //comeback to this if code does not work and change text2
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        //update the view inside the viewholder
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickListener.onItemClicked(getAdapterPosition());
                }
            });
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //notify the listener which position aws long pressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
