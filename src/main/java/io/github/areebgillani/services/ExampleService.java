package io.github.areebgillani.services;

import io.github.areebgillani.Application;
import io.github.areebgillani.aspects.Autowired;
import io.github.areebgillani.aspects.Service;
import io.github.areebgillani.mappers.Mapper;
import io.github.areebgillani.models.Test;
import io.github.areebgillani.repositories.DatabaseRepo;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Tuple;
import io.vertx.sqlclient.templates.TupleMapper;

import java.util.function.Function;

@Service("ExampleWorker") // It is the same name that is described in configuration.
public class ExampleService extends AbstractVerticle {
    @Autowired
    DatabaseRepo myRepo;
    @Override
    public void start(){
        Vertx.currentContext().owner().eventBus().consumer("MyTopic", this::replyHiToUser);
    }
    private void replyHiToUser(Message<Object> message){
        JsonObject vertxJsonObject = (JsonObject) message.body();
        Test t = new Test();
        t.setEntryBy("Test");
        t.setEntryTime(System.currentTimeMillis()+"");
        t.setId(Math.random()+"");
        t.setMessage("Message saved for "+vertxJsonObject.getString("username"));
        myRepo.save(t, "Insert into Test values (#{id}, #{message}, #{entry_by}, #{entry_time})", Mapper.getTestMapper());
        message.reply("Data Saved");
    }
}
