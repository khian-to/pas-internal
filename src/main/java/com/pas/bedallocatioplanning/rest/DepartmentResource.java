package com.pas.bedallocatioplanning.rest;

import com.pas.bedallocatioplanning.domain.Department;
import com.pas.bedallocatioplanning.persistence.DepartmentRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

import javax.transaction.Transactional;

@ResourceProperties(path = "department")
@Transactional
public interface DepartmentResource extends PanacheRepositoryResource<DepartmentRepository, Department,Long> {
}
