package com.pas.bedallocatioplanning.persistence;

import com.pas.bedallocatioplanning.domain.Equipment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EquipmentRepository implements PanacheRepository<Equipment> {
}
