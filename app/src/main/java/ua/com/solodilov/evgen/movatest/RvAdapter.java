package ua.com.solodilov.evgen.movatest;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import io.realm.RealmResults;

class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    private OnChangeItemListener mListener;
    private RealmResults<SearchItem> mListItem;

    RvAdapter(RealmResults<SearchItem> items, OnChangeItemListener listener) {
        mListItem = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(mListItem.get(position).getPhrase());
        holder.view.setOnClickListener(v -> mListener.onChangeItem());
        Glide
                .with(holder.view.getContext())
                .load(mListItem.get(position).getImageUri())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (mListItem == null || mListItem.size() == 0) {
            return 0;
        }
        return mListItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView imageView;
        TextView textView;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.iv_picture);
            textView = itemView.findViewById(R.id.tv_title);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }

    public interface OnChangeItemListener {
        void onChangeItem();
    }
}
