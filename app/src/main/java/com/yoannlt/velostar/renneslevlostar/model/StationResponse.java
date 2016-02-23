package com.yoannlt.velostar.renneslevlostar.model;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yoannlt on 21/02/2016.
 */
public class StationResponse {
    private int nhits;
    private Record[] records;

    public StationResponse() {
    }

    public StationResponse(int nhits, Record[] records) {
        this.nhits = nhits;
        this.records = records;
    }

    public int getNhits() {
        return nhits;
    }

    public void setNhits(int nhits) {
        this.nhits = nhits;
    }

    public Record[] getRecords() {
        return records;
    }

    public void setRecords(Record[] records) {
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