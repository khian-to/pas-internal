package com.pas.bedallocatioplanning.persistence;

import com.pas.bedallocatioplanning.domain.Patient;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PatientRepository implements PanacheRepository<Patient> {
}
