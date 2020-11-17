package andrew.projects.uniapi.Controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pepe.projects.unitest.Entities.DAO.ThemePopularityDAO;
import pepe.projects.unitest.Entities.Theme;
import pepe.projects.unitest.Repos.ThemeRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/theme")
public class ThemeController {
    @Autowired
    ThemeRepo themeRepo;

    @PostMapping
    public String createTheme(Theme t) {
        if (t != null) {
            t.setImage(null);
            themeRepo.save(t);
            return "Saved";
        } else return "something goes wrong";
    }

    @GetMapping
    public @ResponseBody
    Iterable<Theme> getTheme() {
        return themeRepo.findAll();
    }

    @DeleteMapping
    public String removeTheme(@RequestParam Integer id_theme) {
        try {
            themeRepo.deleteById(id_theme);
            return "Removed";
        } catch (Exception e) {
            return "There is no element with that id";
        }
    }
    @PostMapping("/update")
    public String updateSection(Theme t)
    {
        val themeOptional=(themeRepo.findById(t.getId_theme()));
        if(themeOptional.isPresent())
        {
            themeRepo.update(t.getId_theme(),t.getId_section(),t.getName(),t.getDescription());
            return "Updated";
        }
        else return "There is no element with that id";
    }
    @GetMapping("/parse")
    Iterable<Theme> getAllThemesForSection(@RequestParam Integer id_section){
        return themeRepo.getThemesByIdSection(id_section);
    }

}
