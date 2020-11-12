package andrew.projects.uniapi.Repos;

import andrew.projects.uniapi.Entities.Section;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SectionRepo extends CrudRepository<Section,Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE Section  s set  s.name=:input" +
            " where s.id_section=:id_section")
    void update(@Param("id_section") Integer id_section, @Param("input") String input);
}
