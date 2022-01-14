package com.pas.bedallocatioplanning.persistence;

import com.pas.bedallocatioplanning.domain.Bed;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BedRepository implements PanacheRepository<Bed> {
}
