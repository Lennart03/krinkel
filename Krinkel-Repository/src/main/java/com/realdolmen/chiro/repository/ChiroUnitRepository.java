package com.realdolmen.chiro.repository;


import com.realdolmen.chiro.domain.CampGround;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.domain.units.RawChiroUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChiroUnitRepository extends JpaRepository<RawChiroUnit, String> {

/*    @Query("SELECT c FROM RawChiroUnit c WHERE " +
            "(SUBSTRING(c.groepStamNummer, 1, 2) = SUBSTRING(?1, 1, 2) " +
            "OR SUBSTRING(c.groepStamNummer, 1, 3) = SUBSTRING(?1, 1, 3)) " +
            " AND " +
            "(SUBSTRING(c.groepStamNummer,LOCATE('/',c.groepStamNummer)+1, 4) = SUBSTRING(?1, 3, 4)" +
            "OR SUBSTRING(c.groepStamNummer,LOCATE('/',c.groepStamNummer)+1, 4) = SUBSTRING(?1, 4, 4)" +
            ")"
    )
    RawChiroUnit findOne(String s);*/


    @Query("SELECT NEW com.realdolmen.chiro.domain.units.ChiroUnit(c.gewestStamNummer, c.gewestNaam)" +
            "FROM RawChiroUnit c " +
            "GROUP BY c.gewestStamNummer, c.gewestNaam")
    List<ChiroUnit> findAllGewesten();

    @Query("SELECT NEW com.realdolmen.chiro.domain.units.ChiroUnit(c.gewestStamNummer, c.gewestNaam)" +
            "FROM RawChiroUnit c " +
            "WHERE c.verbondStamNummer = ?1 " +
            "GROUP BY c.gewestStamNummer, c.gewestNaam")
    List<ChiroUnit> findAllGewestenWhereVerbondStamNummerIs(String verbondStamNummer);

    @Query("SELECT NEW com.realdolmen.chiro.domain.units.ChiroUnit(c.groepStamNummer, c.groepNaam)" +
            "FROM RawChiroUnit c " +
            "WHERE c.gewestStamNummer = ?1 " +
            "GROUP BY c.groepStamNummer, c.groepNaam")
    List<ChiroUnit> findAllGroepenWhereGewestStamNummerIs(String gewestStamNummer);

    @Query("SELECT NEW com.realdolmen.chiro.domain.units.ChiroUnit(c.verbondStamNummer, c.verbondNaam)" +
            "FROM RawChiroUnit c " +
            "GROUP BY c.verbondStamNummer, c.verbondNaam")
    List<ChiroUnit> findAllVerbonden();

    @Query("SELECT NEW com.realdolmen.chiro.domain.units.ChiroUnit(c.verbondStamNummer, c.verbondNaam) FROM RegistrationParticipant r, RawChiroUnit c WHERE " +
            "r.adNumber = ?1" +
            " AND " +
            "r.stamnumber = c.groepStamNummer")
    ChiroUnit findUnionParticipant(String adNumber);
    /**
     * Get the nr of participants + volunteers who are in the verbond specified by the given verbondStamNummer
     * @param verbondStamNummer
     * @return
     */
    @Query("SELECT COUNT(p) FROM RegistrationParticipant p, RawChiroUnit c " +
            "WHERE c.verbondStamNummer = ?1 AND p.stamnumber = c.groepStamNummer")
    int countParticipantsByVerbond(String verbondStamNummer);

    @Query("SELECT COUNT(p) FROM RegistrationParticipant p, RawChiroUnit c " +
            "WHERE c.verbondStamNummer LIKE ?1 AND p.stamnumber = c.groepStamNummer AND p.status = ?2 AND p.buddy = ?3")
    int countParticipantsByVerbond(String verbondStamNummer, Status status, boolean countBuddies);

    //TODO: countParticipantsByVerbond etc op enkel status doen en buddy = true etc...

    @Query("SELECT COUNT(p) FROM RegistrationParticipant p " +
            "WHERE p.status = ?1 AND p.buddy = true")
    int countParticipantsInternationaal(Status status);

    /**
     * Get the nr of volunteers who are in the verbond specified by the given verbondStamNummer
     * @param verbondStamNummer
     * @return
     */
    @Query("SELECT COUNT(p) FROM RegistrationVolunteer p, RawChiroUnit c " +
            "WHERE c.verbondStamNummer LIKE ?1 AND p.stamnumber = c.groepStamNummer")
    int countVolunteersByVerbond(String verbondStamNummer);

    @Query("SELECT COUNT(p) FROM RegistrationVolunteer p, RawChiroUnit c " +
            "WHERE c.verbondStamNummer = ?1 AND p.stamnumber = c.groepStamNummer AND p.status = ?2 AND p.buddy = ?3")
    int countVolunteersByVerbond(String verbondStamNummer, Status status, boolean countBuddies);

    /**
     * Get the nr of participants + volunteers who are in the gewest specified by the given gewestStamNummer
     * @param gewestStamNummer
     * @return
     */
    @Query("SELECT COUNT(p) FROM RegistrationParticipant p, RawChiroUnit c " +
            "WHERE c.gewestStamNummer = ?1 AND p.stamnumber = c.groepStamNummer")
    int countParticipantsByGewest(String gewestStamNummer);

    @Query("SELECT COUNT(p) FROM RegistrationParticipant p, RawChiroUnit c " +
            "WHERE c.gewestStamNummer = ?1 AND p.stamnumber = c.groepStamNummer AND p.status = ?2 AND p.buddy = ?3")
    int countParticipantsByGewest(String gewestStamNummer, Status status, boolean countBuddies);

    /**
     * Get the nr of volunteers who are in the gewest specified by the given gewestStamNummer
     * @param gewestStamNummer
     * @return
     */
    @Query("SELECT COUNT(p) FROM RegistrationVolunteer p, RawChiroUnit c " +
            "WHERE c.gewestStamNummer = ?1 AND p.stamnumber = c.groepStamNummer")
    int countVolunteersByGewest(String gewestStamNummer);

    @Query("SELECT COUNT(p) FROM RegistrationVolunteer p, RawChiroUnit c " +
            "WHERE c.gewestStamNummer = ?1 AND p.stamnumber = c.groepStamNummer AND p.status = ?2 AND p.buddy = ?3")
    int countVolunteersByGewest(String gewestStamNummer, Status status, boolean countBuddies);

    /**
     * Get the nr of participants + volunteers who are in the groep specified by the given groepStamNummer
     * @param groepStamNummer
     * @return
     */
    @Query("SELECT COUNT(p) FROM RegistrationParticipant p, RawChiroUnit c " +
            "WHERE c.groepStamNummer = ?1 AND p.stamnumber = c.groepStamNummer AND p.status = ?2")
    int countParticipantsByGroep(String groepStamNummer);

    @Query("SELECT COUNT(p) FROM RegistrationParticipant p, RawChiroUnit c " +
            "WHERE c.groepStamNummer = ?1 AND p.stamnumber = c.groepStamNummer AND p.status = ?2 AND p.buddy = ?3")
    int countParticipantsByGroep(String stamNummer, Status status, boolean countBuddies);

    /**
     * Get the nr of participants + volunteers who are in the groep specified by the given groepStamNummer + who have the given paymentstatus
     * @param groepStamNummer
     * @return
     */
    @Query("SELECT COUNT(p) FROM RegistrationParticipant p, RawChiroUnit c " +
            "WHERE c.groepStamNummer = ?1 AND p.stamnumber = c.groepStamNummer AND p.status = ?2")
    int countParticipantsByGroep(String groepStamNummer, Status status);

    /**
     * Get the nr of volunteers who are in the groep specified by the given groepStamNummer
     * @param groepStamNummer
     * @return
     */
    @Query("SELECT COUNT(p) FROM RegistrationVolunteer p, RawChiroUnit c " +
            "WHERE c.groepStamNummer = ?1 AND p.stamnumber = c.groepStamNummer")
    int countVolunteersByGroep(String groepStamNummer);

    @Query("SELECT COUNT(p) FROM RegistrationVolunteer p, RawChiroUnit c " +
            "WHERE c.groepStamNummer = ?1 AND p.stamnumber = c.groepStamNummer AND p.status = ?2")
    int countVolunteersByGroep(String groepStamNummer, Status status);

    @Query("SELECT COUNT(p) FROM RegistrationVolunteer p, RawChiroUnit c " +
            "WHERE c.groepStamNummer = ?1 AND p.stamnumber = c.groepStamNummer AND p.status = ?2 AND p.buddy = ?3")
    int countVolunteersByGroep(String stamNummer, Status status, boolean countBuddies);

    /**
     * Return a list with volunteers who are in the groep specified by the given groepStamNummer
     * @param groepStamNummer
     * @return
     */
    @Query("SELECT p FROM RegistrationVolunteer p, RawChiroUnit c " +
            "WHERE c.groepStamNummer = ?1 AND p.stamnumber = c.groepStamNummer")
    List<RegistrationVolunteer> returnVolunteersByGroep (String groepStamNummer);


    /**
     * Return a list with partticipants who are in the groep specified by the given groepStamNummer
     * @param groepStamNummer
     * @return
     */
    @Query("SELECT p FROM RegistrationParticipant p, RawChiroUnit c " +
            "WHERE c.groepStamNummer = ?1 AND p.stamnumber = c.groepStamNummer AND p.buddy = ?2")
    List<RegistrationParticipant> returnParticipantsByGroep (String groepStamNummer, boolean countBuddies);

    @Query("SELECT COUNT(p) FROM RegistrationVolunteer p " +
            "WHERE p.campGround = ?1 AND p.status = ?2")
    int getCountsVolunteersByCampgroundAndStatus(CampGround campGround, Status status);

    @Query("SELECT p FROM RegistrationParticipant p " +
            "WHERE p.campGround = ?1")
    List<RegistrationVolunteer> returnVolunteersByCampGround(CampGround camgroundByName);

    @Query("SELECT p FROM RegistrationParticipant p " +
            "WHERE p.buddy = true")
    List<RegistrationParticipant> returnParticipantsInternationaal();
}
