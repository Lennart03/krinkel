package com.realdolmen.chiro.repository;


import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.domain.units.RawChiroUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChiroUnitRepository extends JpaRepository<RawChiroUnit, String> {

    @Query("SELECT c FROM RawChiroUnit c WHERE " +
            "(SUBSTRING(c.stamNumber, 1, 2) = SUBSTRING(?1, 1, 2) " +
            "OR SUBSTRING(c.stamNumber, 1, 3) = SUBSTRING(?1, 1, 3)) " +
            " AND " +
            "(SUBSTRING(c.stamNumber,LOCATE('/',c.stamNumber)+1, 4) = SUBSTRING(?1, 3, 4)" +
            "OR SUBSTRING(c.stamNumber,LOCATE('/',c.stamNumber)+1, 4) = SUBSTRING(?1, 4, 4)" +
            ")"
    )
    RawChiroUnit findOne(String s);

    @Query("SELECT p FROM RegistrationParticipant p WHERE " +
            "(SUBSTRING(p.stamnumber, 1, 2) = SUBSTRING(?1, 1, 2)" +
            "OR SUBSTRING(p.stamnumber, 1, 3) = SUBSTRING(?1, 1, 3))" +
            " AND " +
            "(SUBSTRING(p.stamnumber, LOCATE('/', p.stamnumber)+1, 4) = SUBSTRING(?1, 3, 4)" +
            "OR SUBSTRING(p.stamnumber, LOCATE('/', p.stamnumber)+1, 4) = SUBSTRING(?1, 4, 4)" +
            ")"
    )
    List<RegistrationParticipant> findParticipantsByUnit(String s);

    @Query("SELECT NEW com.realdolmen.chiro.domain.units.ChiroUnit(c.gewest, c.gewestName)" +
            "FROM RawChiroUnit c " +
            "GROUP BY c.gewest, c.gewestName")
    List<ChiroUnit> findAllGewesten();

    @Query("SELECT NEW com.realdolmen.chiro.domain.units.ChiroUnit(c.verbond, c.verbondName)" +
            "FROM RawChiroUnit c " +
            "GROUP BY c.verbond, c.verbondName")
    List<ChiroUnit> findAllVerbonden();
}
