package andrew.projects.uniapi.Controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pepe.projects.unitest.Entities.Level;
import pepe.projects.unitest.Repos.LevelRepo;

@RestController
@RequestMapping("/level")
public class LevelController {
    @Autowired
    LevelRepo levelRepo;

    @PostMapping
    public String createLevel(Level l) {
        if (l != null) {
            l.setImage(null);
            levelRepo.save(l);
            return "Saved";
        } else return "something goes wrong";
    }

    @GetMapping
    public @ResponseBody
    Iterable<Level> getLevels() {
        return levelRepo.findAll();
    }

    @DeleteMapping
    public String removeLevel(@RequestParam Integer id_level) {
        try {
            levelRepo.deleteById(id_level);
            return "Removed";
        } catch (Exception e) {
            return "There is no element with that id";
        }
    }

    @PostMapping("/update")
    public String updateLevel(Level l) {
        val levelOptional = (levelRepo.findById(l.getId_level()));
        if (levelOptional.isPresent()) {
            levelRepo.update(l.getId_level(), l.getId_theme(), l.getName(), l.getEducationMaterial());//image убран
            return "Updated";
        } else return "There is no element with that id";
    }

    @GetMapping("/parse")
    public Iterable<Level> getLevelsByTheme(@RequestParam Integer id_theme) {
        return levelRepo.allLevelsByIdTheme(id_theme);
    }


}
