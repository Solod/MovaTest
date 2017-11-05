package ua.com.solodilov.evgen.movatest;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SearchItem extends RealmObject {
    private String id;
    private String title;
    @PrimaryKey
    private String phrase;
    private String imageUri;

    @Override
    public String toString() {
        return "SearchItem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", phrase='" + phrase + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", time=" + time +
                "} " + super.toString();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    private long time;

    public SearchItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
