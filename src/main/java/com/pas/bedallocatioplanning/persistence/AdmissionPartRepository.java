package com.pas.bedallocatioplanning.persistence;

import com.pas.bedallocatioplanning.domain.AdmissionPart;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AdmissionPartRepository implements PanacheRepository<AdmissionPart> {
}
