package com.pas.bedallocatioplanning.rest;

import com.pas.bedallocatioplanning.domain.AdmissionPart;
import com.pas.bedallocatioplanning.persistence.AdmissionPartRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

import java.util.function.LongFunction;

@ResourceProperties(path = "admissionPart")
public interface AdmissionPartResource extends PanacheRepositoryResource<AdmissionPartRepository, AdmissionPart, Long> {
}
