package entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "account")
@Builder
@Table(name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "operation_date", nullable = false)
    private LocalDateTime operationDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


}
