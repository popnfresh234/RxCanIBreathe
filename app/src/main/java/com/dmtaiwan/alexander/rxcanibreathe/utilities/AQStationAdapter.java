package com.dmtaiwan.alexander.rxcanibreathe.utilities;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmtaiwan.alexander.rxcanibreathe.R;
import com.dmtaiwan.alexander.rxcanibreathe.models.AQStation;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alexander on 11/12/2015.
 */
public class AQStationAdapter extends RecyclerView.Adapter<AQStationAdapter.ViewHolder> {

    private static final String LOG_TAG = AQStationAdapter.class.getSimpleName();
    private Context mContext;
    final private View mEmptyView;
    private RecyclerClickListener mListener;
    private List<AQStation> mStationList;
    private int mPage;

    public AQStationAdapter(Context context, View emptyView, RecyclerClickListener listener, int page) {
        this.mContext = context;
        this.mEmptyView = emptyView;
        this.mListener = listener;
        this.mPage = page;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_aq_station, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        AQStation aqStation = mStationList.get(position);
        holder.mPm25Text.setText(aqStation.getAQI());
        holder.mPm25Text.setTextColor(Utilities.getTextColor(aqStation.getAQI(), mContext));
        holder.mPm25Text.setBackground(Utilities.getAqiBackground(aqStation.getAQI(), mContext));
        holder.mStationName.setText(aqStation.getSiteName());

        int id = mContext.getResources().getIdentifier("station" + String.valueOf(aqStation.getSiteNumber()), "string", mContext.getPackageName());
        String name = mContext.getResources().getString(id);
        Log.i(LOG_TAG, name);
        Log.i(LOG_TAG, String.valueOf(aqStation.getSiteNumber()));
        holder.mStationNameEng.setText(mContext.getResources().getString(id));

        holder.mWindSpeed.setText(aqStation.getFormattedWindSpeed());
        holder.mTime.setText(aqStation.getFormattedTime());
    }

    @Override
    public int getItemCount() {
        if (mStationList != null) {
            return mStationList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.pm25)
        TextView mPm25Text;

        @Bind(R.id.aq_station_name)
        TextView mStationName;

        @Bind(R.id.aq_station_name_eng)
        TextView mStationNameEng;

        @Bind(R.id.aq_wind_speed)
        TextView mWindSpeed;

        @Bind(R.id.aq_station_time)
        TextView mTime;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            AQStation aqStation = mStationList.get(position);
            mListener.onRecyclerClick(aqStation, mStationList);
        }
    }

    public void updateData(List<AQStation> stationList) {
        Log.i(LOG_TAG, "updating data");
        mStationList = sortStations(stationList, mPage);
        Log.i("stationList", mStationList.size() + "");
        notifyDataSetChanged();
        mEmptyView.setVisibility(mStationList.size() == 0 ? View.VISIBLE : View.GONE);
    }

    public interface RecyclerClickListener {
        void onRecyclerClick(AQStation aqStation, List<AQStation> aqStationList);
    }


    private List<AQStation> sortStations(List<AQStation> aqStationList, int page) {
        List<AQStation> sortedStations = new ArrayList<>();
        if (page == 0) {
            String county = PreferenceManager.getDefaultSharedPreferences(mContext).getString(mContext.getString(R.string.pref_key_county), mContext.getString(R.string.pref_county_taipei_city));
            for (AQStation aqStation : aqStationList) {
                Log.i(LOG_TAG, aqStation.getCounty());
                if (aqStation.getCounty().equals(county)) {
                    sortedStations.add(aqStation);
                }
            }
            return sortedStations;
        }
        if (page == 1) {
            String secondaryCounty = PreferenceManager.getDefaultSharedPreferences(mContext).getString(mContext.getString(R.string.pref_key_secondary_county), mContext.getString(R.string.pref_county_taipei_city));
            for (AQStation aqStation : aqStationList) {
                if (aqStation.getCounty().equals(secondaryCounty)) {
                    sortedStations.add(aqStation);
                }
            }
            return sortedStations;
        } else {
            return null;
        }
    }
}
