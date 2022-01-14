package com.pas.bedallocatioplanning.persistence;

import com.pas.bedallocatioplanning.domain.Room;
import com.pas.bedallocatioplanning.domain.RoomSpecialism;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoomSpecialismRepository implements PanacheRepository<RoomSpecialism> {
}
