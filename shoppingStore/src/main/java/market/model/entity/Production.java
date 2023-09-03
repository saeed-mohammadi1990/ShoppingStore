package market.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "production")
@Entity(name = "production")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Production implements Serializable {
    @Id
    @SequenceGenerator(name = "p_seq", sequenceName = "p_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "p_seq")
    private Long id;
    @Column(columnDefinition = "VARCHAR2(20)")
    private String name;
    @Column(columnDefinition = "NUMBER")
    private Long price;
    private String description;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "productionList")
    private List<Cart> cartList;
}
