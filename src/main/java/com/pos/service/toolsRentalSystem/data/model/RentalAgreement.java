package com.pos.service.toolsRentalSystem.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
@Builder
@Entity
public class RentalAgreement {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(columnDefinition = "VARCHAR(36)")
  @JdbcTypeCode(SqlTypes.VARCHAR)
  @Setter(AccessLevel.NONE)
  private UUID id;

  @NotNull
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "toolId", referencedColumnName = "id")
  private Tool tool;

  @NotNull private int rentalDayCount;
  @NotNull private int discountPercent;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "M/d/yy")
  private LocalDate checkoutDate;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "M/d/yy")
  private LocalDate dueDate;

  @NotNull private int chargeDays;
  @NotNull private BigDecimal preDiscountCharge;
  @NotNull private BigDecimal discountAmount;
  @NotNull private BigDecimal finalCharge;

  public String getDiscountPercentFormatted() {
    return discountPercent + "%";
  }

  public String getPreDiscountChargeFormatted() {
    return "$" + new DecimalFormat("#,###.##").format(preDiscountCharge);
  }

  public String getDiscountAmountFormatted() {
    return "$" + new DecimalFormat("#,###.##").format(discountAmount);
  }

  public String getFinalChargeFormatted() {
    return "$" + new DecimalFormat("#,###.##").format(finalCharge);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        tool,
        rentalDayCount,
        discountPercent,
        checkoutDate,
        dueDate,
        chargeDays,
        preDiscountCharge,
        discountAmount,
        finalCharge);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    RentalAgreement rentalAgreement = (RentalAgreement) object;
    return Objects.equals(id, rentalAgreement.id)
        && Objects.equals(tool, rentalAgreement.tool)
        && Objects.equals(rentalDayCount, rentalAgreement.rentalDayCount)
        && Objects.equals(discountPercent, rentalAgreement.discountPercent)
        && Objects.equals(checkoutDate, rentalAgreement.checkoutDate)
        && Objects.equals(dueDate, rentalAgreement.dueDate)
        && Objects.equals(chargeDays, rentalAgreement.chargeDays)
        && Objects.equals(preDiscountCharge, rentalAgreement.preDiscountCharge)
        && Objects.equals(discountAmount, rentalAgreement.discountAmount)
        && Objects.equals(finalCharge, rentalAgreement.finalCharge);
  }

  @Override
  public String toString() {
    return "pos.rental.agreement.RentalAgreement"
        + "\n\tTool Code: "
        + this.tool.getCode()
        + "\n\tTool Type: "
        + this.tool.getType()
        + "\n\tTool Brand: "
        + this.tool.getBrand()
        + "\n\tRental days: "
        + this.rentalDayCount
        + "\n\tCheck out date: "
        + this.checkoutDate
        + "\n\tDue date: "
        + this.dueDate
        + "\n\tDaily rental charge: "
        + this.tool.getType().getDailyCharge()
        + "\n\tCharge days: "
        + this.chargeDays
        + "\n\tPre-discount charge: "
        + this.getPreDiscountChargeFormatted()
        + "\n\tDiscount percent: "
        + this.getDiscountPercentFormatted()
        + "\n\tDiscount amount: "
        + this.getDiscountAmountFormatted()
        + "\n\tFinal charge: "
        + this.getFinalChargeFormatted();
  }
}
