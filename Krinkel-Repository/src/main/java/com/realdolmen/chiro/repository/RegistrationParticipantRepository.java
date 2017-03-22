package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RegistrationParticipantRepository extends JpaRepository<RegistrationParticipant, Long> {
    RegistrationParticipant findByAdNumber(String adNumber);

    @Query("SELECT r FROM RegistrationParticipant r WHERE r.status = 'PAID'")
    List<RegistrationParticipant> findRegistrationParticipantsWithStatusPAID();

    @Query("SELECT r FROM RegistrationParticipant r WHERE r.status = 'CONFIRMED' and sync_status = 'UNSYNCED'")
    List<RegistrationParticipant> findRegistrationParticipantsWithStatusUnsyncedAndConfirmed();

    @Query("SELECT p FROM RegistrationParticipant p WHERE " +
            "(SUBSTRING(p.stamnumber, 1, 2) = SUBSTRING(?1, 1, 2)" +
            "OR SUBSTRING(p.stamnumber, 1, 3) = SUBSTRING(?1, 1, 3))" +
            " AND " +
            "(SUBSTRING(p.stamnumber, LOCATE('/', p.stamnumber)+1, 4) = SUBSTRING(?1, 3, 4)" +
            "OR SUBSTRING(p.stamnumber, LOCATE('/', p.stamnumber)+1, 4) = SUBSTRING(?1, 4, 4))" +
            " OR p.stamnumber = ?1 " +
            "AND " +
            "((p.status = com.realdolmen.chiro.domain.Status.CONFIRMED)" +
            " OR " +
            "(p.status = com.realdolmen.chiro.domain.Status.TO_BE_PAID)" +
            " OR " +
            "(p.status = com.realdolmen.chiro.domain.Status.PAID))"

    )
    List<RegistrationParticipant> findParticipantsByGroupWithStatusConfirmedOrToBePaidOrPaid(String s);

    @Query(value = "DELETE FROM registration_participant_language WHERE registration_participant_id = :participantId", nativeQuery = true)
    @Modifying
    @Transactional
    void removeBuddyLanguageRecordsAfterCancellation(@Param("participantId") Long participantId);

    @Query(value= "DELETE FROM registration_participant_post_camp_list WHERE registration_volunteer_id = :participantId", nativeQuery = true )
    @Modifying
    @Transactional
    void removePostCampRecordsAfterCancellation(@Param("participantId") Long participantId);

    @Query(value= "SELECT COUNT(*) FROM registration_participant_post_camp_list WHERE registration_volunteer_id = :participantId", nativeQuery = true )
    Long countPostCampRecordsAfterCancellation(@Param("participantId") Long participantId);

    @Query(value= "DELETE FROM registration_participant_pre_camp_list WHERE registration_volunteer_id = :participantId", nativeQuery = true )
    @Modifying
    @Transactional
    void removePreCampRecordsAfterCancellation(@Param("participantId") Long participantId);

    @Query(value= "SELECT COUNT(*) FROM registration_participant_pre_camp_list WHERE registration_volunteer_id = :participantId", nativeQuery = true )
    Long countPreCampRecordsAfterCancellation(@Param("participantId") Long participantId);

    @Query(value= "SELECT r FROM RegistrationParticipant r WHERE " +
            "(r.eventRole = 'ASPI' OR r.eventRole = 'LEADER')"
            + " AND "
            + "r.buddy = false"
            + " AND "
            + "r.status = 'CONFIRMED'")
    List<RegistrationParticipant> findAllParticipantsNoBuddy();

    @Query("SELECT a FROM RegistrationParticipant a, RawChiroUnit b " +
            "where a.stamnumber=b.groepStamNummer " +
            "and b.verbondStamNummer= :verbondStamNumber")
    List<RegistrationParticipant> findAllParticipantsWithVerbondName(@Param("verbondStamNumber") String verbondNaam);

    @Query("SELECT a FROM RegistrationParticipant a, RawChiroUnit b " +
            "where a.stamnumber=b.groepStamNummer " +
            "and b.gewestStamNummer= :gewestStamNumber")
    List<RegistrationParticipant> findAllParticipantsWithGewestName(@Param("gewestStamNumber") String gewestNaam);

    @Query("SELECT a FROM RegistrationParticipant  a, RawChiroUnit b " +
            "where a.stamnumber=b.groepStamNummer " +
            "and b.groepStamNummer= :groepStamNumber")
    List<RegistrationParticipant> findAllParticipantsWithGroepName(@Param("groepStamNumber") String groepNaam);



    @Query(value = "SELECT r.status FROM  RegistrationParticipant r WHERE r.adNumber = :adNumber")
    Status getPaymentStatusByadNumber(@Param("adNumber") String adNumber);

    List<RegistrationParticipant> findAll();
}
