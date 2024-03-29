package com.pos.service.toolsRentalSystem.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Setter(AccessLevel.NONE)
    protected UUID id;

    @NotBlank
    @NotNull
    @Column(unique = true)
    protected String name;

    public Holiday(String name) {
        this.name = name;
    }

    public abstract LocalDate getDate(int year);

    @Override
    public String toString() {
        return "Holiday{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
