package com.realdolmen.chiro.domain.units;

/**
 * Created by WVDAZ49 on 13/10/2016.
 */
public class StatusChiroUnit {
    private Integer participantsConfirmed=0;
    private Integer volunteersConfirmed=0;
    private Integer participantsNotConfirmed=0;
    private Integer volunteersNotConfirmed=0;
    private Integer participantsNotPaid=0;
    private Integer volunteersNotPaid=0;

    public StatusChiroUnit() {
    }

    public Integer getParticipantsConfirmed() {
        return participantsConfirmed;
    }

    public void setParticipantsConfirmed(Integer participantsConfirmed) {
        this.participantsConfirmed = participantsConfirmed;
    }

    public Integer getVolunteersConfirmed() {
        return volunteersConfirmed;
    }

    public void setVolunteersConfirmed(Integer volunteersConfirmed) {
        this.volunteersConfirmed = volunteersConfirmed;
    }

    public Integer getParticipantsNotConfirmed() {
        return participantsNotConfirmed;
    }

    public void setParticipantsNotConfirmed(Integer participantsNotConfirmed) {
        this.participantsNotConfirmed = participantsNotConfirmed;
    }

    public Integer getVolunteersNotConfirmed() {
        return volunteersNotConfirmed;
    }

    public void setVolunteersNotConfirmed(Integer volunteersNotConfirmed) {
        this.volunteersNotConfirmed = volunteersNotConfirmed;
    }

    public Integer getParticipantsNotPaid() {
        return participantsNotPaid;
    }

    public void setParticipantsNotPaid(Integer participantsNotPaid) {
        this.participantsNotPaid = participantsNotPaid;
    }

    public Integer getVolunteersNotPaid() {
        return volunteersNotPaid;
    }

    public void setVolunteersNotPaid(Integer volunteersNotPaid) {
        this.volunteersNotPaid = volunteersNotPaid;
    }
}

