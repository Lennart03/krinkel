package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.domain.vo.RolesAndUpperClasses;
import com.realdolmen.chiro.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ChiroUnitServiceSecurityTest {
    @InjectMocks
    private ChiroUnitServiceSecurity chiroUnitServiceSecurity;

    @Mock
    private UserService userService;

    ///////////////////////////////////////////////////
    //////          VERBONDEN (ONE PLOEG)      ////////
    ///////////////////////////////////////////////////
    @Test
    public void hasPermissionToFindVerbondenFailsWhenNoLoggedInUser() {
        boolean access = chiroUnitServiceSecurity.hasPermissionToFindVerbonden();
        assertEquals(false, access);
    }

    @Test
    public void hasPermissionToFindVerbondenSucceedsWhenLoggedIn() {
        User arne = new User();
        Mockito.when(userService.getCurrentUser()).thenReturn(arne);
        boolean access = chiroUnitServiceSecurity.hasPermissionToFindVerbonden();
        assertEquals(true, access);
    }

    @Test
    public void hasPermissionToSeeVerbondFailsWhenUserHasOneGroepAndIsNotInVerbondAndNotAdminAndNotNationaal() throws Exception {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0103");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("BG0000");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeVerbonden(chiroUnit);
        assertEquals(false, access);
    }

    @Test
    public void hasPermissionToSeeVerbondFailsWhenUserHasOneGewestAndIsNotInVerbondAndIsNotAdminAndNotNationaal() throws Exception {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GEWEST, "AG0000");
        rolesAndUpperClassesByStam.put("AG0400", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0400");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("BG0000");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeVerbonden(chiroUnit);
        assertEquals(false, access);
    }

    @Test
    public void hasPermissionToSeeVerbondFailsWhenUserHasOneVerbondAndIsNotInVerbondAndIsNotAdminAndNotNationaal() throws Exception {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.VERBOND, "AG0000");
        rolesAndUpperClassesByStam.put("AG0000", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0000");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("BG0000");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeVerbonden(chiroUnit);
        assertEquals(false, access);
    }

    @Test
    public void hasPermissionToSeeVerbondSuccessWhenUserHasOneGroepAndIsNationaal() throws Exception {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.VERBOND, "AG0000");
        rolesAndUpperClassesByStam.put("NAT", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("NAT");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("BG0000");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeVerbonden(chiroUnit);
        assertEquals(true, access);
    }

    @Test
    public void hasPermissionToSeeVerbondSuccessWhenUserHasOneGroepAndIsAdmin() throws Exception {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0103");
        mathias.setRole(SecurityRole.ADMIN);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("BG0000");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeVerbonden(chiroUnit);
        assertEquals(true, access);
    }

    @Test
    public void hasPermissionToSeeVerbondSuccessWhenUserHasOneGroepAndIsInVerbondAndNotAdminAndNotNationaal() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0103");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("AG0000");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeVerbonden(chiroUnit);
        assertEquals(true, access);
    }

    @Test
    public void hasPermissionToSeeVerbondSuccessWhenUserHasOneGewestAndIsInVerbondAndNotAdminAndNotNationaal() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GEWEST, "AG0000");
        rolesAndUpperClassesByStam.put("AG0400", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0400");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("AG0000");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeVerbonden(chiroUnit);
        assertEquals(true, access);
    }

    @Test
    public void hasPermissionToSeeVerbondSuccessWhenUserHasOneVerbondAndIsInVerbondAndIsNotAdminAndNotNationaal() throws Exception {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.VERBOND, "AG0000");
        rolesAndUpperClassesByStam.put("AG0000", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0000");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("AG0000");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeVerbonden(chiroUnit);
        assertEquals(true, access);
    }

    ///////////////////////////////////////////////////
    //////          GEWESTEN (ONE PLOEG)       ////////
    ///////////////////////////////////////////////////
    @Test
    public void hasPermissionToFindGewestenFailsWhenNoLoggedInUser() {
        boolean access = chiroUnitServiceSecurity.hasPermissionToFindUnits();
        assertEquals(false, access);
    }

    @Test
    public void hasPermissionToSeeGewestenFailsWhenUserHasOneGroepAndIsNotInGewestAndNotAdminAndNotNationaal() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0103");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("BG0400");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeUnits(chiroUnit);
        assertEquals(false, access);
    }

    @Test
    public void hasPermissionToSeeGewestenFailsWhenUserHasOneGewestAndIsNotTheSameAndNotAdminAndNotNationaal() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GEWEST, "AG0000");
        rolesAndUpperClassesByStam.put("AG0400", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0400");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("BG0400");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeUnits(chiroUnit);
        assertEquals(false, access);
    }

    @Test
    public void hasPermissionToSeeGewestenSuccessWhenUserHasOneVerbondAndIsNotTheSupperUnitAndNotAdminAndNotNationaal() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.VERBOND, "AG0000");
        rolesAndUpperClassesByStam.put("AG0000", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0000");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("BG0400");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeUnits(chiroUnit);
        assertEquals(true, access);
    }

    @Test
    public void hasPermissionToSeeGewestenSuccessWhenUserHasOneVerbondAndIsNationaal() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.NATIONAAL, "AG0000");
        rolesAndUpperClassesByStam.put("NAT", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("NAT");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("BG0400");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeUnits(chiroUnit);
        assertEquals(true, access);
    }

    @Test
    public void hasPermissionToSeeGewestenSuccessWhenUserHasOneVerbondAndIsAdmin() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.ADMIN, "AG0000");
        rolesAndUpperClassesByStam.put("AG0000", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0000");
        mathias.setRole(SecurityRole.ADMIN);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("BG0400");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeUnits(chiroUnit);
        assertEquals(true, access);
    }

    @Test
    public void hasPermissionToSeeGewestenSuccessWhenUserHasOneGroepAndIsInGewestAndNotAdminAndNotNationaal() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0103");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("AG0400");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeUnits(chiroUnit);
        assertEquals(true, access);
    }

    @Test
    public void hasPermissionToSeeGewestenSuccessWhenUserHasOneGewestpAndIsGewestAndNotAdminAndNotNationaal() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GEWEST, "AG0000");
        rolesAndUpperClassesByStam.put("AG0400", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0400");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("AG0400");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeUnits(chiroUnit);
        assertEquals(true, access);
    }

    ///////////////////////////////////////////////////
    //////           GROEPEN (ONE PLOEG)       ////////
    ///////////////////////////////////////////////////
    @Test
    public void hasPermissionToSeeGroepFailsWhenUserHasOneGroeppAndIsNotSameAndNotAdminAndNotNationaal() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0103");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit upperChiroUnit = new ChiroUnit();
        upperChiroUnit.setStam("AG0400");

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("AG0104");
        chiroUnit.setUpper(upperChiroUnit);

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeUnits(chiroUnit);
        assertEquals(false, access);
    }

    @Test
    public void hasPermissionToSeeGroepSuccessWhenUserHasOneGroepAndIsAdmin() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0103");
        mathias.setRole(SecurityRole.ADMIN);

        ChiroUnit upperChiroUnit = new ChiroUnit();
        upperChiroUnit.setStam("AG0400");

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("AG0104");
        chiroUnit.setUpper(upperChiroUnit);

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeUnits(chiroUnit);
        assertEquals(true, access);
    }

    @Test
    public void hasPermissionToSeeGroepSuccessWhenUserHasOneGroepAndIsNationaal() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.NATIONAAL, "");
        rolesAndUpperClassesByStam.put("NAT", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("NAT");
        mathias.setRole(SecurityRole.ADMIN);


        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("AG0104");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeUnits(chiroUnit);
        assertEquals(true, access);
    }

    @Test
    public void hasPermissionToSeeGroepSuccessWhenUserHasOneGroepAndIsNotSameAndNotAdminAndNotNationaal() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0103");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit upperChiroUnit = new ChiroUnit();
        upperChiroUnit.setStam("AG0400");

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("AG0103");
        chiroUnit.setUpper(upperChiroUnit);

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeUnits(chiroUnit);
        assertEquals(true, access);
    }

    @Test
    public void hasPermissionToSeeGroepSuccessWhenUserHasOneVerbond() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.VERBOND, "AG0000");
        rolesAndUpperClassesByStam.put("AG0000", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0000");
        mathias.setRole(SecurityRole.VERBOND);

        ChiroUnit upperChiroUnit = new ChiroUnit();
        upperChiroUnit.setStam("AG0400");

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("AG0103");
        chiroUnit.setUpper(upperChiroUnit);

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeUnits(chiroUnit);
        assertEquals(true, access);
    }

    ///////////////////////////////////////////////////
    //////      PARTICIPANTS (ONE PLOEG) GET   ////////
    ///////////////////////////////////////////////////
    @Test
    public void hasPermissionToGetParticipantsSuccessWhenUserHasOneGroepAndNotAdmin() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0103");
        mathias.setRole(SecurityRole.GROEP);

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToGetParticipants();
        assertEquals(true, access);
    }

    @Test
    public void hasPermissionToGetParticipantsSuccessWhenUserHasOneGroepAndAdmin() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0103");
        mathias.setRole(SecurityRole.ADMIN);

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToGetParticipants();
        assertEquals(true, access);
    }

    @Test
    public void hasPermissionToGetParticipantsFailsWhenUserHasOneGewestAndNotAdmin() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GEWEST, "AG0000");
        rolesAndUpperClassesByStam.put("AG0400", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0400");
        mathias.setRole(SecurityRole.GEWEST);

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToGetParticipants();
        assertEquals(false, access);
    }

    @Test
    public void hasPermissionToGetParticipantsFailsWhenUserHasOneVerbondAndNotAdmin() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.VERBOND, "AG0000");
        rolesAndUpperClassesByStam.put("AG0000", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0000");
        mathias.setRole(SecurityRole.VERBOND);

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToGetParticipants();
        assertEquals(false, access);
    }

    @Test
    public void hasPermissionToGetParticipantsFailsWhenUserHasOneGroepAndNationaal() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.NATIONAAL, "AG0400");
        rolesAndUpperClassesByStam.put("NAT", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("NAT");
        mathias.setRole(SecurityRole.NATIONAAL);

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToGetParticipants();
        assertEquals(false, access);
    }

    ///////////////////////////////////////////////////
    //////      PARTICIPANTS (ONE PLOEG) SEE   ////////
    ///////////////////////////////////////////////////
    @Test
    public void hasPermissionToSeeParticipantsFailsWhenUserHasOneGroepAndNotSameGroupAndNotOwnAndNotAdmin() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0103");
        mathias.setAdNumber("69");
        mathias.setRole(SecurityRole.GROEP);

        RegistrationParticipant registrationParticipant = new RegistrationParticipant();
        registrationParticipant.setAdNumber("007");
        registrationParticipant.setStamnumber("AG0104");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeParticipants(registrationParticipant);
        assertEquals(false, access);
    }

    @Test
    public void hasPermissionToSeeParticipantsSuccessRestrictedWhenUserHasOneGroepAndSameGroupAndNotOwnAndNotAdmin() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0103");
        mathias.setAdNumber("69");
        mathias.setRole(SecurityRole.GROEP);

        RegistrationParticipant registrationParticipant = new RegistrationParticipant();
        registrationParticipant.setAdNumber("007");
        registrationParticipant.setStamnumber("AG0103");
        registrationParticipant.setMedicalRemarks("medische info");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeParticipants(registrationParticipant);
        assertEquals(true, access);
        assertEquals("", registrationParticipant.getMedicalRemarks());
    }

    @Test
    public void hasPermissionToSeeParticipantsSuccessFullWhenUserHasOneGroepAndSameGroupAndOwnAndNotAdmin() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0103");
        mathias.setAdNumber("007");
        mathias.setRole(SecurityRole.GROEP);

        RegistrationParticipant registrationParticipant = new RegistrationParticipant();
        registrationParticipant.setAdNumber("007");
        registrationParticipant.setStamnumber("AG0103");
        registrationParticipant.setMedicalRemarks("medische info");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeParticipants(registrationParticipant);
        assertEquals(true, access);
        assertEquals("medische info", registrationParticipant.getMedicalRemarks());
    }

    @Test
    public void hasPermissionToSeeParticipantsSuccessFullWhenUserHasOneGroepAndAdmin() {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("AG0103");
        mathias.setAdNumber("69");
        mathias.setRole(SecurityRole.ADMIN);

        RegistrationParticipant registrationParticipant = new RegistrationParticipant();
        registrationParticipant.setAdNumber("007");
        registrationParticipant.setStamnumber("AG0103");
        registrationParticipant.setMedicalRemarks("medische info");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeParticipants(registrationParticipant);
        assertEquals(true, access);
        assertEquals("medische info", registrationParticipant.getMedicalRemarks());
    }

    @Test
    public void hasPermissionToSeeVerbondFailsWhenUserHasMultipleGroepenAndIsNotInVerbondAndNotAdminAndNotNationaal() throws Exception {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        RolesAndUpperClasses rolesAndUpperClasses2 = new RolesAndUpperClasses(SecurityRole.GROEP, "BG0100");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        rolesAndUpperClassesByStam.put("BG0102",rolesAndUpperClasses2);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("BG0103");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("WG0000");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeVerbonden(chiroUnit);
        assertEquals(false, access);
    }

    @Test
    public void hasPermissionToSeeVerbondSuccessWhenMulitpleGroepenAndNationaal() throws Exception {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        RolesAndUpperClasses rolesAndUpperClasses2 = new RolesAndUpperClasses(SecurityRole.NATIONAAL, "BG0100");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        rolesAndUpperClassesByStam.put("NAT",rolesAndUpperClasses2);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("BG0103");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("WG0000");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeVerbonden(chiroUnit);
        assertEquals(true, access);
    }

    @Test
    public void hasPermissionToSeeVerbondSuccessWhenMulitpleGroepenAndAdmin() throws Exception {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        RolesAndUpperClasses rolesAndUpperClasses2 = new RolesAndUpperClasses(SecurityRole.GROEP, "BG0100");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        rolesAndUpperClassesByStam.put("BG0103",rolesAndUpperClasses2);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("BG0103");
        mathias.setRole(SecurityRole.ADMIN);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("WG0000");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeVerbonden(chiroUnit);
        assertEquals(true, access);
    }

    @Test
    public void hasPermissionToSeeVerbondSuccessWhenMulitpleGroepenAndInVerbond() throws Exception {
        User mathias = new User();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();
        RolesAndUpperClasses rolesAndUpperClasses = new RolesAndUpperClasses(SecurityRole.GROEP, "AG0400");
        RolesAndUpperClasses rolesAndUpperClasses2 = new RolesAndUpperClasses(SecurityRole.GROEP, "BG0100");
        rolesAndUpperClassesByStam.put("AG0103", rolesAndUpperClasses);
        rolesAndUpperClassesByStam.put("BG0103",rolesAndUpperClasses2);
        mathias.setRolesAndUpperClassesByStam(rolesAndUpperClassesByStam);
        mathias.setStamnummer("BG0103");
        mathias.setRole(SecurityRole.GROEP);

        ChiroUnit chiroUnit = new ChiroUnit();
        chiroUnit.setStam("BG0000");

        Mockito.when(userService.getCurrentUser()).thenReturn(mathias);

        boolean access = chiroUnitServiceSecurity.hasPermissionToSeeVerbonden(chiroUnit);
        assertEquals(true, access);
    }


}
