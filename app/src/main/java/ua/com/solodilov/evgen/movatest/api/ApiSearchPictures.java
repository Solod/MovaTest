package ua.com.solodilov.evgen.movatest.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import ua.com.solodilov.evgen.movatest.api.model.SearchPictures;

public interface ApiSearchPictures {
    @Headers("Api-Key:q3tgwxqvhhhzt8mfe5u7e6ah")
    @GET("/v3/search/images?fields=id,title,thumb&sort_order=best&page_size=1")
    Single<SearchPictures> searchPictures(@Query(value = "phrase") String phrase);
}
