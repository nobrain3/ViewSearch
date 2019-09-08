package kr.co.kjworld.viewsearch.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import kr.co.kjworld.viewsearch.R;
import kr.co.kjworld.viewsearch.data.response.data.BlogData;
import kr.co.kjworld.viewsearch.data.response.data.CafeData;
import kr.co.kjworld.viewsearch.data.response.data.Document;
import kr.co.kjworld.viewsearch.data.response.data.KakaoData;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private final String LABEL_CAFE = "Cafe";
    private final String LABEL_BLOG = "Blog";

    List<Document> mSearcList;
    Context mContext;

    public SearchAdapter(Context context, ArrayList<Document> list)
    {
        mContext = context;
        mSearcList = list;
    }
    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.search_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mSearcList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        Document data = mSearcList.get(position);

        holder.mLabelView.setText(data.label);
        holder.mNameView.setText(data.cafename);
        holder.mTitleView.setText(data.title);
        holder.mDateTimeView.setText(data.datetime);

        Glide.with(mContext)
                .load(data.thumbnail)
                .into(holder.mThumbnailView);
    }

    public void updateBlogData(BlogData data)
    {
        for (Document document : data.documents)
        {
            Document newDocument = new Document();
            newDocument.thumbnail = document.thumbnail;
            newDocument.isCafe = false;
            newDocument.label = LABEL_BLOG;
            newDocument.datetime = document.datetime;
            newDocument.cafename = document.cafename;
            newDocument.title = document.title;
            mSearcList.add(newDocument);
        }
        notifyDataSetChanged();
    }

    public void updateCafeData(CafeData data)
    {
        for (Document document : data.documents)
        {
            Document newDocument = new Document();
            newDocument.thumbnail = document.thumbnail;
            newDocument.isCafe = true;
            newDocument.label = LABEL_CAFE;
            newDocument.datetime = document.datetime;
            newDocument.cafename = document.cafename;
            newDocument.title = document.title;
            mSearcList.add(newDocument);
        }
        notifyDataSetChanged();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView mLabelView;
        TextView mNameView;
        TextView mTitleView;
        TextView mDateTimeView;
        ImageView mThumbnailView;

        public SearchViewHolder(@NonNull View itemView){
            super(itemView);
            mLabelView = itemView.findViewById(R.id.cardview_label);
            mNameView = itemView.findViewById(R.id.cardview_name);
            mTitleView = itemView.findViewById(R.id.cardview_title);
            mDateTimeView = itemView.findViewById(R.id.careview_date_time);
            mThumbnailView = itemView.findViewById(R.id.cardview_thumbnail);

        }

    }
}
