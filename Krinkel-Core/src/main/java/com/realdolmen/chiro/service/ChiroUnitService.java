package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.domain.units.RawChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Move to seperate module.
 *
 */
@Service
public class ChiroUnitService {

    @Autowired
    private ChiroUnitRepository repository;

    private List<ChiroUnit> chiroUnits;

    private List<ChiroUnit> verbondUnits;
    private List<ChiroUnit> gewestUnits;

    public List<ChiroUnit> findAll(){
        if(this.chiroUnits != null){
            return this.chiroUnits;
        }
        verbondUnits = repository.findAllVerbonden();
        for(ChiroUnit verbondUnit : verbondUnits){
            verbondUnit.setStam(trimStam(verbondUnit.getStam()));
        }

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

        return combinedUnits;
    }

    public List<ChiroUnit> findVerbondUnits(){
        this.findAll();
        return this.verbondUnits;
    }

    public List<ChiroUnit> findGewestUnits(){
        this.findAll();
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



    private String trimStam(String stam){
        return stam.replace("/", "").replace("\\s", "").replace(" ", "");
    }

}
