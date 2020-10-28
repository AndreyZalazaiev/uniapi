package andrew.projects.uniapi.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id_account;
    private String nickname;
    private String email;
    private String password;
    @Column(columnDefinition = "varchar(255) default 'User'")
    private String role;
    @Column(columnDefinition = "int default '0'")
    private Integer bonuses;
    private String fullName;
    private String activationCode;
    private String image;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval =true)
    @JoinColumn(name = "id_account", referencedColumnName = "id_account")
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer a) {
        answers.add(a);
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval =true)
    @JoinColumn(name = "id_account", referencedColumnName = "id_account")
    private List<Certificate> certificates = new ArrayList<>();

    public void addCertificate(Certificate c) {
        certificates.add(c);
    }
}
