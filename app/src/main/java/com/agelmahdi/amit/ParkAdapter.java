package com.agelmahdi.amit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agelmahdi.amit.model.Datum;

import java.util.List;

/**
 * Created by Ahmed El-Mahdi on 12/18/2017.
 */

public class ParkAdapter extends RecyclerView.Adapter<ParkAdapter.Holder> {
    private List<Datum> mPark;

    public ParkAdapter(List<Datum> mPark) {
        this.mPark = mPark;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        final Datum park = mPark.get(position);
        holder.mName.setText(park.getUserNumber());
        holder.mAddress.setText(park.getAddress());
        holder.mLnglat.setText(park.getLangtitude()+","+park.getLatitude());
    }

    @Override
    public int getItemCount() {
        return mPark.size();
    }

    public void addPark(List<Datum> parkResponses) {
        mPark.addAll(parkResponses);
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView mName, mAddress,mLnglat;
        public Holder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mAddress=itemView.findViewById(R.id.address);
            mLnglat=itemView.findViewById(R.id.lnglat);
        }
    }
}
