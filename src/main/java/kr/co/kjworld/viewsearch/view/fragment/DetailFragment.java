package kr.co.kjworld.viewsearch.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import kr.co.kjworld.viewsearch.R;
import kr.co.kjworld.viewsearch.data.response.data.Document;

public class DetailFragment extends Fragment {
    private Document mDocument;
    public DetailFragment(){}
    public DetailFragment(Document document)
    {
        mDocument = document;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        return view;
    }
}
