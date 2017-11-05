package ua.com.solodilov.evgen.movatest;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import ua.com.solodilov.evgen.movatest.api.SearchPicturesApi;
import ua.com.solodilov.evgen.movatest.api.model.SearchPictures;

public class MainActivity extends AppCompatActivity {

    private Realm mRealm;
    private IStorage mStorage;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(4)
                .deleteRealmIfMigrationNeeded()
                .build();

        mRealm = Realm.getInstance(realmConfiguration);
        mStorage = new RealmStorage(mRealm);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            startFirstFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        if (searchManager != null) {
            SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
            mSearchView.setSearchableInfo(searchableInfo);
        }
        mSearchView.setIconifiedByDefault(false);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            getResultFromApi(query);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getResultFromApi(String phrase) {
        new SearchPicturesApi().getApi().searchPictures(phrase)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchPictures -> {
                    if (searchPictures.getResultCount() > 0) {
                        if (mStorage != null) {
                            SearchItem searchItem = getItem(searchPictures);
                            searchItem.setPhrase(phrase);
                            mStorage.saveToDb(searchItem);
                            notifyRecycler();
                            toolbar.collapseActionView();
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                                .setTitle("Очень жаль!!!")
                                .setMessage("по Вашему запросу не чего не найдено.")
                                .setNeutralButton("Ok", null);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }, throwable -> Log.e(this.getLocalClassName(), throwable.getMessage()));
    }

    private void notifyRecycler() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MainFragment.class.getCanonicalName());
        if (fragment != null) {
            ((MainFragment) fragment).notifyAdapter();
        }
    }


    private void startFirstFragment() {
        Fragment fragment = Fragment.instantiate(this, MainFragment.class.getName());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, MainFragment.class.getCanonicalName())
                .commit();
    }

    public Realm getRealm() {
        return mRealm;
    }

    private SearchItem getItem(SearchPictures searchPictures) {
        SearchItem searchItem = new SearchItem();
        searchItem.setId(searchPictures.getImages().get(0).getId());
        searchItem.setTitle(searchPictures.getImages().get(0).getTitle());
        searchItem.setImageUri(searchPictures.getImages().get(0).getDisplaySizes().get(0).getUri());
        searchItem.setTime(new Date().getTime());
        return searchItem;
    }
}
