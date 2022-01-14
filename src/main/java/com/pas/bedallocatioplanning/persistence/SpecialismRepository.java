package com.pas.bedallocatioplanning.persistence;

import com.pas.bedallocatioplanning.domain.Specialism;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SpecialismRepository implements PanacheRepository<Specialism> {
}
