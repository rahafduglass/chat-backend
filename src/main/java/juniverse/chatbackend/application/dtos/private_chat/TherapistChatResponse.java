package juniverse.chatbackend.application.dtos.private_chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TherapistChatResponse {

    private Long id;

    private String userUsername;

    private String userFirstName;

    private String userLastName;

    private Integer therapistUnreadMessagesCount;

}
// TO BE ADDED:
// last message in that chat
// the date of the last message
