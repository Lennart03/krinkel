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

//    @Mock
//    private RegistrationParticipantRepository repository;

    @Mock
    private ChiroUserAdapter adapter;


    @Before
    public void init() {
        user = new User("Ziggy", "test", "user", "ad1", "abcdefg", true, "AG0103");

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

//    @Test
//    public void registeredIsSetToFalseWhenUserIsNotPresentInDatabase(){
//        Mockito.when(repository.findByAdNumber("123")).thenReturn(null);
//    }
//
//    @Test
//    public void registeredIsSetToTrueWhenUserIsPresentInDatabase(){
//        Mockito.when(repository.findByAdNumber("321")).thenReturn(new RegistrationParticipant());
//    }
}
