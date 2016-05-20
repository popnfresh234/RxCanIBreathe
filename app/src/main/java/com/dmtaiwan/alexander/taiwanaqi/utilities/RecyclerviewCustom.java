package com.dmtaiwan.alexander.taiwanaqi.utilities;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

/**
 * Created by lenovo on 5/20/2016.
 */
public class RecyclerViewCustom extends RecyclerView implements ViewTreeObserver.OnGlobalLayoutListener {

    public RecyclerViewCustom(Context context)
    {
        super(context);
    }

    public RecyclerViewCustom(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RecyclerViewCustom(Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    /**
     *  This supports scrolling when using RecyclerView with AppbarLayout
     *  Basically RecyclerView should not be scrollable when there's no data or the last item is visible
     *
     *  Call this method after Adapter#updateData() get called
     */
    public void addOnGlobalLayoutListener()
    {
        this.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout()
    {
        // If the last item is visible or there's no data, the RecyclerView should not be scrollable
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        final RecyclerView.Adapter adapter = getAdapter();
        if (adapter == null || adapter.getItemCount() <= 0 || layoutManager == null)
        {
            setNestedScrollingEnabled(false);
        }
        else
        {
            int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            boolean isLastItemVisible = lastVisibleItemPosition == adapter.getItemCount() - 1;
            setNestedScrollingEnabled(!isLastItemVisible);
        }

        unregisterGlobalLayoutListener();
    }

    private void unregisterGlobalLayoutListener()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        else
        {
            getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

}