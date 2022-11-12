package com.example.funpark.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funpark.R;
import com.example.funpark.database.entity.TicketEntity;
import com.example.funpark.database.entity.VisitorEntity;
import com.example.funpark.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<T> mData;
    private RecyclerViewItemClickListener mListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTextView;
        ViewHolder(TextView textView) {
            super(textView);
            mTextView = textView;
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            mListener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        T item = mData.get(position);
        if (item.getClass().equals(VisitorEntity.class))
            holder.mTextView.setText(((VisitorEntity) item).getFirstName());
        if (item.getClass().equals(TicketEntity.class))
            holder.mTextView.setText(((TicketEntity) item).getTicketName());
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<T> data) {
        if (mData == null) {
            mData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof VisitorEntity) {
                        return ((VisitorEntity) mData.get(oldItemPosition)).getId() == ((VisitorEntity) data.get(newItemPosition)).getId();
                    }
                    if (mData instanceof TicketEntity) {
                        return false;
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof VisitorEntity) {
                        VisitorEntity newVisitor = (VisitorEntity) data.get(newItemPosition);
                        VisitorEntity oldVisitor = (VisitorEntity) mData.get(newItemPosition);
                        return newVisitor.getId()== oldVisitor.getId()
                                && Objects.equals(newVisitor.getFirstName(), newVisitor.getFirstName())
                                && Objects.equals(newVisitor.getLastName(), newVisitor.getLastName())
                                && Objects.equals(newVisitor.getBirthDate(), newVisitor.getBirthDate())
                                && Objects.equals(newVisitor.getVisitDate(), newVisitor.getVisitDate());
                    }
                    if (mData instanceof TicketEntity) {
                        TicketEntity newTicket = (TicketEntity) data.get(newItemPosition);
                        TicketEntity oldTicket = (TicketEntity) mData.get(newItemPosition);
                        return false;
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}
