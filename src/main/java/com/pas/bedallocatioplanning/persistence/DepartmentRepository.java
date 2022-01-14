package com.pas.bedallocatioplanning.persistence;

import com.pas.bedallocatioplanning.domain.Department;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class DepartmentRepository implements PanacheRepository<Department> {
}
