package com.realdolmen.chiro.domain;

public enum Status {
    TO_BE_PAID,
    PAID,
    CONFIRMED,
    SYNCED
    //Status changes to confirmed when the confirmation link is clicked
}
