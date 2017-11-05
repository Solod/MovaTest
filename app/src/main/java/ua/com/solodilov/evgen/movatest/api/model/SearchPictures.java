
package ua.com.solodilov.evgen.movatest.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchPictures {

    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("result_count")
    @Expose
    private Integer resultCount;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }
}

