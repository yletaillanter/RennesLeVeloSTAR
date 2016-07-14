package com.yoannlt.velostar.renneslevlostar.model;

import java.util.Arrays;

/**
 * Created by yoannlt on 21/02/2016.
 */
public class JSONStationResponse {
    private int nhits;
    private Enregistrement[] records;

    public JSONStationResponse() {
    }

    public JSONStationResponse(int nhits, Enregistrement[] records) {
        this.nhits = nhits;
        this.records = records;
    }

    public int getNhits() {
        return nhits;
    }

    public void setNhits(int nhits) {
        this.nhits = nhits;
    }

    public Enregistrement[] getRecords() {
        return records;
    }

    public void setRecords(Enregistrement[] records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "StationResponse{" +
                "nhits=" + nhits +
                ", records=" + Arrays.toString(records) +
                '}';
    }
}