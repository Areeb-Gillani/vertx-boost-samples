package io.github.areebgillani;

import io.github.areebgillani.boost.Booster;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class Application extends AbstractVerticle {
    Logger logger = LoggerFactory.getLogger(Application.class);
    Vertx vertx;
    Router router;
    public static JsonObject config;
    @Override
    public void start() throws Exception {
        super.start();
        logger.info("Starting Vertx Application");
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(config().getInteger("port", 8080))
                .onSuccess(server -> logger.info(("HTTP server started at port: " + server.actualPort())))
                .onFailure(failed -> System.out.println(failed.getMessage()));
    }
    public void init() {
        vertx = Vertx.vertx();
        router = Router.router(vertx);
        logger.info("PID: " + ProcessHandle.current().pid());
        ConfigRetriever.create(vertx, initRetrieverConfig()).getConfig().onComplete(ar -> {
            if (ar.failed()) {
                logger.error("Failed to retrieve configuration..");
            } else {
                config = ar.result();

                logger.info("Starting the app with config.");

                vertx.deployVerticle(this, new DeploymentOptions().setConfig(config))
                        .onSuccess(resp->{
                            Context cn = Vertx.currentContext();
                            Booster booster = new Booster(vertx, router, config);
                            try {
                                booster.boost(this.getClass().getPackage().getName());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .onFailure(failed -> System.out.println(failed.getMessage()));
            }
        });
    }
    private ConfigRetrieverOptions initRetrieverConfig() {
        return new ConfigRetrieverOptions()
                .addStore(new ConfigStoreOptions()
                        .setType("file")
                        .setOptional(true)
                        .setConfig(new JsonObject().put("path", "config.json")))
                .addStore(new ConfigStoreOptions().setType("sys"));
    }
}
