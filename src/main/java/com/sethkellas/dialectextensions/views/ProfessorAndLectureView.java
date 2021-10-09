package com.sethkellas.dialectextensions.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorAndLectureView {
    private String fullName;
    private String lectureList;
}
