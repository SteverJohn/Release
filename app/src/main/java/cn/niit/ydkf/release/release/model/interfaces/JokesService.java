package cn.niit.ydkf.release.release.model.interfaces;


import cn.niit.ydkf.release.release.model.entities.JokesBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JokesService {
    @GET("255-1")
    Call<JokesBean> getJokes(@Query("showapi_appid") int showapi_appid , @Query("showapi_sign") String showapi_sign, @Query("type") int type,@Query("page") int page);
}
