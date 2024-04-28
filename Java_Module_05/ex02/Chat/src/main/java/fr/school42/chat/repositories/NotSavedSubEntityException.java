package fr.school42.chat.repositories;

public class NotSavedSubEntityException extends RuntimeException {

    public NotSavedSubEntityException(String message) {
        super(message);
    }


    public NotSavedSubEntityException(String message, Throwable cause) {
        super(message, cause);
    }

}
