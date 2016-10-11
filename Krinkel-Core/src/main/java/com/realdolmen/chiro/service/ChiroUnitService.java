package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.domain.units.RawChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Move to separate module?
 *
 * Mock for an as of yet unavailable Chiro REST API.
 *
 * Maps organisational information about Chiro Groups to a format suitable for the REST endpoint.
 */
@Service
public class ChiroUnitService {

    @Autowired
    private ChiroUnitRepository repository;

    private List<ChiroUnit> chiroUnits;
    private List<ChiroUnit> verbondUnits;
    private List<ChiroUnit> gewestUnits;

    @PostConstruct
    public void setUp(){
        this.findAll();
    }

    public List<ChiroUnit> findAll(){
        if(this.chiroUnits != null){
            return this.chiroUnits;
        }

        /* Fix StamNumbers */
        verbondUnits = repository.findAllVerbonden();
        for(ChiroUnit verbondUnit : verbondUnits){
            verbondUnit.setStam(trimStam(verbondUnit.getStam()));
        }

        /* Fix StamNumbers */
        gewestUnits = repository.findAllGewesten();
        for(ChiroUnit gewestUnit : gewestUnits){
            gewestUnit.setStam(trimStam(gewestUnit.getStam()));
        }

        List<ChiroUnit> chiroUnits = new ArrayList<>();
        // Convert all low level groups
        for(RawChiroUnit rawUnit : repository.findAll()){
            ChiroUnit unit = new ChiroUnit(trimStam(rawUnit.getStamNumber()), rawUnit.getName());

            ChiroUnit gewest = findByStam(gewestUnits, trimStam(rawUnit.getGewest()));
            gewest.getLower().add(unit);
            gewest.setUpper(findByStam(verbondUnits, trimStam(rawUnit.getVerbond())));
            unit.setUpper(gewest);
            chiroUnits.add(unit);
        }

        for(ChiroUnit gewestUnit : gewestUnits){
            ChiroUnit verbond = findByStam(verbondUnits, trimStam(gewestUnit.getUpper().getStam()));
            verbond.getLower().add(gewestUnit);
        }

        List<ChiroUnit> combinedUnits = new ArrayList<ChiroUnit>();
        combinedUnits.addAll(verbondUnits);
        combinedUnits.addAll(gewestUnits);
        combinedUnits.addAll(chiroUnits);

        this.chiroUnits = combinedUnits;

        return combinedUnits;
    }

    public List<ChiroUnit> findVerbondUnits(){
        return this.verbondUnits;
    }

    public List<ChiroUnit> findGewestUnits(){
        return this.gewestUnits;
    }

    public ChiroUnit find(String stam){
        return this.findByStam(this.findAll(), stam);
    }

    private ChiroUnit findByStam(List<ChiroUnit> units, String stam){
        for(ChiroUnit unit : units){
            if(unit.getStam().equals(stam)){
                return unit;
            }
        }
        return null;
    }

    public List<RegistrationParticipant> findParticipantsByUnit(String stamNumber){
        return repository.findParticipantsByUnit(stamNumber);
    }

    private String trimStam(String stam){
        return stam.replace("/", "").replace("\\s", "").replace(" ", "");
    }

}
