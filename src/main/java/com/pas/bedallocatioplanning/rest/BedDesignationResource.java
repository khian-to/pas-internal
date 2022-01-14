package com.pas.bedallocatioplanning.rest;

import com.pas.bedallocatioplanning.domain.Bed;
import com.pas.bedallocatioplanning.domain.BedDesignation;
import com.pas.bedallocatioplanning.persistence.BedDesignationRepository;
import com.pas.bedallocatioplanning.persistence.BedRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "bedDesignation")
public interface BedDesignationResource extends PanacheRepositoryResource<BedDesignationRepository, BedDesignation, Long> {
}
