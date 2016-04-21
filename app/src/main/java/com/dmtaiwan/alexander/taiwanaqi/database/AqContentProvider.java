package com.dmtaiwan.alexander.taiwanaqi.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.dmtaiwan.alexander.taiwanaqi.utilities.Utilities;
import com.tjeannin.provigen.ProviGenOpenHelper;
import com.tjeannin.provigen.ProviGenProvider;

/**
 * Created by lenovo on 4/21/2016.
 */
public class AqContentProvider extends ProviGenProvider {

    private static Class[] contracts = new Class[]{AqStationContract.class};

    @Override
    public SQLiteOpenHelper openHelper(Context context) {
        return new ProviGenOpenHelper(getContext(), Utilities.DATABASE_NAME, null, 1, contracts);
    }

    @Override
    public Class[] contractClasses() {
        return contracts;
    }
}
