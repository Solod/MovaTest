package ua.com.solodilov.evgen.movatest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Sort;

public class MainFragment extends Fragment {
    private RvAdapter mRvAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_last_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvAdapter = new RvAdapter(((MainActivity)getActivity()).getRealm().where(SearchItem.class).findAllSorted("time", Sort.DESCENDING), changeListener);
        mRecyclerView.setAdapter(mRvAdapter);
        getItemForList();
    }

    private void getItemForList() {

    }

    private final RvAdapter.OnChangeItemListener changeListener = () -> {};

    public void notifyAdapter() {
        mRvAdapter.notifyDataSetChanged();
    }
}
