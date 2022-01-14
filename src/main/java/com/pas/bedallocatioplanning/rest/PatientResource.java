package com.pas.bedallocatioplanning.rest;

import com.pas.bedallocatioplanning.domain.Patient;
import com.pas.bedallocatioplanning.persistence.PatientRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "patient")
public interface PatientResource extends PanacheRepositoryResource<PatientRepository, Patient,Long> {

}
