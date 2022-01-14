package com.pas.bedallocatioplanning.rest;

import com.pas.bedallocatioplanning.domain.BedDesignation;
import com.pas.bedallocatioplanning.domain.PatientAdmissionSchedule;
import com.pas.bedallocatioplanning.domain.RoomSpecialism;
import com.pas.bedallocatioplanning.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
@Path("patientSchedule")
@Transactional
@Slf4j
public class PatientAdmissionScheduleResource {
    public static final Long SINGLETON_BED_DESIGNATION_ID = 1L;
    @Inject
    AdmissionPartRepository admissionRepo;
    @Inject
    BedDesignationRepository bedDesignationRepository;
    @Inject
    BedRepository bedRepository;
    @Inject
    DepartmentRepository departmentRepository;
    @Inject
    EquipmentRepository equipmentRepository;
    @Inject
    NightRepository nightRepository;
    @Inject
    PatientRepository patientRepository;
    @Inject
    RoomRepository roomRepository;
    @Inject
    SpecialismRepository specialismRepository;
    @Inject
    RoomSpecialismRepository roomSpecialismRepository;
    @Inject
    RoomEquipmentRepository roomEquipmentRepository;
    @Inject
    DepartmentSpecialismRepository departmentSpecialismRepository;
    @Inject
    PreferredPatientEquipmentRepository preferredPatientEquipmentRepository;
    @Inject
    RequiredPatientEquipmentRepository requiredPatientEquipmentRepository;


    @Inject
    SolverManager<PatientAdmissionSchedule, Long> solverManager;
    @Inject
    ScoreManager<PatientAdmissionSchedule, HardSoftScore> scoreManager;

    @GET
    public PatientAdmissionSchedule getPatientSchedule() {
        // Get the solver status before loading the solution
        // to avoid the race condition that the solver terminates between them
        log.info("getPatientSchedule...");
        SolverStatus solverStatus = getSolverStatus();
        PatientAdmissionSchedule solution = findById(SINGLETON_BED_DESIGNATION_ID);
//        log.info("Solution - Admission "+solution.getAdmissionPartList().size());
        scoreManager.updateScore(solution); // Sets the score
//        log.info(scoreManager.explainScore(solution).getSummary());
        solution.setSolverStatus(solverStatus);
        return solution;
    }

    @POST
    @Path("solve")
    public void solve() {
        log.info("Solving..");
        log.info("SINGLETON_BED_DESIGNATION_ID: "+SINGLETON_BED_DESIGNATION_ID);
//        solverManager.solveAndListen(SINGLETON_BED_DESIGNATION_ID,
//                this::findById,
//                this::save);
        solverManager.solve(SINGLETON_BED_DESIGNATION_ID,
                this::findById,
                this::save);
    }

    @GET
    @Path("status")
    public SolverStatus status() {
        return solverManager.getSolverStatus(SINGLETON_BED_DESIGNATION_ID);
    }
    @GET
    @Path("score")
    public String getScoreSummary() {
        PatientAdmissionSchedule solution = findById(SINGLETON_BED_DESIGNATION_ID);
        return scoreManager.getSummary(solution);
    }

    public SolverStatus getSolverStatus() {
        return solverManager.getSolverStatus(SINGLETON_BED_DESIGNATION_ID);
    }

    @Transactional
    protected PatientAdmissionSchedule findById(Long id) {
        log.info("findById...");
        if (!SINGLETON_BED_DESIGNATION_ID.equals(id)) {
            throw new IllegalStateException("There is no patient schedule with id (" + id + ").");
        }
        // Occurs in a single transaction, so each initialized lesson references the same timeslot/room instance
        // that is contained by the timeTable's timeslotList/roomList.


        PatientAdmissionSchedule patientAdmissionSchedule = new PatientAdmissionSchedule(specialismRepository.listAll(), equipmentRepository.listAll(), departmentRepository.listAll(),
                roomRepository.listAll(), bedRepository.listAll(), nightRepository.listAll(), patientRepository.listAll(), admissionRepo.listAll(),
                bedDesignationRepository.listAll(), departmentSpecialismRepository.listAll(), roomSpecialismRepository.listAll(),
                roomEquipmentRepository.listAll(), requiredPatientEquipmentRepository.listAll(), preferredPatientEquipmentRepository.listAll());
        log.info("Admission size "+patientAdmissionSchedule.getAdmissionPartList().size());
        log.info("Bed Designation size "+patientAdmissionSchedule.getBedDesignationList().size());
        return patientAdmissionSchedule;
    }

    @Transactional
    protected void save(PatientAdmissionSchedule patientSchedule) {
        log.info("SAVE");
        log.info("PatientAdmissionSchedule"+patientSchedule.getBedDesignationList().size());
        for (BedDesignation bedDesignation :patientSchedule.getBedDesignationList()) {
            log.info("bedDesignation"+bedDesignation.getBed());
            // TODO this is awfully naive: optimistic locking causes issues if called by the SolverManager
            BedDesignation attachedBedDesignation = (BedDesignation) bedDesignationRepository.findById(bedDesignation.getId());
            attachedBedDesignation.setAdmissionPart(bedDesignation.getAdmissionPart());
            attachedBedDesignation.setBed(bedDesignation.getBed());
            bedDesignationRepository.persist(attachedBedDesignation);
            log.info("BedDesignation persisted: "+attachedBedDesignation.getBed());
        }
    }
}
