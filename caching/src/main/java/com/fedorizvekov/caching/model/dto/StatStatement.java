package com.fedorizvekov.caching.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StatStatement {

    private String query;

    private Integer calls;

    private Float totalExecTime;

    private Float meanExecTime;

}
