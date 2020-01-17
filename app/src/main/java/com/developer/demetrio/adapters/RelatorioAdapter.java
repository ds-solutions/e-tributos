package com.developer.demetrio.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.developer.demetrio.adapters.utils.ItemRelatorio;

public class RelatorioAdapter extends ArrayAdapter<ItemRelatorio> {
    public RelatorioAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
