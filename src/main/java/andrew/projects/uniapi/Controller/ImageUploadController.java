package andrew.projects.uniapi.Controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pepe.projects.unitest.Repos.AccountRepo;
import pepe.projects.unitest.Repos.LevelRepo;
import pepe.projects.unitest.Repos.QuestionRepo;
import pepe.projects.unitest.Repos.ThemeRepo;
import pepe.projects.unitest.Services.FileService;

import java.io.IOException;


@RestController

public class ImageUploadController {
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    LevelRepo levelRepo;
    @Autowired
    QuestionRepo questionRepo;
    @Autowired
    FileService fileService;
    @Autowired
    ThemeRepo themeRepo;


    @PostMapping("/account/image")
    public String accountUploadImage(@RequestParam("id_account") Integer id_account, @RequestParam("image") MultipartFile file) throws IOException {
        val a = accountRepo.findById(id_account);
        if (a.isPresent() && file != null) {
            if (a.get().getImage() != null) fileService.deleteFile(a.get().getImage());
            String fileName = fileService.putFile(file);
            a.get().setImage(fileName);
            accountRepo.save(a.get());
            return "Saved as:" + fileName;
        }
        return "Wrong file";
    }

    //refactor
    @PostMapping("/level/image")
    public String levelUploadImage(@RequestParam("id_level") Integer id_level, @RequestParam("image") MultipartFile file) throws IOException {
        val l = levelRepo.findById(id_level);
        if (l.isPresent() && file != null) {
            if (l.get().getImage() != null) fileService.deleteFile(l.get().getImage());
            String fileName = fileService.putFile(file);
            l.get().setImage(fileName);
            levelRepo.save(l.get());
            return "Saved as:" + fileName;
        }
        return "Wrong file";
    }

    @PostMapping("/question/image")
    public String questionUploadImage(@RequestParam("id_question") Integer id_question, @RequestParam("image") MultipartFile file) throws IOException {
        val q = questionRepo.findById(id_question);
        if (q.isPresent() && file != null) {
            if (q.get().getImage() != null) fileService.deleteFile(q.get().getImage());
            String fileName = fileService.putFile(file);
            q.get().setImage(fileName);
            questionRepo.save(q.get());
            return "Saved as:" + fileName;
        }
        return "Wrong file";
    }

    @PostMapping("/theme/image")
    public String themeUploadImage(@RequestParam("id_theme") Integer id_theme, @RequestParam("image") MultipartFile file) throws IOException {
        val t = themeRepo.findById(id_theme);
        if (t.isPresent() && file != null) {
            if (t.get().getImage() != null) fileService.deleteFile(t.get().getImage());
            String fileName = fileService.putFile(file);
            t.get().setImage(fileName);
            themeRepo.save(t.get());
            return "Saved as:" + fileName;
        }
        return "Wrong file";
    }

}