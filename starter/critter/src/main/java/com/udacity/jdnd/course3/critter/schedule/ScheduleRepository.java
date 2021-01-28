package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByPets_Id(Long id);

    List<Schedule> findAllByEmployees_Id(Long id);

    @Query("SELECT s FROM Schedule s JOIN s.pets AS p JOIN p.customer AS c WHERE c.id = :id")
    List<Schedule> findAllByCustomer_Id(Long id);

}
