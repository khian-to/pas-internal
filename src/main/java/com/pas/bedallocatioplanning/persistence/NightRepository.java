package com.pas.bedallocatioplanning.persistence;

import com.pas.bedallocatioplanning.domain.Night;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NightRepository implements PanacheRepository<Night> {
}
