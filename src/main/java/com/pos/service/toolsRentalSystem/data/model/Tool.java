package com.pos.service.toolsRentalSystem.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @NotBlank
    @NotNull
    @Column(unique = true)
    private String code;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "toolTypeId", referencedColumnName = "id")
    private ToolType type;

    @NotBlank @NotNull private String brand;

    @NotNull private Integer availableAmount;

    @Override
    public String toString() {
        return "Tool{"
                + "id="
                + id
                + ", code='"
                + code
                + '\''
                + ", type='"
                + type
                + '\''
                + ", brand='"
                + brand
                + '\''
                + ", availableAmount='"
                + availableAmount
                + '\''
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, type, brand, availableAmount);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Tool tool = (Tool) object;
        return Objects.equals(id, tool.id)
                && type.equals(tool.type)
                && code.equals(tool.getCode())
                && brand.equals(tool.getBrand());
    }
}
