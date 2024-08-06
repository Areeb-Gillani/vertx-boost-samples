package io.github.areebgillani.services;

import io.github.areebgillani.aspects.Autowired;
import io.github.areebgillani.aspects.Service;
import io.github.areebgillani.boost.AbstractService;
import io.github.areebgillani.models.Test;
import io.github.areebgillani.models.TestParametersMapper;
import io.github.areebgillani.repositories.DatabaseRepo;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

@Service("ExampleWorker") // It is the same name that is described in configuration.
public class ExampleService extends AbstractService {
    @Autowired
    DatabaseRepo myRepo;

    @Override
    public void bindTopics() {
        eventBus.consumer("MyTopic", this::replyHiToUser);
        eventBus.consumer("VerticleConfig", this::configVerticle);
    }

    private void configVerticle(Message<JsonObject> tMessage) {

    }

    private void replyHiToUser(Message<Object> message) {
        JsonObject vertxJsonObject = (JsonObject) message.body();
        Test t = new Test();
        t.setEntryBy("Test");
        t.setEntryTime(System.currentTimeMillis() + "");
        t.setId(Math.random() + "");
        t.setMessage("Message saved for " + vertxJsonObject.getString("username"));
        //myRepo.save(t, "Insert into Test values (#{id}, #{message}, #{entry_by}, #{entry_time})", Mapper.getTestMapper());
        myRepo.save(t, "Insert into Test values (#{id}, #{message}, #{entry_by}, #{entry_time})", TestParametersMapper.INSTANCE);
        message.reply("Data Saved");
    }
}
