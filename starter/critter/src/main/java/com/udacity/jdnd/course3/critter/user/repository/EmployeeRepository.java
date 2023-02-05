package com.udacity.jdnd.course3.critter.user.repository;

import com.udacity.jdnd.course3.critter.user.data.Employee;
import com.udacity.jdnd.course3.critter.user.data.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE :dayOfWeek MEMBER OF e.daysAvailable AND :size = (SELECT COUNT(s) FROM e.skills s WHERE s IN :skills)")
    List<Employee> findByDayOfWeekAndSkills(
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("skills") Set<EmployeeSkill> skills,
            @Param("size") long size);

}


