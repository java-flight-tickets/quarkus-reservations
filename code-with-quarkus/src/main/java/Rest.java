import dto.Reservation;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Rest {

    @Inject
    Service reservationService;

    @GET
    public Uni<Response> listAllReservations() {
        return reservationService.listAllReservations()
                .onItem().transform(reservations -> Response.ok(reservations).build());
    }

    @POST
    public Uni<Response> createReservation(Reservation reservationDto) {
        return reservationService.createReservation(reservationDto.date(), reservationDto.userId(), reservationDto.ticketId())
                .onItem().transform(reservation -> Response.status(Response.Status.CREATED).entity(reservation).build());
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> updateReservation(@PathParam("id") String id, Reservation reservationDto) {
        return reservationService.updateReservation(id, reservationDto.date(), reservationDto.userId(), reservationDto.ticketId())
                .onItem().transform(reservation -> {
                    if (reservation != null) {
                        return Response.ok(reservation).build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                });
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteReservation(@PathParam("id") String id) {
        return reservationService.deleteReservation(id)
                .onItem().transform(deleted ->
                        deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build()
                );
    }
}
