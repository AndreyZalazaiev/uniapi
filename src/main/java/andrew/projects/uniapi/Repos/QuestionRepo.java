package andrew.projects.uniapi.Repos;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pepe.projects.unitest.Entities.Question;

import java.util.HashMap;
import java.util.Map;

public interface QuestionRepo extends CrudRepository<Question,Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE Question q set q.id_level=:id_level,q.text=:text,q.name=:name" +
            " where q.id_question=:id_question")
    void update(@Param("id_question") Integer id_question, @Param("id_level") Integer id_level, @Param("text") String text, @Param("name") String name);

    @Query(value = "from Question q where q.id_level=:id_level")
    Iterable<Question> allQuestionsByIdLevel(@Param("id_level") Integer id_level);

    @Query(value = "select l.id_theme from Level l ,Question q where q.id_question=:id_question and q.id_level=l.id_level")
    Integer getIdThemeByIdQuestion(@Param("id_question") Integer id_question);
}
