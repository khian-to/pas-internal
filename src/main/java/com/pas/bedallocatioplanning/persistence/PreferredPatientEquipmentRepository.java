package com.pas.bedallocatioplanning.persistence;

import com.pas.bedallocatioplanning.domain.Patient;
import com.pas.bedallocatioplanning.domain.PreferredPatientEquipment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PreferredPatientEquipmentRepository implements PanacheRepository<PreferredPatientEquipment> {
}
