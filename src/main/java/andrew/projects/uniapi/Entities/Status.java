package andrew.projects.uniapi.Entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Status {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id_status;
    private Integer id_answer;
    private Integer id_account;
    private String comment;
    @Column(columnDefinition = "int default '0'")
    private Integer rating;
}
