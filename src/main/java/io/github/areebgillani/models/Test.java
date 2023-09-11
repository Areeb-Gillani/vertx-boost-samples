package io.github.areebgillani.models;


import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.templates.annotations.Column;
import io.vertx.sqlclient.templates.annotations.ParametersMapped;

@DataObject
@ParametersMapped
public class Test {
    @Column(name="message")
    String message;
    @Column(name="entry_time")
    String entryTime;
    @Column(name="id")
    String id;
    @Column(name="entry_by")
    String entryBy;

    public Test() {
    }
    public Test(JsonObject obj) {
    }

    public Test(String message, String entryTime, String id, String entryBy) {
        this.message = message;
        this.entryTime = entryTime;
        this.id = id;
        this.entryBy = entryBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntryBy() {
        return entryBy;
    }

    public void setEntryBy(String entryBy) {
        this.entryBy = entryBy;
    }
}
