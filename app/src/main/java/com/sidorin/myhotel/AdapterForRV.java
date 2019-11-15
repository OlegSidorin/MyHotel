package com.sidorin.myhotel;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterForRV extends RecyclerView.Adapter<AdapterForRV.MyViewHolder> {

    static int selected_position = -1;

    private ArrayList<DataTableItem> dataInAdapter;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_guest_name, tv_guest_surname,
                tv_room, tv_title, tv_days_guest_out, tv_days_guest_in;
        private ImageView iv_sign;

        private MyViewHolder(@NonNull View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);
            tv_guest_name = (TextView) itemView.findViewById(R.id.tv_name_in);
            tv_guest_surname = (TextView) itemView.findViewById(R.id.tv_surname_in);
            tv_room = (TextView) itemView.findViewById(R.id.tv_room);
            iv_sign = (ImageView) itemView.findViewById(R.id.iv_sign);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_days_guest_out = (TextView) itemView.findViewById(R.id.tv_days);
            tv_days_guest_in = (TextView) itemView.findViewById(R.id.tv_in_days);

        }

        @Override
        public void onClick(View view) {

            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

            // обновляем в соответствии с выделением
            notifyItemChanged(selected_position);
            selected_position = getAdapterPosition();
            notifyItemChanged(selected_position);

        }
    }

    public AdapterForRV(ArrayList<DataTableItem> data) {
        this.dataInAdapter = data;
    }

    @NonNull
    @Override
    public AdapterForRV.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForRV.MyViewHolder holder, final int position) {
        DataTableItem item = dataInAdapter.get(position);

        if (item.daysBeforeCheckOut != 0 || item.daysAfterCheckIn != 0) {
            holder.iv_sign.setImageResource(R.drawable.hotel);
        }
        if (item.daysBeforeCheckOut == 0) {
            holder.iv_sign.setImageResource(R.drawable.run_fast_m);
            // holder.tv_guest_surname.setTextColor(Color.rgb(255,152,0));
            // holder.tv_guest_name.setTextColor(Color.rgb(255,152,0));
        }
        if (item.daysAfterCheckIn == 0) {
            holder.iv_sign.setImageResource(R.drawable.walk);
            // holder.tv_guest_surname.setTextColor(Color.rgb(255,235,59));
            // holder.tv_guest_name.setTextColor(Color.rgb(255,235,59));
        }

        holder.tv_room.setText(item.getRoom());
        holder.tv_guest_surname.setText(item.getSurnameOfGuest());

        holder.tv_guest_name.setText(item.getNameOfGuest());

        holder.tv_days_guest_out.setText(item.daysBeforeCheckOut.toString());
        holder.tv_days_guest_in.setText(item.daysAfterCheckIn.toString());


        holder.itemView.setBackgroundColor(selected_position == position ? Color.argb(90, 156, 185, 235) : Color.TRANSPARENT);

        // holder.tv_title.setVisibility(View.VISIBLE);


        // holder.tv_type.setText(item.type);

    }

    @Override
    public int getItemCount() {
        return dataInAdapter.size();
    }


}
