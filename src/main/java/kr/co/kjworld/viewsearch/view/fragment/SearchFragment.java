package kr.co.kjworld.viewsearch.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

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

    private RecyclerView mSearchView;
    private SearchAdapter mAdapter;
    private Spinner mFilterSpinner;
    private Context mContext;
    private ImageButton mImageButton;

    private int mSelecteditem;

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_layout, container, false);

        mSearchView = view.findViewById(R.id.search_listview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mSearchView.setLayoutManager(layoutManager);
        mAdapter = new SearchAdapter(mContext, new ArrayList<Document>());
        mSearchView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(mSearchView.getContext(),new LinearLayoutManager(view.getContext()).getOrientation());
        mSearchView.addItemDecoration(dividerItemDecoration);

        mFilterSpinner = view.findViewById(R.id.filter_spinner);
        mFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mAdapter.setType(i);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSelecteditem = -1;
        mImageButton = view.findViewById(R.id.sort_button);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] sortItemList = view.getResources().getStringArray(R.array.sort_dialog);
                AlertDialog.Builder oDialog = new AlertDialog.Builder(mContext, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                oDialog.setTitle(R.string.dialog_title)
                        .setSingleChoiceItems(sortItemList, mSelecteditem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mSelecteditem = i;
                            }
                        })
                        .setNeutralButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (mSelecteditem >= 0)
                                    mAdapter.sort(mSelecteditem);
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancle, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void searchStart(String aText, Context context)
    {
        View view = getView();

        KakaoSearchService kakaoService = RetrofitCreator.getInstance().create(KakaoSearchService.class);

        kakaoService.getBlogData("KakaoAK 158ecb4f39933422a476a6ce06120296", aText, "accuracy", 1, 25)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> mAdapter.updateBlogData(item));

        kakaoService.getCafeData("KakaoAK 158ecb4f39933422a476a6ce06120296", aText, "accuracy", 1, 25)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> mAdapter.updateCafeData(item));



    }
}
