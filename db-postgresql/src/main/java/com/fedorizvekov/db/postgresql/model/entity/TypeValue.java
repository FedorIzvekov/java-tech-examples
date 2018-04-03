package com.fedorizvekov.db.postgresql.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "type_value")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TypeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type_value_seq")
    @SequenceGenerator(name = "type_value_seq", sequenceName = "type_value_long_id_seq")
    @Column(name = "long_id", nullable = false)
    private Long longId;

    @Column(name = "database_name", nullable = false, length = 100)
    private String databaseName;

    @Column(name = "string_value", nullable = false)
    private String stringValue;

    @Column(name = "char_value", nullable = false)
    private Character charValue;

    @Column(name = "local_date_value", nullable = false)
    private LocalDate localDateValue;

    @Column(name = "local_time_value", nullable = false)
    private LocalTime localTimeValue;

    @Column(name = "local_date_time_value", nullable = false)
    private LocalDateTime localDateTimeValue;

    @Column(name = "byte_value", nullable = false)
    private Byte byteValue;

    @Column(name = "short_value", nullable = false)
    private Short shortValue;

    @Column(name = "integer_value", nullable = false)
    private Integer integerValue;

    @Column(name = "big_decimal_value", nullable = false)
    private BigDecimal bigDecimalValue;

    @Column(name = "boolean_value", nullable = false)
    private Boolean booleanValue;

    @Column(name = "uuid_value", nullable = false)
    private UUID uuidValue;

    @Transient
    private byte[] blobValue;

}
