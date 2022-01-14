package com.pas.bedallocatioplanning.rest;

import com.pas.bedallocatioplanning.domain.Room;
import com.pas.bedallocatioplanning.persistence.RoomRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "room")
public interface RoomResource extends PanacheRepositoryResource<RoomRepository, Room,Long> {
}
