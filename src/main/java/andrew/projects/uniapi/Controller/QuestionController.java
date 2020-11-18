package andrew.projects.uniapi.Controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pepe.projects.unitest.Entities.Question;
import pepe.projects.unitest.Repos.QuestionRepo;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionRepo questionRepo;

    @PostMapping
    public String createQuestion(Question q) {
        if (q != null) {
            q.setImage(null);
            questionRepo.save(q);
            return "Saved";
        } else return "something goes wrong";
    }

    @GetMapping
    public @ResponseBody
    Iterable<Question> getQuestions() {
        return questionRepo.findAll();
    }

    @DeleteMapping
    public String removeQuestion(@RequestParam Integer id_question) {
        try {
            questionRepo.deleteById(id_question);
            return "Removed";
        } catch (Exception e) {
            return "There is no element with that id";
        }
    }

    @PostMapping("/update")
    public String updateQuestion(Question q) {
        val questionOptional = (questionRepo.findById(q.getId_question()));
        if (questionOptional.isPresent()) {
            questionRepo.update(q.getId_question(),q.getId_level(),q.getText(),q.getName());//image убран
            return "Updated";
        } else return "There is no element with that id";
    }
    @GetMapping("/parse")
    public Iterable<Question> getAllQuestionsByIdLevel(@RequestParam Integer id_level)
    {
        return questionRepo.allQuestionsByIdLevel(id_level);
    }


}
