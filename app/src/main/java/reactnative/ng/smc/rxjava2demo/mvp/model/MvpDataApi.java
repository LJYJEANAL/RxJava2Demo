package reactnative.ng.smc.rxjava2demo.mvp.model;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import reactnative.ng.smc.rxjava2demo.network.data.ContentInfo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/9/28.
 * 形如?t=1&p=2&size=3的url链接不能用@PATH注解
 * 使用@Query注解
 * http://u1.3gtv.net:2080/pms-service/section/subsection_content_list?id=5042&limit=4&start=0&portalId=1
 */

public interface MvpDataApi {
    /**
     * 获取首页数据
     */
    @GET("pms-service/section/subsection_content_list")
    Call<ResponseBody> getRecommendCall(@QueryMap Map<String, Integer> Map);

    @GET("pms-service/section/subsection_list")
    Call<ResponseBody> getSecationBannerCall(@QueryMap Map<String, Integer> Map);
    @GET("pms-service/section/subsection_list")
    Call<RequestBody> getSecationBannerCall(@Query("id") int id, @Query("start") int start, @Query("limit") int limit);


    /**
     * [ 获取首页子栏目内容 ]完整地址：http://u1.3gtv.net:2080/pms-service/section/content_list?
     * start=0&id=5055&limit=10&portalId=1
     */
    @GET("pms-service/section/content_list")
    Call<ContentInfo> getSecationContentCall(@Query("id") int id, @Query("start") int start, @Query("limit") int limit);
    @GET("pms-service/section/content_list")
    Observable<ContentInfo>getSecationContentCall(@QueryMap Map<String, Integer> Map);
    /*http://api.3gtv.net/topic-service/section/contentList.to?portal=2&id=734&limit=10&start=1&sort=createTime+desc&portalId=1*/
    /**
     * get 与path使用
     *http://gank.io/api/data/福利/5/1
     @GET("api/data/福利/{pageCount}/{pageIndex}")
     Call<DataInfo> getData(@Path("pageCount") int pageCount,
     @Path("pageIndex") int pageIndex);
     */




    /** 用户登录 */
    @FormUrlEncoded
    @POST("gdzbt/user/login/action.do")
    Call<ResponseBody> loginCall(@Field("phone") String phone, @Field("password") String password);
    /** 用户信息获取 */
    @GET("gdzbt/user/{userId}/info.do")
    Call<ResponseBody> userInfoCall(@Path("userId") long userId);
    /** 用户信息修改 */
    @FormUrlEncoded
    @PUT("gdzbt/user/{userId}/modifly.do")
    Call<ResponseBody> modiflyCall(@Path("userId") long userId, @Field("name") String name, @Field("sex") int sex);

    /** 用户修改密码(根据手机号码) */
    @FormUrlEncoded
    @POST("gdzbt/user/modiflyPasswordByPhone/modifly.do")
    Call<ResponseBody> modiflyPasswordCall(@Field("phone") String phone, @Field("password") String password);

    /**
     * 头像上传 根据自动生成的图片名字
     */
    @Multipart
    @POST("gdzbt/user/{userId}/avatar/upload.do")
    Call<ResponseBody> upLoadCall(@Path("userId") long userId, @PartMap Map<String, RequestBody> map);
}
