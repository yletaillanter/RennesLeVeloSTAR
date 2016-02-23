package com.yoannlt.velostar.renneslevlostar.api;

import com.yoannlt.velostar.renneslevlostar.model.StationResponse;

import retrofit.RestAdapter;
import retrofit.http.GET;

/**
 * Created by yoannlt on 21/02/2016.
 */
public class ApiManager {

    private interface ApiManagerService {
        @GET("/?dataset=vls-stations-etat-tr&rows=90&sort=idstation&facet=nom&facet=etat&facet=nombreemplacementsactuels&facet=nombreemplacementsdisponibles&facet=nombrevelosdisponibles")
        StationResponse getAllStations();
    }

    private static final RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint("https://data.explore.star.fr/api/records/1.0/search")
            .build();

    private static final ApiManagerService apiManager = restAdapter.create(ApiManagerService.class);
}
