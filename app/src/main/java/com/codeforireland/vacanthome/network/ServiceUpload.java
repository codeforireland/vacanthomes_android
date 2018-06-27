package com.codeforireland.vacanthome.network;

import com.codeforireland.vacanthome.model.PostNewHomeData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.codeforireland.vacanthome.network.ApiUtils.SERVICE_CALL_UPLOAD;

public interface ServiceUpload {

    @POST(SERVICE_CALL_UPLOAD)
    Call<ServiceUpload> uploadNewHomeCall(@Body PostNewHomeData newHomeData);
}
