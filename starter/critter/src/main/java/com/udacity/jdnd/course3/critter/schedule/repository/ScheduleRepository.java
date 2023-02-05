package com.udacity.jdnd.course3.critter.schedule.repository;

import com.udacity.jdnd.course3.critter.pet.data.Pet;
import com.udacity.jdnd.course3.critter.schedule.data.Schedule;
import com.udacity.jdnd.course3.critter.user.data.Customer;
import com.udacity.jdnd.course3.critter.user.data.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Set<Schedule> findAllByEmployeesContaining(Employee employee);

    Set<Schedule> findAllByPetsContaining(Pet pet);

    @Query("SELECT s FROM Schedule s JOIN s.pets p JOIN p.owner c WHERE c = :customer")
    Set<Schedule> findByCustomer(@Param("customer") Customer customer);

}
