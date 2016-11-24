package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.GraphLoginCount;
import com.realdolmen.chiro.domain.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoginLoggerRepository extends JpaRepository<LoginLog, Long> {
	
	List<LoginLog> findByAdNumber(String adNumber);

    @Query("SELECT NEW com.realdolmen.chiro.domain.GraphLoginCount(l.stamp, count(DISTINCT l.adNumber), l.stamNumber)" +
            "FROM LoginLog l " +
            "GROUP BY l.stamNumber, l.stamp")
    List<GraphLoginCount> crunchData();
}
