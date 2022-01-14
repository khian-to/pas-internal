package com.pas.bedallocatioplanning.domain.solver;

import com.pas.bedallocatioplanning.domain.Bed;
import com.pas.bedallocatioplanning.domain.Department;
import com.pas.bedallocatioplanning.domain.Room;

import java.util.Collections;
import java.util.Comparator;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

public class BedStrengthComparator implements Comparator<Bed> {
    private static final Comparator<Integer> NULLSAFE_INTEGER_COMPARATOR = Comparator.nullsFirst(Integer::compareTo);
    private static final Comparator<Department> DEPARTMENT_COMPARATOR = comparing(
            (Department department) -> department.getMinimumAge() == null) // null minimumAge is stronger
            .thenComparing(department -> department.getMaximumAge() == null) // null maximumAge is stronger
            .thenComparing(Department::getMinimumAge, Collections.reverseOrder(NULLSAFE_INTEGER_COMPARATOR)) // Descending, low minimumAge is stronger
            .thenComparing(Department::getMaximumAge, NULLSAFE_INTEGER_COMPARATOR); // High maximumAge is stronger
    private static final Comparator<Room> ROOM_COMPARATOR = Comparator.comparingInt((Room room) -> room.getRoomEquipmentList().size())
            .thenComparingInt(room -> room.getRoomSpecialismList().size())
            .thenComparingInt(room -> -room.getCapacity()); // Descending (smaller rooms are stronger)
    private static final Comparator<Bed> COMPARATOR = comparing((Bed bed) -> bed.getRoom().getDepartment(),
            DEPARTMENT_COMPARATOR)
            .thenComparing(Bed::getRoom, ROOM_COMPARATOR)
            .thenComparingLong(Bed::getId);

    @Override
    public int compare(Bed a, Bed b) {

        if (a == null) {
            if (b == null) {
                return 0;
            }
            return -1;
        } else if (b == null) {
            return 1;
        }
        return COMPARATOR.compare(a, b);
    }
}
