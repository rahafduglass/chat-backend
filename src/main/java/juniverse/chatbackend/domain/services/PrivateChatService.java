package juniverse.chatbackend.domain.services;

import juniverse.chatbackend.application.dtos.private_chat.TherapistChatResponse;
import juniverse.chatbackend.domain.mappers.PrivateChatMapper;
import juniverse.chatbackend.domain.models.PrivateChatModel;
import juniverse.chatbackend.domain.provider.IdentityProvider;
import juniverse.chatbackend.persistance.entities.PrivateChatEntity;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import juniverse.chatbackend.persistance.repositories.MessageRepository;
import juniverse.chatbackend.persistance.repositories.PrivateChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateChatService {
    private final PrivateChatRepository privateChatRepository;
    private final PrivateChatMapper privateChatMapper;
    private final MessageRepository messageRepository;
    private final IdentityProvider identityProvider;


    public PrivateChatModel createChat(Long userId) {
        PrivateChatModel privateChatTemp = new PrivateChatModel();
        privateChatTemp.setUserId(userId);
        Long therapistId = 2L;
        privateChatTemp.setTherapistId(therapistId);
        return privateChatRepository.create(privateChatTemp);
    }

    public List<TherapistChatResponse> getAllTherapistChats() {

        //retrieve therapist details
        SysUserEntity sysUserEntity = identityProvider.currentIdentity();

        //retrieve all chats
        List<Object[]> results = privateChatRepository.findAllByTherapistId(sysUserEntity.getId());

        //extracting then mapping results(entities) to (DTO(response)) --- > SHOULD BE FIXED to (models)
        return results.stream().map(row -> {

            // Extract PrivateChatEntity from list row[0]
            PrivateChatEntity chat = (PrivateChatEntity) row[0];

            //mapping
            TherapistChatResponse therapistChatResponse = privateChatMapper.entityToTherapistChatResponse(chat);

            //retrieve and manually map unreadMessagesCount ---> FIND A BETTER WAY
            therapistChatResponse.setUnreadMessagesCount(messageRepository.getNumOfUnreadMessagesByChatAndReceiver(chat.getId(), sysUserEntity.getId()));

            //return sing response to be collected in List<Response>
            return therapistChatResponse;
        }).collect(Collectors.toList());
    }

    public PrivateChatModel getChat() {
        //retrieve current user
        SysUserEntity currentUser = identityProvider.currentIdentity();

        //retrieve private chat using current user
        PrivateChatEntity chat = privateChatRepository.findByUser(currentUser);

        //check if not null
        if (chat == null) throw new RuntimeException("private-chat not found");

        //privateChatModel
        PrivateChatModel privateChatModel=privateChatMapper.entityToModel(chat);

        //retrieve and manually map unreadMessagesCount ---> FIND A BETTER WAY
        privateChatModel.setUserUnreadMessagesCount(messageRepository.getNumOfUnreadMessagesByChatAndReceiver(chat.getId(), currentUser.getId()));

        //mapping entity to our model
        return privateChatModel;
    }

    public Boolean marChatAsRead(Long chatId) {
        return messageRepository.markMessagesAsRead(identityProvider.currentIdentity().getId(), chatId);
    }

    public PrivateChatModel getChatById(Long privateChatId) {
        return privateChatMapper.entityToModel(privateChatRepository.findById(privateChatId));
    }
}
