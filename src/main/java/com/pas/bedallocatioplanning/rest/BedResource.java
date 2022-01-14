package com.pas.bedallocatioplanning.rest;

import com.pas.bedallocatioplanning.domain.Bed;
import com.pas.bedallocatioplanning.persistence.BedRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "bed")
public interface BedResource extends PanacheRepositoryResource<BedRepository, Bed, Long> {
}
