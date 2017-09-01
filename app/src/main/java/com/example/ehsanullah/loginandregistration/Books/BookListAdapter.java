package com.example.ehsanullah.loginandregistration.Books;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ehsanullah.loginandregistration.R;
import com.google.api.services.books.model.Volume;

import java.util.List;


/**
 * Created by cloubao on 1/20/16.
 */
public class BookListAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private final int spanCount;
    private List<Volume> volumes;
    Context context;

    public BookListAdapter(Context context,List<Volume> volumes, int spanCount) {
        this.volumes = volumes;
        this.spanCount = spanCount;
        this.context = context;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_book, parent, false);

        return new BookViewHolder(context,itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.setSpanCount(spanCount);
        holder.setVolume(volumes.get(position));
    }

    @Override
    public long getItemId(int position) {
        return volumes.get(position).getId().hashCode();
    }

    @Override
    public int getItemCount() {
        return volumes.size();
    }
}
