package io.github.areebgillani.controllers;

import io.github.areebgillani.boost.aspects.GetMapping;
import io.github.areebgillani.boost.aspects.PostMapping;
import io.github.areebgillani.boost.aspects.RequestParam;
import io.github.areebgillani.boost.aspects.RestController;
import io.github.areebgillani.boost.AbstractController;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

@RestController
public class ExampleController extends AbstractController {
    @GetMapping("/sayHi")
    public String sayHi(){
        return "hi";
    }
    @GetMapping("/sayHello")
    public String sayHello(@RequestParam("username") String user){
        return "Hello "+user;
    }
    @PostMapping("/sayHiToUser")
    public String sayHiToUser(JsonObject body){
        return "Hi! " +body.getString("username");
    }
    @PostMapping("/replyHiToUser")
    public void replyHiToUser(JsonObject body, RoutingContext context){
        eventBus.request("MyTopic", body, reply->{
            if(reply.succeeded()){
                context.json(reply.result().body());
            }
        });
    }
    @PostMapping("/someURL")
    public void configVerticle(JsonObject body, RoutingContext context){
        eventBus.request("someOtherTopic", body, reply -> {
            if (reply.succeeded()) {
                context.json(reply.result().body());
            } else {
                context.response().setStatusCode(500).end(reply.cause().getMessage());
            }
        });
    }

}
