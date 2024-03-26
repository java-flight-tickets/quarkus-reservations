package vao;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@MongoEntity(collection = "reservations")
public class Reservation extends PanacheMongoEntityBase {

    public Reservation(String date, String userId, String ticketId) {
        this.date = date;
        this.userId = userId;
        this.ticketId = ticketId;
    }

    public dto.Reservation toDto() {
        return new dto.Reservation(
                getId(),
                getDate(),
                getUserId(),
                getTicketId());
    }

    @BsonId
    protected ObjectId id;
    protected String date;
    protected String userId;
    protected String ticketId;
}