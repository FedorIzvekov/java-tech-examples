package com.fedorizvekov.db.clickhouse.model.entity;

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
import javax.persistence.Table;
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
    @Column(name = "long_id", nullable = false)
    private Long longId;                        // ClickHouse not support SEQUENCE, IDENTITY and TABLE

    @Column(name = "database_name", nullable = false, length = 100)
    private String databaseName;

    @Column(name = "string_value", nullable = false)
    private String stringValue;

    @Column(name = "char_value", nullable = false, columnDefinition = "fixedstring")
    private Character charValue;

    @Column(name = "local_date_value", nullable = false, columnDefinition = "date")
    private LocalDate localDateValue;

    @Column(name = "local_time_value", nullable = false, columnDefinition = "datetime")
    private LocalTime localTimeValue;

    @Column(name = "local_date_time_value", nullable = false, columnDefinition = "datetime")
    private LocalDateTime localDateTimeValue;

    @Column(name = "byte_value", nullable = false, columnDefinition = "int8")
    private Byte byteValue;

    @Column(name = "short_value", nullable = false, columnDefinition = "int16")
    private Short shortValue;

    @Column(name = "integer_value", nullable = false, columnDefinition = "int32")
    private Integer integerValue;

    @Column(name = "big_decimal_value", nullable = false)
    private BigDecimal bigDecimalValue;

    @Column(name = "boolean_value", nullable = false, columnDefinition = "int8")
    private Integer booleanValue;


    @Column(name = "uuid_value", nullable = false, columnDefinition = "UUID")
    private UUID uuidValue;

//    private byte[] blobValue;         // ClickHouse not support BLOB value


    public boolean isBooleanValue() {
        return booleanValue == 1;
    }


    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue ? 1 : 0;
    }

}
