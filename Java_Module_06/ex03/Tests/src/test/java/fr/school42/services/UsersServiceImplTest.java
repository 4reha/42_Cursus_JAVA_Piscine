package fr.school42.services;

import fr.school42.exceptions.AlreadyAuthenticatedException;
import fr.school42.exceptions.EntityNotFoundException;
import fr.school42.models.User;
import fr.school42.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsersServiceImplTest {
  private UsersRepository usersRepository;
  private UsersServiceImpl usersService;

  @BeforeEach
  public void setup() {
    usersRepository = Mockito.mock(UsersRepository.class);
    usersService = new UsersServiceImpl(usersRepository);
  }

  @Test
  public void authenticate_correctLoginAndPassword() throws EntityNotFoundException {
    User user = new User();
    user.setLogin("test");
    user.setPassword("password");
    user.setAuthenticated(false);

    when(usersRepository.findByLogin("test")).thenReturn(user);

    assertTrue(usersService.authenticate("test", "password"));
    verify(usersRepository, times(1)).update(user);
  }

  @Test
  public void authenticate_incorrectLogin() {

    when(usersRepository.findByLogin("wrong")).thenThrow(new EntityNotFoundException());

    assertThrows(EntityNotFoundException.class, () -> usersService.authenticate("wrong", "password"));
  }

  @Test
  public void authenticate_alreadyAuthenticated() {
    User user = new User();
    user.setLogin("test");
    user.setPassword("password");
    user.setAuthenticated(true);

    when(usersRepository.findByLogin("test")).thenReturn(user);

    assertThrows(AlreadyAuthenticatedException.class, () -> usersService.authenticate("test", "password"));
  }


  @Test
  public void authenticate_incorrectPassword() throws EntityNotFoundException {
    User user = new User();
    user.setLogin("test");
    user.setPassword("password");
    user.setAuthenticated(false);

    when(usersRepository.findByLogin("test")).thenReturn(user);

    assertFalse(usersService.authenticate("test", "wrong"));
  }
}