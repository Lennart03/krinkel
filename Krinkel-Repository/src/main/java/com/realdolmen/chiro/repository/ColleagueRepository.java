package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.Colleague;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ColleagueRepository{
    public List<Colleague> findAllByAdNumber(String adNumber){
        List<Colleague> colleagues = new ArrayList();
        colleagues.add(new Colleague("0","firstname0","lastname0","afdeling0"));
        colleagues.add(new Colleague("1","firstname1","lastname1","afdeling1"));
        colleagues.add(new Colleague("2","firstname2","lastname2","afdeling2"));
        colleagues.add(new Colleague("3","firstname3","lastname3","afdeling3"));
        colleagues.add(new Colleague("4","firstname4","lastname4","afdeling4"));
        return colleagues;
    }
}
