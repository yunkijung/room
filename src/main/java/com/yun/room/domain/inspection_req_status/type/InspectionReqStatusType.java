package com.yun.room.domain.inspection_req_status.type;

public enum InspectionReqStatusType {
    INIT("Init"),
    REJECTED("Rejected"),
    UPDATED("Updated"),
    ACCEPTED("Accepted"),
    CONFIRMED("Confirmed");
    // Add more status types as needed

    private final String type;

    InspectionReqStatusType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
