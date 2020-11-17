package andrew.projects.uniapi.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
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
public class Theme {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id_theme;
    @NotNull
    private Integer id_section;
    @Column(columnDefinition = "varchar(255) default 'Untitled'")
    private String name;
    @Column(columnDefinition = "varchar(255) default 'No description'",length = 2000)
    private String description;
    private String image;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval =true)
    @JoinColumn(name = "id_theme", referencedColumnName = "id_theme")
    private List<Level> levels = new ArrayList<>();

    public void addLevel(Level l) {
        levels.add(l);
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval =true)
    @JoinColumn(name = "id_theme", referencedColumnName = "id_theme")
    private List<Certificate> certificates = new ArrayList<>();

    public void addCertificate(Certificate c) {
        certificates.add(c);
    }
}
