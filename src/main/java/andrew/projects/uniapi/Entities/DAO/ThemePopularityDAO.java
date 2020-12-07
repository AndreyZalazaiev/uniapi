package andrew.projects.uniapi.Entities.DAO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ThemePopularityDAO implements Serializable {
    private Integer id_theme;
    private Integer id_section;
    private String name;
    private String description;
    private String image;
    private Integer popularity;
}
