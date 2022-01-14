package com.pas.bedallocatioplanning.rest;

import com.pas.bedallocatioplanning.domain.Specialism;
import com.pas.bedallocatioplanning.persistence.SpecialismRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "specialism")
public interface SpecialismResource extends PanacheRepositoryResource<SpecialismRepository, Specialism,Long> {
}
