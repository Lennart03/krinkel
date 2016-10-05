package com.realdolmen.chiro.service;


import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private User user;

    @InjectMocks
    private UserService service;

    @Mock
    private RegistrationParticipantRepository repository;

    @Mock
    private ChiroUserAdapter adapter;


    @Before
    public void init() {
        //create user that mockito can return
        user = new User("Ziggy", "test", "user", "ad1", "abcdefg", false, "AG0103");

    }

    @Test
    public void getUserReturnsUserWhenCredentialsAreCorrect() {
        Mockito.when(adapter.getUser("Ziggy", "test")).thenReturn(user);
        Assert.assertNotNull(service.getUser("Ziggy", "test"));
    }

    @Test
    public void getUserReturnsNullWhenCredentialsAreFalse() {
        Mockito.when(adapter.getUser("Ziggy", "password")).thenReturn(null);
        Assert.assertNull(service.getUser("Ziggy", "password"));
    }

    @Test
    public void registeredIsSetToFalseWhenUserIsNotPresentInDatabase(){
        Mockito.when(repository.findByAdNumber(user.getAdNumber())).thenReturn(null);
        Mockito.when(adapter.getUser("Ziggy", "test")).thenReturn(user);
        User u = service.getUser("Ziggy", "test");
        Assert.assertFalse(u.getSubscribed());
    }

    @Test
    public void registeredIsSetToTrueWhenUserIsPresentInDatabase(){
        Mockito.when(repository.findByAdNumber(user.getAdNumber())).thenReturn(new RegistrationParticipant());
        Mockito.when(adapter.getUser("Ziggy", "test")).thenReturn(user);
        User u = service.getUser("Ziggy", "test");
        Assert.assertTrue(u.getSubscribed());
    }
}
