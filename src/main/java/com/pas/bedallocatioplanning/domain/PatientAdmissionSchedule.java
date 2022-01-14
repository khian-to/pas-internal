package com.pas.bedallocatioplanning.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverStatus;

import javax.persistence.Entity;
import javax.transaction.Transactional;
import java.util.List;

@PlanningSolution
@Data
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class PatientAdmissionSchedule {


    @ProblemFactCollectionProperty
    private List<Specialism> specialismList;
    @ProblemFactCollectionProperty
    private List<Equipment> equipmentList;
    @ProblemFactCollectionProperty
    private List<Department> departmentList;

    @ProblemFactCollectionProperty
    private List<Room> roomList;

    @ValueRangeProvider(id = "bedRange")
    @ProblemFactCollectionProperty
    private List<Bed> bedList;
    @ProblemFactCollectionProperty
    private List<Night> nightList;
    @ProblemFactCollectionProperty
    private List<Patient> patientList;
    @ProblemFactCollectionProperty
    private List<AdmissionPart> admissionPartList;


    @PlanningEntityCollectionProperty
    private List<BedDesignation> bedDesignationList;
    @ProblemFactCollectionProperty
    private List<DepartmentSpecialism> departmentSpecialismList;
    @ProblemFactCollectionProperty
    private List<RoomSpecialism> roomSpecialismList;
    @ProblemFactCollectionProperty
    private List<RoomEquipment> roomEquipmentList;
    @ProblemFactCollectionProperty
    private List<RequiredPatientEquipment> requiredPatientEquipmentList;
    @ProblemFactCollectionProperty
    private List<PreferredPatientEquipment> preferredPatientEquipmentList;

    @PlanningScore
    private HardMediumSoftScore score;

    // Ignored by OptaPlanner, used by the UI to display solve or stop solving button
    private SolverStatus solverStatus;

    public PatientAdmissionSchedule(List<Specialism> specialismList, List<Equipment> equipmentList, List<Department> departmentList, List<Room> roomList, List<Bed> bedList, List<Night> nightList, List<Patient> patientList, List<AdmissionPart> admissionPartList, List<BedDesignation> bedDesignationList, List<DepartmentSpecialism> departmentSpecialismList, List<RoomSpecialism> roomSpecialismList, List<RoomEquipment> roomEquipmentList, List<RequiredPatientEquipment> requiredPatientEquipmentList, List<PreferredPatientEquipment> preferredPatientEquipmentList) {
        this.specialismList = specialismList;
        this.equipmentList = equipmentList;
        this.departmentList = departmentList;
        this.roomList = roomList;
        this.bedList = bedList;
        this.nightList = nightList;
        this.patientList = patientList;
        this.admissionPartList = admissionPartList;
        this.bedDesignationList = bedDesignationList;
        this.departmentSpecialismList = departmentSpecialismList;
        this.roomSpecialismList = roomSpecialismList;
        this.roomEquipmentList = roomEquipmentList;
        this.requiredPatientEquipmentList = requiredPatientEquipmentList;
        this.preferredPatientEquipmentList = preferredPatientEquipmentList;
    }

    public SolverStatus getSolverStatus() {
        return solverStatus;
    }

    public void setSolverStatus(SolverStatus solverStatus) {
        this.solverStatus = solverStatus;
    }


}
