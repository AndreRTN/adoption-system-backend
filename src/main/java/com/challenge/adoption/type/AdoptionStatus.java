package com.challenge.adoption.type;

import java.util.List;

public enum AdoptionStatus {
    AVAILABLE,
    ADOPTED;

    public static AdoptionStatus getStatusFromString(String status) {
        List<AdoptionStatus> statusList = List.of(AdoptionStatus.values());
        return statusList.stream().filter(st -> st.name().equalsIgnoreCase(status)).findFirst().orElse(null);
    }
}
