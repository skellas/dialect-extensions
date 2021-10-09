package com.sethkellas.dialectextensions.repos;

import com.sethkellas.dialectextensions.entities.Lecture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    
}
