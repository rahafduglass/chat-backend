package org.example.chatbackend.persistance.repositories;


import org.example.chatbackend.domain.models.MessageModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository {

    MessageModel sendMessage(MessageModel messageModel);

    MessageModel updateMessageStatus(MessageModel messageModel);


    MessageModel findById(Long id);

    List<MessageModel> findAllByPrivateChatId(Long id);


}
