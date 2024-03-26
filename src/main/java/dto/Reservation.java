package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.bson.types.ObjectId;

@JsonInclude(value = Include.NON_NULL)
public record Reservation(ObjectId id, String date, String userId, String ticketId) {

}
