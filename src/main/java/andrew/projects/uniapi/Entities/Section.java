package andrew.projects.uniapi.Entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Section {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id_section;
    @Column(columnDefinition = "varchar(255) default 'Untitled section'")
    private String name;

    //@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval =true)
    @JoinColumn(name = "id_section", referencedColumnName = "id_section")
    private List<Theme> themes = new ArrayList<>();

    public void addTheme(Theme t) {
        themes.add(t);
    }
}
