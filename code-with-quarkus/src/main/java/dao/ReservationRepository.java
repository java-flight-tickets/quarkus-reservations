package dao;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import vao.Reservation;

@ApplicationScoped
public class ReservationRepository implements ReactivePanacheMongoRepository<Reservation> {
}