package com.pas.bedallocatioplanning.rest;

import com.pas.bedallocatioplanning.domain.Equipment;
import com.pas.bedallocatioplanning.persistence.EquipmentRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "equipment")
public interface EquipementResource extends PanacheRepositoryResource<EquipmentRepository, Equipment,Long> {
}
