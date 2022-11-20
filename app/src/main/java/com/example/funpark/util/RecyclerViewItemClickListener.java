package com.example.funpark.util;

import android.view.View;

/**
 * Interface qui permet de définir l'action à faire pour les clicks et long clicks des items
 */
public interface RecyclerViewItemClickListener {
    void onItemClick(View v, int position);

    void onItemLongClick(View v, int position);
}
