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
@Transactional
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer minimumAge = null;
    private Integer maximumAge = null;

    @OneToMany(mappedBy = "department",fetch = FetchType.EAGER)
//    @LazyCollection(LazyCollectionOption.TRUE)
    @JsonIgnore
    private List<Room> roomList;

    @Transactional
    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public Integer getMaximumAge() {
        return maximumAge;
    }
    public int countHardDisallowedAdmissionPart(AdmissionPart admissionPart) {
        return countDisallowedPatientAge(admissionPart.getPatient());
    }

    public int countDisallowedPatientAge(Patient patient) {
        int count = 0;
        if (minimumAge != null && patient.getAge() < minimumAge) {
            count += 100;
        }
        if (maximumAge != null && patient.getAge() > maximumAge) {
            count += 100;
        }
        return count;
    }

    public String getLabel() {
        String label = name;
        if (minimumAge != null) {
            label += "(≥" + minimumAge + ")";
        }
        if (maximumAge != null) {
            label += "(≤" + maximumAge + ")";
        }
        return label;
    }
}
