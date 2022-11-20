package com.example.funpark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.funpark.R;
import com.example.funpark.util.IEntityBase;

import java.util.List;

/**
 * classe qui permet d'afficher les listes dans les fomrulaires
 *
 * @param <T> choix du type dans la liste
 */
public class ListAdapter<T> extends ArrayAdapter<T> {

    private int mResource;
    private List<T> mData;

    public ListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<T> data) {
        super(context, resource, data);
        mResource = resource;
        mData = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(mResource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = convertView.findViewById(R.id.ticketTypeItem);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        T item = getItem(position);
        if (item != null && item instanceof IEntityBase) {
            viewHolder.itemView.setText(((IEntityBase) item).toString(getContext()));
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView itemView;
    }

    public void updateData(List<T> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }
}
