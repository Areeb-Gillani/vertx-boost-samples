package io.github.areebgillani.controllers;

import io.github.areebgillani.aspects.GetMapping;
import io.github.areebgillani.aspects.PostMapping;
import io.github.areebgillani.aspects.RequestParam;
import io.github.areebgillani.aspects.RestController;
import io.github.areebgillani.boost.AbstractController;
import io.github.areebgillani.boost.cache.HttpRequest;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
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
    @PostMapping("/configVerticle")
    public void configVerticle(JsonObject body, HttpRequest request){
        eventBus.request("VerticleConfig", body, request.getResponseHandler());
    }

}
