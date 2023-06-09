package com.example.projectpas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private Context context;
    private List<EncapField> listSports;
    private AdapterListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvReleaseDate;
        public ImageView imageList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
            imageList = itemView.findViewById(R.id.imageList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSportsSelected(listSports.get(getAdapterPosition()));
                }
            });
        }
    }

    public Adapter(Context context, List<EncapField> listSports, AdapterListener listener) {
        this.context = context;
        this.listSports = listSports;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        MyViewHolder myView = new MyViewHolder(item);

        item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onDataLongClicked(listSports.get(myView.getAdapterPosition()));
                return true;
            }
        });

        return myView;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        final EncapField contact = this.listSports.get(position);

        holder.tvName.setText(contact.getName());
        holder.tvReleaseDate.setText(contact.getFirstEvent());

        Glide.with(holder.itemView.getContext()).load(contact.getImageBadge()).into(holder.imageList);
    }

    @Override
    public int getItemCount() {
        return this.listSports.size();
    }

    public interface AdapterListener{
        void onSportsSelected(EncapField contact);

        void onDataLongClicked(EncapField listSport);
    }

}
