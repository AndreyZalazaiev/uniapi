package andrew.projects.uniapi.Repos;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pepe.projects.unitest.Entities.Theme;

public interface ThemeRepo extends CrudRepository<Theme,Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE Theme  t set  t.id_section=:id_section, t.name=:name, t.description=:description" +
            " where t.id_theme=:id_theme")
    void update(@Param("id_theme") Integer id_theme, @Param("id_section") Integer id_section, @Param("name") String name, @Param("description") String description);
    @Query(value = "From Theme  t where t.id_section=:id_section")
    Iterable<Theme> getThemesByIdSection(@Param("id_section") Integer id_section);

    @Query(value="SELECT T.id_theme,T.id_section,T.name,T.description,T.image, count(C.date_of_receiving) as popularity  FROM Theme T, Certificate C " +
            "where T.id_theme = C.id_theme " +
            "group by T.id_theme " +
            "having popularity > 0 " +
            "order by popularity desc ",nativeQuery = true)
    Iterable<?> topThemes();
}
