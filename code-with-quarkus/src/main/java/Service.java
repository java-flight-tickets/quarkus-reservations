import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import vao.Reservation;
import dao.ReservationRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class Service {

    @Inject
    ReservationRepository reservationRepository;

    public Uni<List<dto.Reservation>> listAllReservations() {
        return reservationRepository.listAll()
                .onItem().transformToUni(reservations -> Uni.createFrom().item(
                        reservations.stream()
                                .map(Reservation::toDto)
                                .collect(Collectors.toList())
                ));
    }

    @Inject
    @Channel("generated-reservations")
    Emitter<Reservation> reservationEmitter;

    public Uni<Reservation> createReservation(String date, String userId, String ticketId) {
        Reservation reservation = new Reservation(date, userId, ticketId);
        return reservationRepository.persist(reservation)
                .onItem().invoke(() -> reservationEmitter.send(reservation))
                .onItem().transform(ignore -> reservation);
    }


    public Uni<dto.Reservation> updateReservation(String id, String date, String userId, String ticketId) {
        return reservationRepository.findById(new ObjectId(id))
                .onItem().transformToUni(existingReservation -> {
                    if (existingReservation == null) {
                        return Uni.createFrom().nullItem();
                    }
                    existingReservation.setDate(date);
                    existingReservation.setUserId(userId);
                    existingReservation.setTicketId(ticketId);
                    return reservationRepository.update(existingReservation)
                            .onItem().transform(updated -> existingReservation.toDto());
                });
    }

    public Uni<Boolean> deleteReservation(String id) {
        return reservationRepository.deleteById(new ObjectId(id))
                .onItem().transform(deleted -> deleted);
    }
}
