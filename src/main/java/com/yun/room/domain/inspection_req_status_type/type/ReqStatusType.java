package com.yun.room.domain.inspection_req_status_type.type;

public enum ReqStatusType {
    REJECTED_BY_HOST("REJECTED_BY_HOST"),
    UPDATED_BY_HOST("UPDATED_BY_HOST"),
    ACCEPTED_BY_HOST("ACCEPTED_BY_HOST"),
    CANCELED_BY_HOST("CANCELED_BY_HOST"),


    INIT_BY_TENANT("INIT_BY_TENANT"),
    CANCELED_BY_TENANT("CANCELED_BY_TENANT"),
    REJECTED_BY_TENANT("REJECTED_BY_TENANT"),
    UPDATED_BY_TENANT("UPDATED_BY_TENANT"),
    CONFIRMED_BY_TENANT("CONFIRMED_BY_TENANT");

    // Add more status types as needed

    private final String type;

    ReqStatusType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
