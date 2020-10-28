package andrew.projects.uniapi.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Certificate {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id_certificate;
    private Integer id_theme;
    private Integer id_account;
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfReceiving;
    @Column(columnDefinition = "int default '0'")
    private Integer completedTasks;
    @Column(columnDefinition = "int default '0'")
    private Integer allTasks;
}
