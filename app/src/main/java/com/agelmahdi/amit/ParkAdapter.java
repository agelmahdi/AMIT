package com.agelmahdi.amit;

import android.database.Cursor;
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
    private Cursor mCursor;

    public ParkAdapter(List<Datum> mPark) {
        this.mPark = mPark;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        mCursor.moveToPosition(position);

        String id = mCursor.getString(MainActivity.COL_NUM_ID);
        String name = mCursor.getString(MainActivity.COL_NUM_USERS_NAME);
        String address = mCursor.getString(MainActivity.COL_NUM_ADDRESS);
        String lng = mCursor.getString(MainActivity.COL_NUM_LNG);
        String lat = mCursor.getString(MainActivity.COL_NUM_LAT);

        holder.itemView.setTag(id);

        holder.mName.setText(name);
        holder.mAddress.setText(address);
        holder.mLnglat.setText(lng+","+lat);

    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor  swapCursor(Cursor newCursor) {

        if (mCursor == newCursor) {
            return null;
        }
        Cursor temp = mCursor;
        this.mCursor = newCursor;

        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
        return temp;

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
