import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.logging.Log4jLogDelegateFactory;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Created by eitan on 09/01/2016.
 */
public class MyFirstVertical extends AbstractVerticle {
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Logger logger =LoggerFactory.getLogger(MyFirstVertical.class);

        logger.info("logged");
        System.out.println("MyFirstVertical");

        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("test",objectMessage -> System.out.println("got "+objectMessage.body()));
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
