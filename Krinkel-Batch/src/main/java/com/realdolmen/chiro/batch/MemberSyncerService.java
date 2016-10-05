package com.realdolmen.chiro.batch;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberSyncerService {

    @Autowired
    private RegistrationParticipantRepository repository;

    @Scheduled(cron = "0/10 * * * * *")
    public void executeBookProcessingSchedule() {
        System.out.println("Hello, World!");
        List<RegistrationParticipant> all = repository.findAll();
        System.out.println(all.size());

    }
}
