package com.udacity.jdnd.course3.critter.user.data;

import com.udacity.jdnd.course3.critter.user.util.DayOfWeekIntegerConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements User {
    @Id
    @GeneratedValue
    Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<EmployeeSkill> skills;
    @Convert(converter = DayOfWeekIntegerConverter.class)
    @ElementCollection
    private Set<DayOfWeek> daysAvailable;

}
