package com.pas.bedallocatioplanning.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.lookup.PlanningId;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionPart {
    @PlanningId
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "firstNight_id",insertable = false,updatable = false)
    private Night firstNight;


    @ManyToOne
    @JoinColumn(name = "lastNight_id",insertable = false,updatable = false)
    private Night lastNight;

    @OneToOne
    private Specialism specialism;


//    public int getNightCount() {
//        return lastNight.getIndex() - firstNight.getIndex() + 1;
//    }

    public int calculateSameNightCount(AdmissionPart other) {
        int firstNightIndex = Math.max(getFirstNight().getIndex(), other.getFirstNight().getIndex());
        int lastNightIndex = Math.min(getLastNight().getIndex(), other.getLastNight().getIndex());
        return Math.max(0, lastNightIndex - firstNightIndex + 1);
    }
}
