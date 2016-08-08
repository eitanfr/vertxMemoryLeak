import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

/**
 * Created by eitan on 09/01/2016.
 */
public class MySecondVertical extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        System.out.println("MySecondVertical");

        EventBus eventBus = vertx.eventBus();

        eventBus.publish("test","hello eitan");
    }
}
