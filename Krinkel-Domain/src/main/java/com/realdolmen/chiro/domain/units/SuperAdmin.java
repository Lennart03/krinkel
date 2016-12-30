package com.realdolmen.chiro.domain.units;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by LVDBB73 on 28/12/2016.
 */
@Entity
@DiscriminatorValue("super-admin")
public class SuperAdmin extends Admin {


}
