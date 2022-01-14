package com.pas.bedallocatioplanning.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int capacity;
    private GenderLimitation genderLimitation;
    private Long department_id;

    @OneToOne
    @JoinColumn(name = "department_id",insertable = false,updatable = false)
    private Department department;

    @OneToMany(mappedBy = "room")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<RoomSpecialism> roomSpecialismList;

    @OneToMany(mappedBy = "room")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<RoomEquipment> roomEquipmentList;

    @OneToMany(mappedBy = "room")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Bed> bedList;
    @Transactional
    public List<RoomSpecialism> getRoomSpecialismList() {
        return roomSpecialismList;
    }

    public void setRoomSpecialismList(List<RoomSpecialism> roomSpecialismList) {
        this.roomSpecialismList = roomSpecialismList;
    }
    @Transactional
    public List<RoomEquipment> getRoomEquipmentList() {
        return roomEquipmentList;
    }
    @Transactional
    public void setRoomEquipmentList(List<RoomEquipment> roomEquipmentList) {
        this.roomEquipmentList = roomEquipmentList;
    }

    public List<Bed> getBedList() {
        return bedList;
    }

    public void setBedList(List<Bed> bedList) {
        this.bedList = bedList;
    }

    public int countHardDisallowedAdmissionPart(AdmissionPart admissionPart) {
        return countMissingRequiredRoomProperties(admissionPart.getPatient())
                + department.countHardDisallowedAdmissionPart(admissionPart)
                + countDisallowedPatientGender(admissionPart.getPatient());
        // TODO preferredMaximumRoomCapacity and specialism
    }

    public int countMissingRequiredRoomProperties(Patient patient) {
        int count = 0;
        for (RequiredPatientEquipment requiredPatientEquipment : patient.getRequiredPatientEquipmentList()) {
            Equipment requiredEquipment = requiredPatientEquipment.getEquipment();
            boolean hasRequiredEquipment = false;
            for (RoomEquipment roomEquipment : roomEquipmentList) {
                if (roomEquipment.getEquipment().equals(requiredEquipment)) {
                    hasRequiredEquipment = true;
                }
            }
            if (!hasRequiredEquipment) {
                count += 100000;
            }
        }
        return count;
    }

    public int countDisallowedPatientGender(Patient patient) {
        switch (genderLimitation) {
            case ANY_GENDER:
                return 0;
            case MALE_ONLY:
                return patient.getGender() == Gender.MALE.getCode() ? 0 : 4;
            case FEMALE_ONLY:
                return patient.getGender() == Gender.FEMALE.getCode() ? 0 : 4;
            case SAME_GENDER:
                // Constraints check this
                return 1;
            default:
                throw new IllegalStateException("The genderLimitation (" + genderLimitation + ") is not implemented.");
        }
    }

    public int countSoftDisallowedAdmissionPart(AdmissionPart admissionPart) {
        return countMissingPreferredRoomProperties(admissionPart.getPatient());
        // TODO preferredMaximumRoomCapacity and specialism
    }

    public int countMissingPreferredRoomProperties(Patient patient) {
        int count = 0;
        for (PreferredPatientEquipment preferredPatientEquipment : patient.getPreferredPatientEquipmentList()) {
            Equipment preferredEquipment = preferredPatientEquipment.getEquipment();
            boolean hasPreferredEquipment = false;
            for (RoomEquipment roomEquipment : roomEquipmentList) {
                if (roomEquipment.getEquipment().equals(preferredEquipment)) {
                    hasPreferredEquipment = true;
                }
            }
            if (!hasPreferredEquipment) {
                count += 20;
            }
        }
        return count;
    }

    public String getLabel() {
        return name;
    }

}
