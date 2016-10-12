package com.realdolmen.chiro.service;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.Role;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import io.jsonwebtoken.Jwt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import io.jsonwebtoken.Jwts;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    private UserService service;

    @Mock
    private ChiroUserAdapter adapter;

    @Mock
    private RegistrationParticipantRepository repo;

    private final static String TEST_AD_NUMBER = "apiuehf54aiawuef";
    private User u = new User();


    @Before
    public void setUp(){
        u.setAdNumber(TEST_AD_NUMBER);
    }


    @Test
    public void getUserAsksChiroUserAdapterForUserWithCorrectAdNumber() {
        Mockito.when(adapter.getChiroUser(TEST_AD_NUMBER)).thenReturn(new User());
        service.getUser(TEST_AD_NUMBER);
        Mockito.verify(adapter, times(1)).getChiroUser(TEST_AD_NUMBER);
    }


    @Test
    public void getUserReturnsUserGivenByChiroAdapter() {
        Mockito.when(adapter.getChiroUser(TEST_AD_NUMBER)).thenReturn(u);
        User user = service.getUser(TEST_AD_NUMBER);
        Assert.assertSame(user, u);
    }

    @Test
    public void getUserChecksIfUserIsRegisteredWhenUserIsNotNull() {
        Mockito.when(adapter.getChiroUser(TEST_AD_NUMBER)).thenReturn(u);
        service.getUser(TEST_AD_NUMBER);
        Mockito.verify(repo, times(1)).findByAdNumber(TEST_AD_NUMBER);
    }


    @Test
    public void getUserSetsPaidToFalseWhenUserHasNotPaid() {
        RegistrationParticipant participant = new RegistrationParticipant();
        participant.setStatus(Status.TO_BE_PAID);
        Mockito.when(adapter.getChiroUser(TEST_AD_NUMBER)).thenReturn(u);
        Mockito.when(repo.findByAdNumber(TEST_AD_NUMBER)).thenReturn(participant);

        User user = service.getUser(TEST_AD_NUMBER);
        Assert.assertSame(u, user);
        Assert.assertFalse(user.hasPaid());
    }

    @Test
    public void getUserSetsPaidToTrueWhenUserHasPaid() {
        RegistrationParticipant participant = new RegistrationParticipant();
        participant.setStatus(Status.PAID);
        Mockito.when(adapter.getChiroUser(TEST_AD_NUMBER)).thenReturn(u);
        Mockito.when(repo.findByAdNumber(TEST_AD_NUMBER)).thenReturn(participant);

        User user = service.getUser(TEST_AD_NUMBER);
        Assert.assertSame(u, user);
        Assert.assertTrue(user.hasPaid());
    }

    @Test
    public void getUserSetsRegisteredAndPaidToFalseWhenUserIsNotFoundInDatabase() {
        Mockito.when(adapter.getChiroUser(TEST_AD_NUMBER)).thenReturn(u);
        Mockito.when(repo.findByAdNumber(TEST_AD_NUMBER)).thenReturn(null);

        User user = service.getUser(TEST_AD_NUMBER);
        Assert.assertSame(u, user);
        Assert.assertFalse(u.isRegistered());
        Assert.assertFalse(u.hasPaid());
    }

    @Test
    public void getUserSetsRegisteredToTrueWhenFoundInDatabase() {
        RegistrationParticipant participant = new RegistrationParticipant();
        participant.setStatus(Status.PAID);
        Mockito.when(adapter.getChiroUser(TEST_AD_NUMBER)).thenReturn(u);
        Mockito.when(repo.findByAdNumber(TEST_AD_NUMBER)).thenReturn(participant);

        User user = service.getUser(TEST_AD_NUMBER);
        Assert.assertSame(u, user);
        Assert.assertTrue(user.isRegistered());
    }
}
