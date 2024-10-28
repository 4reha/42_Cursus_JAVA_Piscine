package fr.school42.sockets.services;

import fr.school42.sockets.models.Message;

public interface MessagesService {
    Message saveMessage(String login, String text);
}
