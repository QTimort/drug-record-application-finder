package com.diguiet.draf.common.models.api.fda;

import lombok.Data;

@Data
public class Submission {
    private String submission_type;
    private String submission_number;
    private String submission_status;
    private String submission_status_date;
    private String submission_class_code;
    private String submission_class_code_description;
    private String review_priority;
}
