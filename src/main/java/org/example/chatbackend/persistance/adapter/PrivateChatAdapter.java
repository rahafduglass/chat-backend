package org.example.chatbackend.persistance.adapter;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.domain.mappers.PrivateChatMapper;
import org.example.chatbackend.domain.mappers.UserMapper;
import org.example.chatbackend.domain.models.PrivateChatModel;
import org.example.chatbackend.domain.models.UserModel;
import org.example.chatbackend.persistance.jpa.PrivateChatJpaRepository;
import org.example.chatbackend.persistance.repositories.PrivateChatRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PrivateChatAdapter implements PrivateChatRepository {

    private final PrivateChatJpaRepository privateChatJpaRepository;
    private final UserMapper userMapper;
    private final PrivateChatMapper privateChatMapper;


    @Override
    public PrivateChatModel findPrivateChatByUser(UserModel sender) {
        return privateChatMapper.entityToModel(privateChatJpaRepository.findPrivateChatEntityByUser(userMapper.modelToEntity(sender)));
    }

    @Override
    public PrivateChatModel createPrivateChat(PrivateChatModel privateChat) {
        return privateChatMapper.entityToModel(privateChatJpaRepository.save(privateChatMapper.modelToEntity(privateChat)));
    }

    @Override
    public List<PrivateChatModel> findAllByTherapistId(Long therapistId) {
        return privateChatMapper.listOfEntitiesToListOfModels(privateChatJpaRepository.findAllByTherapistId(therapistId));
    }


}
