package io.github.areebgillani;

import io.github.areebgillani.boost.BoostApplication;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class Application extends BoostApplication {
    Logger logger = LoggerFactory.getLogger(Application.class);

    @Override
    public void start() throws Exception {
        super.start();
        deployApplication("config.json");
    }

    public static void main(String[] args) {
        run(Application.class, args);
    }
}
