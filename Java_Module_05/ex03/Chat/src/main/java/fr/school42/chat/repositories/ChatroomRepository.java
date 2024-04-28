package fr.school42.chat.repositories;

import java.util.Optional;

import fr.school42.chat.models.Chatroom;

public interface ChatroomRepository {
    Optional<Chatroom> findById(Long id) throws RuntimeException;

}
