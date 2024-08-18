package io.github.areebgillani.services;

import io.github.areebgillani.aspects.Autowired;
import io.github.areebgillani.aspects.Service;
import io.github.areebgillani.boost.AbstractService;
import io.github.areebgillani.repositories.DatabaseRepo;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

@Service("ExampleWorker") // It is the same name that is described in configuration.
public class ExampleService extends AbstractService {
    @Autowired
    DatabaseRepo myRepo;

    @Override
    public void bindTopics() {
        eventBus.consumer("MyTopic", this::replyHiToUser);
        eventBus.consumer("someOtherTopic", this::someOtherTopic);
    }

    private void replyHiToUser(Message<Object> message) {
        JsonObject vertxJsonObject = (JsonObject) message.body();
        try {
            message.reply(myRepo.saveData(vertxJsonObject).get()?"Data Saved":"Data saving error");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void someOtherTopic(Message<JsonObject> tMessage) {
        //TODO add your code here
    }
}
