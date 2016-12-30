package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.units.Admin;
import com.realdolmen.chiro.domain.units.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by LVDBB73 on 8/12/2016.
 */
public interface AdminRepository extends JpaRepository<Admin, Integer> {

}
