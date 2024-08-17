package io.github.areebgillani;

import io.github.areebgillani.boost.BoostApplication;
import io.vertx.core.Launcher;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;

public class Application extends BoostApplication {
    Logger logger = LoggerFactory.getLogger(Application.class);

    @Override
    public void start() throws Exception {
        super.start();
        run();
    }

    public static void main(String[] args) {
        Launcher l = new Launcher();
        l.dispatch(new String[]{"run", Application.class.getCanonicalName(), "--launcher-class="+Launcher.class.getCanonicalName()});
    }
}
