package fr.school42.sockets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import fr.school42.sockets.models.Message;
import fr.school42.sockets.repositories.MessagesRepository;
import fr.school42.sockets.repositories.UsersRepository;
import fr.school42.sockets.models.User;

@Component
public class MessagesServiceImpl implements MessagesService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private MessagesRepository messageRepository;

    @Override
    public Message saveMessage(String username, String text) {

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username is empty");
        }
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text is empty");
        }

        User user = usersRepository.findByUsername(username).orElseThrow();
        Message message = new Message(user, text);
        messageRepository.save(message);

        return message;

    }
}