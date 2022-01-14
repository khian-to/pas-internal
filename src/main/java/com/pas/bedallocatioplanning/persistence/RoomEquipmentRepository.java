package com.pas.bedallocatioplanning.persistence;

import com.pas.bedallocatioplanning.domain.Room;
import com.pas.bedallocatioplanning.domain.RoomEquipment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoomEquipmentRepository implements PanacheRepository<RoomEquipment> {
}
