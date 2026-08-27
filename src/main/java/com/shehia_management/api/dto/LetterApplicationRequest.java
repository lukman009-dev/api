package com.shehia_management.api.dto;

import com.shehia_management.api.enums.LetterType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LetterApplicationRequest {

    private Long residentId;
    private LetterType type;
    private String formData;
    private String docUrl; // optional
}
