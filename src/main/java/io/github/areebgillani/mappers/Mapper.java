package io.github.areebgillani.mappers;

import io.github.areebgillani.models.Test;
import io.vertx.sqlclient.templates.TupleMapper;

import java.util.HashMap;
import java.util.Map;

public class Mapper {

    public static TupleMapper<Test> getTestMapper(){
        return TupleMapper.mapper(test -> {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("id", test.getId());
            parameters.put("message", test.getMessage());
            parameters.put("entry_by", test.getEntryBy());
            parameters.put("entry_time", test.getEntryTime());
            return parameters;
        });
    }
}
