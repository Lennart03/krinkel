package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.GraphLoginCount;
import com.realdolmen.chiro.domain.LoginLog;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoginLoggerRepository extends JpaRepository<LoginLog, Long> {

    @Query("SELECT NEW com.realdolmen.chiro.domain.GraphLoginCount(l.stamp, count(DISTINCT l.adNumber))" +
            "FROM LoginLog l " +
            "GROUP BY l.stamp")
    List<GraphLoginCount> crunchData();
}
