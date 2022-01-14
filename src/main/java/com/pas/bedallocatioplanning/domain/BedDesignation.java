package com.pas.bedallocatioplanning.domain;

import com.pas.bedallocatioplanning.domain.solver.BedDesignationDifficultyWeightFactory;
import com.pas.bedallocatioplanning.domain.solver.BedStrengthComparator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import javax.persistence.*;

@PlanningEntity(difficultyWeightFactoryClass = BedDesignationDifficultyWeightFactory.class)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BedDesignation {
    @PlanningId
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Bed bed;

    @OneToOne
    private AdmissionPart admissionPart;
    public AdmissionPart getAdmissionPart() {
        return admissionPart;
    }

    public void setAdmissionPart(AdmissionPart admissionPart) {
        this.admissionPart = admissionPart;
    }

    @PlanningVariable(nullable = true, valueRangeProviderRefs = {
            "bedRange" }, strengthComparatorClass = BedStrengthComparator.class)
    public Bed getBed() {
        return bed;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public Patient getPatient() {
        return admissionPart.getPatient();
    }

    public String getPatientGender() {
        return admissionPart.getPatient().getGender();
    }

    public int getPatientAge() {
        return admissionPart.getPatient().getAge();
    }

    public Integer getPatientPreferredMaximumRoomCapacity() {
        return admissionPart.getPatient().getPreferredMaximumRoomCapacity();
    }

    public Specialism getAdmissionPartSpecialism() {
        return admissionPart.getSpecialism();
    }

    public int getFirstNightIndex() {
        return admissionPart.getFirstNight().getIndex();
    }

    public int getLastNightIndex() {
        return admissionPart.getLastNight().getIndex();
    }

    public int getAdmissionPartNightCount() {
        return admissionPart.getLastNight().getIndex() - admissionPart.getFirstNight().getIndex();
    }

    public Room getRoom() {
        if (bed == null) {
            return null;
        }
        return bed.getRoom();
    }

    public int getRoomCapacity() {
        if (bed == null) {
            return Integer.MIN_VALUE;
        }
        return bed.getRoom().getCapacity();
    }

    public Department getDepartment() {
        if (bed == null) {
            return null;
        }
        return bed.getRoom().getDepartment();
    }

    public GenderLimitation getRoomGenderLimitation() {
        if (bed == null) {
            return null;
        }
        return bed.getRoom().getGenderLimitation();
    }
}
