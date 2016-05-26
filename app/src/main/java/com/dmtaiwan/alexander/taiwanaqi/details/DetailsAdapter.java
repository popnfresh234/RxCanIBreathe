package com.dmtaiwan.alexander.taiwanaqi.details;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmtaiwan.alexander.taiwanaqi.R;
import com.dmtaiwan.alexander.taiwanaqi.models.AQStation;
import com.dmtaiwan.alexander.taiwanaqi.utilities.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alexander on 11/13/2015.
 */
public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {

    private Context mContext;
    private AQStation mAQStation;


    public DetailsAdapter(Context context, AQStation aqStation) {
        this.mContext = context;
        this.mAQStation = aqStation;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_detail, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTitle.setText(Utilities.getAQDetailTitle(position, mContext));
        holder.mData.setText(Utilities.getAqData(position, mAQStation));
        holder.mIcon.setImageResource(Utilities.getAqIcon(position));

        if (position == 2) {
            float angle = Utilities.getWindDegreeForRotate(mAQStation.getWindDirec());
            holder.mIcon.setRotation(angle);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.aq_detail_title)
        TextView mTitle;

        @Nullable
        @Bind(R.id.aq_detail_data)
        TextView mData;

        @Nullable
        @Bind(R.id.aq_detail_icon)
        ImageView mIcon;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
