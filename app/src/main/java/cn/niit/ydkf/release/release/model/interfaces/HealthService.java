package cn.niit.ydkf.release.release.model.interfaces;


import cn.niit.ydkf.release.release.model.entities.health.HealthBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HealthService {
    @GET("96-109")
    Call<HealthBean> getHealth(@Query("showapi_appid") int showapi_appid, @Query("showapi_sign") String showapi_sign);
}
