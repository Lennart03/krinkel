package com.realdolmen.chiro.repository;


import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.domain.units.RawChiroUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChiroUnitRepository extends JpaRepository<RawChiroUnit, String> {

    @Query("SELECT c FROM RawChiroUnit c WHERE " +
            "(SUBSTRING(c.groepStamNummer, 1, 2) = SUBSTRING(?1, 1, 2) " +
            "OR SUBSTRING(c.groepStamNummer, 1, 3) = SUBSTRING(?1, 1, 3)) " +
            " AND " +
            "(SUBSTRING(c.groepStamNummer,LOCATE('/',c.groepStamNummer)+1, 4) = SUBSTRING(?1, 3, 4)" +
            "OR SUBSTRING(c.groepStamNummer,LOCATE('/',c.groepStamNummer)+1, 4) = SUBSTRING(?1, 4, 4)" +
            ")"
    )
    RawChiroUnit findOne(String s);


    @Query("SELECT NEW com.realdolmen.chiro.domain.units.ChiroUnit(c.gewestStamNummer, c.gewestNaam)" +
            "FROM RawChiroUnit c " +
            "GROUP BY c.gewestStamNummer, c.gewestNaam")
    List<ChiroUnit> findAllGewesten();

    @Query("SELECT NEW com.realdolmen.chiro.domain.units.ChiroUnit(c.verbondStamNummer, c.verbondNaam)" +
            "FROM RawChiroUnit c " +
            "GROUP BY c.verbondStamNummer, c.verbondNaam")
    List<ChiroUnit> findAllVerbonden();

    @Query("SELECT COUNT(p) FROM RegistrationParticipant p, RawChiroUnit c " +
            "WHERE c.verbondStamNummer = ?1 AND p.stamnumber = c.groepStamNummer")
    int countParticipantsByVerbond(String verbondStamNummer);

    @Query("SELECT COUNT(p) FROM RegistrationVolunteer p, RawChiroUnit c " +
                  "WHERE c.verbondStamNummer = ?1 AND p.stamnumber = c.groepStamNummer")
    int countVolunteersByVerbond(String verbondStamNummer);

    @Query("SELECT COUNT(p) FROM RegistrationParticipant p, RawChiroUnit c " +
            "WHERE c.gewestStamNummer = ?1 AND p.stamnumber = c.groepStamNummer")
    int countParticipantsByGewest(String gewestStamNummer);

    @Query("SELECT COUNT(p) FROM RegistrationVolunteer p, RawChiroUnit c " +
            "WHERE c.gewestStamNummer = ?1 AND p.stamnumber = c.groepStamNummer")
    int countVolunteersByGewest(String gewestStamNummer);

    /*SELECT c FROM Customer c, DiscountCode d WHERE c.DISCOUNT_CODE= d.DISCOUNT_CODE", Customer.class);*/
}
