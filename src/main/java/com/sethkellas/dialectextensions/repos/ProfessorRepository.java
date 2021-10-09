package com.sethkellas.dialectextensions.repos;

import java.util.Set;

import com.sethkellas.dialectextensions.entities.Professor;
import com.sethkellas.dialectextensions.views.ProfessorAndLectureView;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    @Query( value =  "SELECT new com.sethkellas.dialectextensions.views.ProfessorAndLectureView("
            + " CONCAT(p.firstName, ' ', p.lastName)"
            + " , LISTAGG(l.title, ',')"
            + ")"
            + " FROM Professor p"
            + " LEFT JOIN Lecture l on l.professor = p"
            + " GROUP BY p.firstName",
            countQuery = "SELECT COUNT(*) FROM Professor")
    Set<ProfessorAndLectureView> findProfessorsWithLecturers();
}
