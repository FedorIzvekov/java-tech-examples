package com.fedorizvekov.caching.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cached_data")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CachedData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "string_value", nullable = false)
    private String stringValue;

    @Column(name = "local_date_value", nullable = false)
    private LocalDate localDateValue;

    @Column(name = "local_time_value", nullable = false)
    private LocalTime localTimeValue;

    @Column(name = "local_date_time_value", nullable = false)
    private LocalDateTime localDateTimeValue;

    @Column(name = "integer_value", nullable = false)
    private Integer integerValue;

    @Column(name = "big_decimal_value", nullable = false)
    private BigDecimal bigDecimalValue;

    @Column(name = "boolean_value", nullable = false)
    private Boolean booleanValue;

}
