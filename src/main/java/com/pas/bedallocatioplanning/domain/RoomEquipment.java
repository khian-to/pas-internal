package com.pas.bedallocatioplanning.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomEquipment {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne
    private Room room;
    @OneToOne
    private Equipment equipment;
}
