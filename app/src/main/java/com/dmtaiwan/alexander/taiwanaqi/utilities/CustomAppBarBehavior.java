package com.dmtaiwan.alexander.taiwanaqi.utilities;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Alexander on 5/21/2016.
 */
public class CustomAppBarBehavior extends AppBarLayout.Behavior {

    private RecyclerView recyclerView;
    private boolean enabled;

    public CustomAppBarBehavior() {
    }

    public CustomAppBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        updatedEnabled();
        return enabled && super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        return enabled && super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY, boolean consumed) {
        return enabled && super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    private void updatedEnabled() {
        enabled = false;
        if(recyclerView != null) {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter != null) {
                int count = adapter.getItemCount();
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int lastItem = 0;
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                        lastItem = Math.abs(linearLayoutManager.findLastCompletelyVisibleItemPosition());
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                        int[] lastItems = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(new int[staggeredGridLayoutManager.getSpanCount()]);
                        lastItem = Math.abs(lastItems[lastItems.length - 1]);
                    }
                    enabled = lastItem < count - 1;
                }
            }
        }
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}