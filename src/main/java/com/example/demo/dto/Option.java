package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Option {
        @SerializedName("underlying")
        private String underlying;
        @SerializedName("strikeDate")
        private String strikeDate;
        @SerializedName("issueDate")
        private String issueDate;
        @SerializedName("solvingFor")
        private String solvingFor;
}