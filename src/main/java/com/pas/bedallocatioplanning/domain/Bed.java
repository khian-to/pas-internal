package com.pas.bedallocatioplanning.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.lookup.PlanningId;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bed {
    @PlanningId
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    private Room room;
    private int indexInRoom;
}
