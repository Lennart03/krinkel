package com.realdolmen.chiro.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberSyncerService {

    @Scheduled(cron = "0/2 * * * * *")
    public void executeBookProcessingSchedule() {
        System.out.println("Hello, World!");
    }
}
