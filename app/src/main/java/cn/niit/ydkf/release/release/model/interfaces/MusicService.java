package cn.niit.ydkf.release.release.model.interfaces;


import cn.niit.ydkf.release.release.model.entities.MusicBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MusicService {
    @GET("213-4")
    Call<MusicBean> getMusic(@Query("showapi_appid") int showapi_appid , @Query("showapi_sign") String showapi_sign,@Query("topid") int topid);
}
