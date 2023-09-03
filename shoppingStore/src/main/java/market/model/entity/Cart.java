package market.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "cart")
@Entity(name = "cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @SequenceGenerator(name = "cart_seq", sequenceName = "cart_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cart_seq")
    private Long id;
    @Column(columnDefinition = "NUMBER")
    private Long totalPrice;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "cart_production", joinColumns = @JoinColumn(name = "c_id"), inverseJoinColumns = @JoinColumn(name = "p_id"))
    private List<Production> productionList = new ArrayList<>();
}
