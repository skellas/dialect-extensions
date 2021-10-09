package com.sethkellas.dialectextensions.repos;

import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import com.sethkellas.dialectextensions.entities.Lecture;
import com.sethkellas.dialectextensions.entities.Professor;
import com.sethkellas.dialectextensions.views.ProfessorAndLectureView;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(SoftAssertionsExtension.class)
class DataRepositoriesTest {
    @Autowired
    private ProfessorRepository professorRepo;
    @Autowired
    private LectureRepository lectureRepo;
    @InjectSoftAssertions
    private SoftAssertions softly;

    @Test
    void shouldPeristEntities() {
        // Given
        Professor testProfessor = professorRepo.save(Professor.builder().firstName(randomAlphabetic(8)).lastName(randomAlphabetic(12)).build());
        Lecture lectureAlpha = lectureRepo.save(Lecture.builder().title(randomAlphabetic(16)).build());
        Lecture lectureBeta = lectureRepo.save(Lecture.builder().title(randomAlphabetic(24)).build());

        // When
        Professor persistedProfessor = professorRepo.saveAndFlush(testProfessor.toBuilder().lectures(Set.of(lectureAlpha, lectureBeta)).build());

        // Then
        assertThat(persistedProfessor.getLectures()).as("Lectures are related and eagerly available")
                                                    .extracting("title").containsExactlyInAnyOrder(lectureAlpha.getTitle(), lectureBeta.getTitle());
    }
    
    @Test
    void shouldReturnViews() {
        // Given
        Professor testProfessor = professorRepo.save(Professor.builder().firstName(randomAlphabetic(8)).lastName(randomAlphabetic(12)).build());
        Lecture lectureAlpha = lectureRepo.save(Lecture.builder().title(randomAlphabetic(16)).professor(testProfessor).build());
        Lecture lectureBeta = lectureRepo.save(Lecture.builder().title(randomAlphabetic(24)).professor(testProfessor).build());

        // When
        Set<ProfessorAndLectureView> professors = professorRepo.findProfessorsWithLecturers();

        // Then
        softly.assertThat(professors).as("Matches Full Name")
                                     .extracting("fullName").contains(format("%s %s", testProfessor.getFirstName(), testProfessor.getLastName()));
        softly.assertThat(professors).as("Contains First Lecture Name")
                                     .extracting("lectureList").asString().contains(lectureAlpha.getTitle());
        softly.assertThat(professors).as("Contains Second Lecture Name")
                                     .extracting("lectureList").asString().contains(lectureBeta.getTitle());
    }
}
