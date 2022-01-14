package com.pas.bedallocatioplanning.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentSpecialism {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private Integer priority;
    @OneToOne
    private Department department;
    @OneToOne
    private Specialism specialism;

}
