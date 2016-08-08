import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.impl.EventBusImpl;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by eitan on 05/01/2016.
 */
public class main {
    private static final int NUM_OF_ENTITIES = 1000;
    private static final int SIZE = 8000; // + 700
    public static final int SLEEP = 500;
    private  static Vertx vertx;
    private  static Router router;
    private  static HttpServer httpServer;

    public static void main(String[] args) throws InterruptedException {
        vertx = Vertx.vertx();
        router = Router.router(vertx);
        BridgeOptions opts = new BridgeOptions()
                .addOutboundPermitted(new PermittedOptions().setAddress("feed"));

        SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
        sockJSHandler.bridge(opts);
        router.route("/eventbus/*").handler(sockJSHandler);

        router.route().handler(StaticHandler.create());

        httpServer = vertx.createHttpServer(new HttpServerOptions().setUsePooledBuffers(false));
        httpServer.requestHandler(router::accept).listen(8080);

        EventBus eb = vertx.eventBus();

        byte[] bytes = new byte[SIZE];
        Arrays.fill(bytes, (byte) 1);
        while (true){
            for (int i = 0; i < NUM_OF_ENTITIES ; i++) {
                eb.publish("feed",new JsonObject().put("now",bytes));
            }

            Thread.sleep(SLEEP);
        }

//
//        while (true){
//            JsonArray objects = new JsonArray();
//            for (int i = 0; i < NUM_OF_ENTITIES ; i++) {
//
//                objects.add(new JsonObject().put("now",bytes));
//                if (i % 100 == 0){
//                    eb.publish("feed",objects);
//                    objects = new JsonArray();
//                }
//            }
//            Thread.sleep(SLEEP);
//        }
    }
}
