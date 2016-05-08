package com.afollestad.appthemeenginesample.rv;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.appthemeenginesample.R;

import java.util.Locale;

/**
 * @author Aidan Follestad (afollestad)
 */
public class SampleRVAdapter extends RecyclerView.Adapter<SampleRVAdapter.SampleVH> {

    public SampleRVAdapter() {
    }

    @Override
    public SampleVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_rv, parent, false);
        return new SampleVH(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(SampleVH holder, int position) {
        holder.title.setText(String.format(Locale.getDefault(), "Item %d", position + 1));
        holder.subtitle.setText("Sample coloring");
    }

    @Override
    public int getItemCount() {
        return 80;
    }

    public static class SampleVH extends RecyclerView.ViewHolder {

        final TextView title;
        final TextView subtitle;

        public SampleVH(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
        }
    }
}