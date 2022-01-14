package com.pas.bedallocatioplanning.persistence;

import com.pas.bedallocatioplanning.domain.BedDesignation;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BedDesignationRepository implements PanacheRepository<BedDesignation> {
}
