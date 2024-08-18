package io.github.areebgillani.repositories;

import io.github.areebgillani.aspects.Repository;
import io.github.areebgillani.db.boost.CrudRepository;
import io.github.areebgillani.models.Test;
import io.github.areebgillani.models.TestParametersMapper;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.concurrent.CompletableFuture;

@Repository("Primary")
public class DatabaseRepo extends CrudRepository<Test> {
    public DatabaseRepo(String connectionName, JsonObject config) {
        super(connectionName, config);
    }
    public CompletableFuture<Boolean> saveData(JsonObject vertxJsonObject) {
        Test t = new Test();
        t.setEntryBy("Test");
        t.setEntryTime(System.currentTimeMillis() + "");
        t.setId(Math.random() + "");
        t.setMessage("Message saved for " + vertxJsonObject.getString("username"));
        return save(t, "Insert into Test values (#{id}, #{message}, #{entry_by}, #{entry_time})", TestParametersMapper.INSTANCE);
    }
}
