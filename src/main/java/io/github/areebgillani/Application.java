package io.github.areebgillani;

import io.github.areebgillani.boost.Booster;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.concurrent.CountDownLatch;

public class Application extends AbstractVerticle {
    Logger logger = LoggerFactory.getLogger(Application.class);
    Vertx vertx;
    Router router;
    public JsonObject config;

    @Override
    public void start() throws Exception {
        super.start();
        startServer();
    }

    public Application() throws InterruptedException {
        vertx = Vertx.vertx();
        router = Router.router(vertx);
        loadConfig();
        scanAndDeployVerticles();
    }

    private void loadConfig() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ConfigRetriever.create(vertx, initRetrieverConfig()).getConfig().onComplete(ar -> {
            if (ar.failed()) {
                latch.countDown();
                logger.error("Failed to retrieve configuration.");
            } else {
                config = ar.result();
                latch.countDown();
                logger.info("Configuration loaded.");
            }
        });
        latch.await();
    }

    private void startServer() {
        logger.info("Starting Vertx Application Server...");
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(config().getInteger("port", 8080))
                .onSuccess(server -> logger.info(("HTTP server started at port: " + server.actualPort())))
                .onFailure(failed -> System.out.println(failed.getMessage()));

    }

    private void scanAndDeployVerticles() {
        if (config != null) {
            vertx.deployVerticle(this, new DeploymentOptions().setConfig(config))
                    .onSuccess(resp -> {
                        Booster booster = new Booster(vertx, router, config);
                        try {
                            booster.boost(this.getClass().getPackage().getName());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .onFailure(failed -> System.out.println(failed.getMessage()));
        } else
            logger.info("Unable to deploy verticles without proper config.");
    }

    private ConfigRetrieverOptions initRetrieverConfig() {
        return new ConfigRetrieverOptions()
                .addStore(new ConfigStoreOptions()
                        .setType("file")
                        .setOptional(true)
                        .setConfig(new JsonObject().put("path", "config.json")))
                .addStore(new ConfigStoreOptions().setType("sys"));
    }

    public static void main(String[] args) throws Exception {
        Application app = new Application();
        app.start();
    }
}
