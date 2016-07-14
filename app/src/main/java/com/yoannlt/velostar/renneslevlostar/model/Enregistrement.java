package com.yoannlt.velostar.renneslevlostar.model;

/**
 * Created by yoannlt on 23/02/2016.
 */
public class Enregistrement {
    private String datasetid;
    private String recordid;
    private Station fields;
    private String record_timestamp;

    public Enregistrement() {
    }

    public Enregistrement(String datasetid, String recordid, Station fields, String record_timestamp) {
        this.datasetid = datasetid;
        this.recordid = recordid;
        this.fields = fields;
        this.record_timestamp = record_timestamp;
    }

    public String getDatasetid() {
        return datasetid;
    }

    public void setDatasetid(String datasetid) {
        this.datasetid = datasetid;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public Station getFields() {
        return fields;
    }

    public void setFields(Station fields) {
        this.fields = fields;
    }

    public String getRecord_timestamp() {
        return record_timestamp;
    }

    public void setRecord_timestamp(String record_timestamp) {
        this.record_timestamp = record_timestamp;
    }

    @Override
    public String toString() {
        return "Record{" +
                "datasetid='" + datasetid + '\'' +
                ", recordid='" + recordid + '\'' +
                ", fields=" + fields +
                ", record_timestamp='" + record_timestamp + '\'' +
                '}';
    }
}
