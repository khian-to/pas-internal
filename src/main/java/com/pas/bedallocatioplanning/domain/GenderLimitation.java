package com.pas.bedallocatioplanning.domain;

public enum GenderLimitation {
    ANY_GENDER("N"), // mixed
    MALE_ONLY("M"),
    FEMALE_ONLY("F"),
    SAME_GENDER("D"); // dependent on the first

    public static GenderLimitation valueOfCode(String code) {
        for (GenderLimitation gender : GenderLimitation.values()) {
            if (code.equalsIgnoreCase(gender.getCode())) {
                return gender;
            }
        }
        return null;
    }

    private String code;

    GenderLimitation(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
