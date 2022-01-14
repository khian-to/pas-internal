package com.pas.bedallocatioplanning.rest;

import com.pas.bedallocatioplanning.domain.Bed;
import com.pas.bedallocatioplanning.domain.Night;
import com.pas.bedallocatioplanning.persistence.BedRepository;
import com.pas.bedallocatioplanning.persistence.NightRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "night")
public interface NightResource extends PanacheRepositoryResource<NightRepository, Night, Long> {
}
