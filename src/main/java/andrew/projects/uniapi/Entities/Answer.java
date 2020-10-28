package andrew.projects.uniapi.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Answer {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id_answer;
    private Integer id_question;
    @Column(columnDefinition = "varchar(255) default 'Empty answer'",length = 1000)
    private String text;
    private String advice;
    private Boolean isCorrect;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval =true)
    @JoinColumn(name = "id_answer", referencedColumnName = "id_answer")
    private List<Status> statuses = new ArrayList<>();

    public void addStatus(Status s) {
        statuses.add(s);
    }
}
