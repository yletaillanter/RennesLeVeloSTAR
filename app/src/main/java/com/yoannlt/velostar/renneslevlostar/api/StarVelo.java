package com.yoannlt.velostar.renneslevlostar.api;

import com.yoannlt.velostar.renneslevlostar.model.JSONStationResponse;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by yoannlt on 21/02/2016.
 */
public interface StarVelo {

    // SYNC + RxJava Observable
    @GET("/?dataset=vls-stations-etat-tr&rows=90&sort=idstation&facet=nom&facet=etat&facet=nombreemplacementsactuels&facet=nombreemplacementsdisponibles&facet=nombrevelosdisponibles")
    Observable<JSONStationResponse> getAllStations();
}
