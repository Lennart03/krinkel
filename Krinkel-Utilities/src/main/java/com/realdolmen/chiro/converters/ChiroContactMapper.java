package com.realdolmen.chiro.converters;

import com.realdolmen.chiro.containers.ChiroContactContainer;
import com.realdolmen.chiro.dataholders.ChiroContactHolder;
import com.realdolmen.chiro.domain.units.ChiroContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LVDBB73 on 14/12/2016.
 */
public class ChiroContactMapper {

    public ChiroContact retrieveChiroContact(ChiroContactContainer chiroContactsContainer) {
        return createChiroContact(chiroContactsContainer.getValues().get(0));
    }

    public List<ChiroContact> retrieveCollegues(ChiroContactContainer chiroContactContainer) {
        List<ChiroContact> chiroContacts = new ArrayList<>();
        for (ChiroContactHolder holder : chiroContactContainer.getValues()) {
            chiroContacts.add(createChiroContact(holder));
        }
        return chiroContacts;
    }

    protected ChiroContact createChiroContact(ChiroContactHolder holder) {
        ChiroContact chiroContact = new ChiroContact();
        chiroContact.setAdnr(holder.getAdnr());
        chiroContact.setFirstName(holder.getFirst_name());
        chiroContact.setLastName(holder.getLast_name());
        chiroContact.setAfdeling(holder.getAfdeling());
        chiroContact.setGender(holder.getGender_id());
        chiroContact.setBirthDate(holder.getBirth_date());
        chiroContact.setStreetAddress(holder.getStreet_address());
        chiroContact.setCity(holder.getCity());
        chiroContact.setCountry(holder.getCountry());
        chiroContact.setPhone(holder.getPhone());
        chiroContact.setEmail(holder.getEmail());
        chiroContact.setId(holder.getId());
        chiroContact.setPostalCode(holder.getPostal_code());
        chiroContact.setFuncties(holder.getFuncties());
        return chiroContact;
    }

}
