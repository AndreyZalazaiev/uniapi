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
public class Question {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id_question;
    @NotNull
    private Integer id_level;
    @Column(columnDefinition = "varchar(255) default 'No question text'",length = 2000)
    private String text;
    @Column(columnDefinition = "varchar(255) default 'Untitled question'")
    private String name;
    private String image;

    //@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval =true)
    @JoinColumn(name = "id_question", referencedColumnName = "id_question")
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer a) {
        answers.add(a);
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval =true)
    @JoinColumn(name = "id_question", referencedColumnName = "id_question")
    private List<QuestionStatistics> stats = new ArrayList<>() ;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval =true)
    @JoinColumn(name = "id_question", referencedColumnName = "id_question")
    private List<QuestionDetails> questionDetails = new ArrayList<>();

    public void addUQD( QuestionDetails uqd) {
        questionDetails.add(uqd);
    }

}
