package com.example.realmattendence;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DataModel extends RealmObject {

    @PrimaryKey
    private long id;
    private String sname;
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataModel() {
    }
}
