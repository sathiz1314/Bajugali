package com.pyramidions.bajugali.apiClients;

/**
 * Created by User on 13-Feb-17.
 */

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://dazecorp.com/demos/bajugali_api/index.php/api/bajugali_api/";
   // public static final String BASE_URL = "http://35.163.72.7/bajugali_api/index.php/api/bajugali_api/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
