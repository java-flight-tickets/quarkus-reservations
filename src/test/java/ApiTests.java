import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import vao.Reservation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ApiTests {

    @Test
    public void testCreateReservationEndpoint() {
        Reservation mockReservation = new Reservation("2024-03-30", "user123", "ticket456");

        given()
                .contentType(ContentType.JSON)
                .body(mockReservation)
                .when().post("/reservations")
                .then()
                .statusCode(201)
                .body("date", equalTo(mockReservation.getDate()),
                        "userId", equalTo(mockReservation.getUserId()),
                        "ticketId", equalTo(mockReservation.getTicketId()));
    }

    @Test
    public void testListAllReservationsEndpoint() {
        given()
                .when().get("/reservations")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThanOrEqualTo(0)));
    }
}
