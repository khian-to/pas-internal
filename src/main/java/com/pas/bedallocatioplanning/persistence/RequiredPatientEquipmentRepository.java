package com.pas.bedallocatioplanning.persistence;

import com.pas.bedallocatioplanning.domain.RequiredPatientEquipment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RequiredPatientEquipmentRepository implements PanacheRepository<RequiredPatientEquipment> {
}
