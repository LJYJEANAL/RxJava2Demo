package reactnative.ng.smc.rxjava2demo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/9/1.
 * http://gank.io/api/data/Android/10/1
 * http://gank.io/api/data/福利/10/1
 * http://gank.io/api/data/all/10/1
 */

public interface NewsApi {

    @GET("api/data/{category}/{count}/{page}")
    Observable<NewsEntity> getNews(@Path("category") String category, @Path("count") int count, @Path("page") int page);

}
