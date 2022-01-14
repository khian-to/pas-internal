package com.pas.bedallocatioplanning.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.optaplanner.core.api.domain.lookup.PlanningId;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String gender;
    private int age;
    private Integer preferredMaximumRoomCapacity;
    @OneToMany(mappedBy = "patient")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<RequiredPatientEquipment> requiredPatientEquipmentList;
    @OneToMany(mappedBy = "patient")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<PreferredPatientEquipment> preferredPatientEquipmentList;
}
