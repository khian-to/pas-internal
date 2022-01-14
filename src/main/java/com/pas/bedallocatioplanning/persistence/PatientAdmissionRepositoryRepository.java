package com.pas.bedallocatioplanning.persistence;

import com.pas.bedallocatioplanning.domain.PatientAdmissionSchedule;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PatientAdmissionRepositoryRepository implements PanacheRepository<PatientAdmissionSchedule> {
}
