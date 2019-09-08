package kr.co.kjworld.viewsearch.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kr.co.kjworld.viewsearch.R;
import kr.co.kjworld.viewsearch.data.network.RetrofitCreator;
import kr.co.kjworld.viewsearch.data.response.KakaoSearchService;
import kr.co.kjworld.viewsearch.data.response.data.Document;
import kr.co.kjworld.viewsearch.view.adapter.SearchAdapter;

public class SearchFragment extends Fragment {

    RecyclerView mSearchView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_layout, container, false);
    }

    public void searchStart(String aText, Context context)
    {
        View view = getView();
        mSearchView = view.findViewById(R.id.search_listview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mSearchView.setLayoutManager(layoutManager);
        final SearchAdapter adapter = new SearchAdapter(context, new ArrayList<Document>());
        mSearchView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(mSearchView.getContext(),new LinearLayoutManager(view.getContext()).getOrientation());
        mSearchView.addItemDecoration(dividerItemDecoration);


        KakaoSearchService kakaoService = RetrofitCreator.getInstance().create(KakaoSearchService.class);

        kakaoService.getBlogData("KakaoAK 158ecb4f39933422a476a6ce06120296", aText, "accuracy", 1, 25)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> adapter.updateBlogData(item));

        kakaoService.getCafeData("KakaoAK 158ecb4f39933422a476a6ce06120296", aText, "accuracy", 1, 25)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> adapter.updateCafeData(item));



    }
}
