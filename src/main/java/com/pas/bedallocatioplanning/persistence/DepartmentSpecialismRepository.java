package com.pas.bedallocatioplanning.persistence;

import com.pas.bedallocatioplanning.domain.DepartmentSpecialism;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DepartmentSpecialismRepository implements PanacheRepository<DepartmentSpecialism> {
}
