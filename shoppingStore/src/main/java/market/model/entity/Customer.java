package market.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "customer")
@Entity(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {
    @Id
    @SequenceGenerator(name = "c_seq", sequenceName = "c_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "c_seq")
    private Long id;
    @Column(columnDefinition = "VARCHAR2(10)")
    private String name;
    @Column(columnDefinition = "VARCHAR2(10)")
    private String family;
    @Column(columnDefinition = "NUMBER")
    private Long phoneNumber;
    private String address;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
