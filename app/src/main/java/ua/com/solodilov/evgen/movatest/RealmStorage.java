package ua.com.solodilov.evgen.movatest;

import io.realm.Realm;

class RealmStorage implements IStorage {
    private final Realm mRealm;

    public RealmStorage(Realm realm) {
        mRealm = realm;
    }

    @Override
    public void saveToDb(SearchItem searchItem) {
        if (!mRealm.isInTransaction()) mRealm.beginTransaction();
        mRealm.insertOrUpdate(searchItem);
        mRealm.commitTransaction();
    }
}
