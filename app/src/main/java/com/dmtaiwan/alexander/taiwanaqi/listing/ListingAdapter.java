package com.dmtaiwan.alexander.taiwanaqi.listing;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmtaiwan.alexander.taiwanaqi.R;
import com.dmtaiwan.alexander.taiwanaqi.models.AQStation;
import com.dmtaiwan.alexander.taiwanaqi.utilities.PinyinHandler;
import com.dmtaiwan.alexander.taiwanaqi.utilities.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alexander on 11/12/2015.
 */
public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHolder> {

    private static final String LOG_TAG = ListingAdapter.class.getSimpleName();
    private Context mContext;
    final private View mEmptyView;
    private RecyclerClickListener mListener;
    private List<AQStation> mStationList;
    private int mPage;

    public ListingAdapter(Context context, View emptyView, RecyclerClickListener listener, int page) {
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

        HashMap nameMap = PinyinHandler.fetchMap(mContext);
        String engName = (String) nameMap.get(aqStation.getSiteName());

        holder.mStationNameEng.setText(engName);

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
        mStationList = sortStations(stationList, mPage);
        notifyDataSetChanged();
        setEmptyView();
    }



    public interface RecyclerClickListener {
        void onRecyclerClick(AQStation aqStation, List<AQStation> aqStationList);
    }


    private List<AQStation> sortStations(List<AQStation> aqStationList, int page) {
        List<AQStation> sortedStations = new ArrayList<>();
        if (page == 0 && aqStationList!= null) {
            String county = PreferenceManager.getDefaultSharedPreferences(mContext).getString(mContext.getString(R.string.pref_key_county), mContext.getString(R.string.pref_county_taipei_city));
            for (AQStation aqStation : aqStationList) {;
                if (aqStation.getCounty().equals(county)) {
                    sortedStations.add(aqStation);
                }
            }
            return sortedStations;
        }
        if (page == 1 && aqStationList != null) {
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

    private void setEmptyView() {
        if (mStationList == null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }else {
            mEmptyView.setVisibility(mStationList.size() == 0 ? View.VISIBLE : View.GONE);
        }
    }
}
