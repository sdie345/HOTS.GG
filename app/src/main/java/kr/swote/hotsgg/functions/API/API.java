package kr.swote.hotsgg.functions.API;

import java.util.ArrayList;

import kr.swote.hotsgg.functions.datas.HeroData;
import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
/*
    @GET("api/unknown")
    Call<MultipleResource> doGetListResources();

    @POST("api/users")
    Call<User> createUser(@Body User user);

    @GET("api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);
*/
    @GET("getHeroData/")
    Call<ArrayList<HeroData>> getHeroData();
}