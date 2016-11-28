package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.GraphLoginCount;
import com.realdolmen.chiro.domain.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LoginLoggerRepository extends JpaRepository<LoginLog, Long> {

	List<LoginLog> findByAdNumber(String adNumber);

    @Query("SELECT NEW com.realdolmen.chiro.domain.GraphLoginCount(l.stamp, count(DISTINCT l.adNumber), l.stamNumber)" +
            "FROM LoginLog l " +
            "GROUP BY l.stamNumber, l.stamp")
    List<GraphLoginCount> crunchData();


    @Query("SELECT DISTINCT l.stamp " +
            "FROM LoginLog l")
    List<Date> findDistinctStamps();


    @Query("SELECT l " +
            "FROM LoginLog l " +
            "WHERE l.stamp BETWEEN :startDate AND :endDate")
    List<LoginLog> findLogsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT DISTINCT l.stamp " +
            "FROM LoginLog l " +
            "WHERE l.stamp BETWEEN :startDate AND :endDate")
    List<Date> findDistinctStamps(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
