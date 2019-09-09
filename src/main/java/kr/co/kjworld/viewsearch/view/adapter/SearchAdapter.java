package kr.co.kjworld.viewsearch.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import kr.co.kjworld.viewsearch.R;
import kr.co.kjworld.viewsearch.data.response.data.Document;
import kr.co.kjworld.viewsearch.data.response.data.KakaoData;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    public final static int ALL = 0;
    public final static int BLOG = 1;
    public final static int CAFE = 2;

    public final static String LABEL_CAFE = "Cafe";
    public final static String LABEL_BLOG = "Blog";

    public final static int SORT_TITLE = 0;
    public final static int SORT_DATETIME = 1;

    List<Document> mSearcList;
    Context mContext;

    int mLabelType;
    int mSortType;

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
        int size = 0;
        int fullSize = mSearcList.size();

        switch (mLabelType)
        {
            case BLOG:
            case CAFE:
                Document temp;
                for(int i = 0; i < fullSize; i ++)
                {
                    temp = mSearcList.get(i);
                    if (mLabelType == BLOG)
                    {
                        if (temp.isCafe == false)
                            size++;
                    } else {
                        if (temp.isCafe == true)
                            size++;
                    }
                }
                break;

            case ALL:
            default:
                size = fullSize;
                break;

        }
        return size;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        Document temp;

        int itemIndex = 0;
        int fullSize = mSearcList.size();

        Document data = null;
        if (mLabelType == ALL)
        {
            data = mSearcList.get(position);
        }

        for (int i = 0; i < fullSize; i++)
        {
            temp = mSearcList.get(i);
            if (mLabelType == BLOG && temp.isCafe == false)
            {
                if (position == itemIndex) {
                    data = temp;
                    break;
                }
                itemIndex++;
            } else if (mLabelType == CAFE && temp.isCafe == true){
                if (position == itemIndex) {
                    data = temp;
                    break;
                }

                itemIndex++;
            }
        }

        holder.mLabelView.setText(data.label);
        holder.mNameView.setText(data.name);
        holder.mTitleView.setText(data.title);
        holder.mDateTimeView.setText(data.datetime);

        Glide.with(mContext)
                .load(data.thumbnail)
                .into(holder.mThumbnailView);
    }

    public void updateBlogData(KakaoData data)
    {
        for (Document document : data.documents)
        {
            Document newDocument = new Document();
            newDocument.thumbnail = document.thumbnail;
            newDocument.isCafe = false;
            newDocument.label = LABEL_BLOG;
            newDocument.datetime = document.datetime;
            newDocument.name = document.name;
            newDocument.title = document.title;
            mSearcList.add(newDocument);
        }
        notifyDataSetChanged();
    }

    public void updateCafeData(KakaoData data)
    {
        for (Document document : data.documents)
        {
            Document newDocument = new Document();
            newDocument.thumbnail = document.thumbnail;
            newDocument.isCafe = true;
            newDocument.label = LABEL_CAFE;
            newDocument.datetime = document.datetime;
            newDocument.name = document.name;
            newDocument.title = document.title;
            mSearcList.add(newDocument);
        }
        notifyDataSetChanged();
    }

    public void setType(int type) {
        mLabelType = type;
    }

    public void sort(int type) {
        mSortType = type;
        Collections.sort(mSearcList, new Comparator<Document>() {
            @Override
            public int compare(Document document, Document document2) {
                if (mSortType == SORT_TITLE)
                    return document.title.compareTo(document2.title);
                else
                    return document2.datetime.compareTo(document.datetime);
            }
        });
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
