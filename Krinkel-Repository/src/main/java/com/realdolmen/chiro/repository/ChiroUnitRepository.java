package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.ChiroUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ChiroUnitRepository extends JpaRepository<ChiroUnit, String>{
    //, ChiroUnitRepositoryCustom {

    @Query("SELECT c FROM ChiroUnit c WHERE " +
            "(SUBSTRING(c.stamNumber, 1, 2) = SUBSTRING(?1, 1, 2) " +
            "OR SUBSTRING(c.stamNumber, 1, 3) = SUBSTRING(?1, 1, 3)) " +
            " AND " +
            "(SUBSTRING(c.stamNumber,LOCATE('/',c.stamNumber)+1, 4) = SUBSTRING(?1, 3, 4)" +
            "OR SUBSTRING(c.stamNumber,LOCATE('/',c.stamNumber)+1, 4) = SUBSTRING(?1, 4, 4)" +
            ")"
    )
    ChiroUnit findOne(String s);
}
