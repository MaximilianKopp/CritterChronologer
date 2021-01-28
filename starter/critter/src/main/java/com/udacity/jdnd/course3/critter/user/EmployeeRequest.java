package com.udacity.jdnd.course3.critter.user;

import java.time.LocalDate;
import java.util.Set;

/**
 * Does not need to be an entity since we do not need to persist it to the database.
 */
public class EmployeeRequest {
    private Set<EmployeeSkill> skills;
    private LocalDate date;

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
