package com.sethkellas.dialectextensions.repos;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import com.sethkellas.dialectextensions.entities.Lecture;
import com.sethkellas.dialectextensions.entities.Professor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DataRepositoriesTest {
    @Autowired
    private ProfessorRepository professorRepo;
    @Autowired
    private LectureRepository lectureRepo;

    @Test
    void shouldPeristEntities() {
        // Given
        Professor testProfessor = professorRepo.save(Professor.builder().firstName(randomAlphabetic(8)).lastName(randomAlphabetic(12)).build());
        Lecture lectureAlpha = lectureRepo.save(Lecture.builder().title(randomAlphabetic(16)).build());
        Lecture lectureBeta = lectureRepo.save(Lecture.builder().title(randomAlphabetic(24)).build());

        // When
        Professor persistedProfessor = professorRepo.saveAndFlush(testProfessor.toBuilder().lectures(Set.of(lectureAlpha, lectureBeta)).build());

        // Then
        assertThat(persistedProfessor.getLectures()).extracting("title").containsExactlyInAnyOrder(lectureAlpha.getTitle(), lectureBeta.getTitle());
    }
}
