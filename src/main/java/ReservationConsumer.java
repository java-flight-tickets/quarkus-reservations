import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReservationConsumer {

    @Incoming("reservations")
    public void consume(JsonObject message) {
        System.out.println("Received message: " + message.encodePrettily());
    }
}
