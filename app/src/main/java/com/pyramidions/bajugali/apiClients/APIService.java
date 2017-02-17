package com.pyramidions.bajugali.apiClients;


import com.pyramidions.bajugali.dataModels.ModelDataNotice;
import com.pyramidions.bajugali.dataModels.SignInResponceModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by User on 13-Feb-17.
 */

public interface APIService {
    @POST("users_signin")
    @FormUrlEncoded
    Call<SignInResponceModel> SIGN_IN_RESPONCE_MODEL_CALL (@Field("X-API-KEY") String Xpi,
                                                           @Field("userid") String userid,
                                                           @Field("deviceid") String deviceid,
                                                           @Field("password") String password,
                                                           @Field("mobileoremail") String mobileoremail);
    @POST("noticeboard")
    @FormUrlEncoded
    Call<ModelDataNotice> MODEL_DATA_NOTICE_CALL(@Field("X-API-KEY") String Xpi,
                                                 @Field("userid") String userid,
                                                 @Field("accesstoken") String accesstoken);
}
