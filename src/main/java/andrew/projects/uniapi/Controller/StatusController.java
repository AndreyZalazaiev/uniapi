package andrew.projects.uniapi.Controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pepe.projects.unitest.Entities.*;
import pepe.projects.unitest.Repos.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/status")
public class StatusController {
    @Autowired
    StatusRepo statusRepo;
    @Autowired
    AnswerRepo answerRepo;
    @Autowired
    QuestionStatisticsRepo questionStatisticsRepo;
    @Autowired
    UserQuestionDetailsRepo questionDetailsRepo;
    @Autowired
    CertificateRepo certificateRepo;
    @Autowired
    QuestionRepo questionRepo;

    @PostMapping
    public String createStatus(@RequestBody ArrayList<Status> s) { //изменено 17.10
        if (s != null) {
            statusRepo.saveAll(s);
            updateStatsOfQuestion(s);
            return "Saved";
        } else return "Something goes wrong";
    }

    @GetMapping
    public @ResponseBody
    Iterable<Status> getStatus() {
        return statusRepo.findAll();
    }

    @DeleteMapping
    public String removeStatus(@RequestParam Integer id_status) {
        try {
            statusRepo.deleteById(id_status);
            return "Removed";
        } catch (Exception e) {
            return "There is no element with that id";
        }
    }

    @PostMapping("/update")
    public String updateQuestion(Status s) {
        val questionOptional = (statusRepo.findById(s.getId_status()));
        if (questionOptional.isPresent()) {
            statusRepo.update(s.getId_status(), s.getId_answer(), s.getId_account(), s.getComment(), s.getRating());
            return "Updated";
        } else return "There is no element with that id";
    }

    @GetMapping("/parse")
    public Iterable<Status> allStatusesByQuestionAndAccount(@RequestParam Integer id_account, @RequestParam Integer id_question) {
        return statusRepo.allStatusesByAnswerAndAccount(id_account, id_question);
    }

    @GetMapping("/stats")
    public double getStatsByIdQuestion(@RequestParam int id_question) {
        List<QuestionStatistics> qs = questionStatisticsRepo.findAllById_question(id_question);
        return qs.stream().map(q -> q.getPercentOfRightAnswers()).reduce(Double::sum).get() / qs.size();
    }

    public void updateStatsOfQuestion(ArrayList<Status> s) throws IndexOutOfBoundsException {
        statusRepo.saveAll(s);
        if (s.size() > 0) {
            HashMap<Integer, Integer> questions = new HashMap<>();

            for (Status status : s) {
                Answer a = answerRepo.findById(status.getId_answer()).get();

                if (questions.containsKey(a.getId_question()))
                    questions.computeIfPresent(a.getId_question(), (k, v) -> a.getIsCorrect() ? (v + 1) : (v - 1));
                else questions.put(a.getId_question(), a.getIsCorrect() ? 1 : -1);
            }

            ArrayList<QuestionStatistics> statsList = new ArrayList<>();
            questions.forEach((k, v) -> {
                long allQuestions = answerRepo.allRightAnswersForQuestion(k).size();
                if (allQuestions > 0) {
                    QuestionStatistics qs = new QuestionStatistics();
                    qs.setId_question(k);
                    qs.setPercentOfRightAnswers((double) v / allQuestions);
                    statsList.add(qs);
                }
            });

            questionStatisticsRepo.saveAll(statsList);

        } else throw new IndexOutOfBoundsException();

    }

    @PostMapping("/save")
    public String saveResults(@RequestBody ArrayList<QuestionDetails> input) {
        ArrayList<QuestionDetails> questionDetailsStored = (ArrayList<QuestionDetails>) questionDetailsRepo.findAll();
        for (QuestionDetails q : input
        ) {
            int index = checkForExistingRecord(questionDetailsStored, q);
            if (index != -1) {
                val temp = questionDetailsStored.get(index);
                if (temp.getResult() != q.getResult()) {
                    temp.setResult(q.getResult());
                    questionDetailsRepo.save(temp);
                    incrementCertificate(findidThemeByIdQuestion(temp.getId_question()), temp.getId_account(), temp.getResult());
                }
            } else {
                questionDetailsRepo.save(q);
                incrementCertificate(findidThemeByIdQuestion(input.get(0).getId_question()), input.get(0).getId_account(), input.get(0).getResult());
            }
        }
        return "Saved";
    }

    public int checkForExistingRecord(ArrayList<QuestionDetails> questionDetails, QuestionDetails obj) {
        for (int i = 0; i < questionDetails.size(); i++) {
            if (questionDetails.get(i).getId_question() == obj.getId_question()) {
                return i;
            }
        }
        return -1;
    }

    public void incrementCertificate(Integer id_theme, Integer id_account, Boolean res) {
        Optional<Certificate> c = certificateRepo.getCertificateByIdThemeAndIdAccount(id_theme, id_account);
        if (c.isPresent()) {
            c.get().setCompletedTasks(c.get().getCompletedTasks() + (res ? 1 : -1));
            certificateRepo.save(c.get());
        } else {
            Certificate created = new Certificate();
            created.setId_theme(id_theme);
            created.setAllTasks(certificateRepo.getCountOfThemes(id_theme));
            created.setId_account(id_account);
            created.setCompletedTasks(res ? 1 : 0);
            certificateRepo.save(created);
        }

    }

    public Integer findidThemeByIdQuestion(int id_question) {
        return questionRepo.getIdThemeByIdQuestion(id_question);
    }
}
