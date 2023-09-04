package io.github.areebgillani.repositories;

import io.github.areebgillani.aspects.Repository;
import io.github.areebgillani.db.boost.CrudRepository;
import io.github.areebgillani.models.Test;
import io.vertx.core.json.JsonObject;

@Repository("Primary")
public class DatabaseRepo extends CrudRepository<Test> {
    public DatabaseRepo(String connectionName, JsonObject config) {
        super(connectionName, config);
    }
}
