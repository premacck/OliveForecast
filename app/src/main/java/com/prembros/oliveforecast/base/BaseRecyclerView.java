package com.prembros.oliveforecast.base;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Prem$ on 2/21/2018.
 *
 * <p>Base class containing custom {@link Adapter} and {@link ViewHolder}</p>
 */
public class BaseRecyclerView {

    /**
     * <p>A custom {@link RecyclerView.Adapter} which also facilitates the custom
     *    {@link ViewHolder} to be efficiently attached and detached.</p>
     * <p>This Adapter respects the {@link DiffUtil} changes and
     *    encapsulates the binding of the {@link ViewHolder} to be in the ViewHolder itself.</p>
     */
    public abstract static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull @Override public abstract ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

        @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind();
        }

        @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
            if (!payloads.isEmpty()) holder.bind(payloads);
            else holder.bind();
        }

        @Override public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            holder.onAttach();
        }

        @Override public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            holder.onDetach();
        }

        @Override public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        public abstract int getItemCount();

        public abstract void notifyDataChanged();

        public void release() {}
    }

    /**
     * <p>A custom {@link RecyclerView.ViewHolder} which allows the attaching and detaching of listeners
     *    in the onAttach() and onDetach() methods respectively.</p>
     *
     * <p>The binding of the views should be done in the bind() method,
     *    and the diffUtil changes should be applied to the bind(List<Object>) method</p>
     */
    public abstract static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bind();

        public void bind(List<Object> payloads) {}

        public void onAttach() {}

        public void onDetach() {}
    }
}