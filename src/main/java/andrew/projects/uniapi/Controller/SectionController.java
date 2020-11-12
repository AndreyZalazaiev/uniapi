package andrew.projects.uniapi.Controller;

import andrew.projects.uniapi.Entities.Section;
import andrew.projects.uniapi.Repos.SectionRepo;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/section")
public class SectionController {
    @Autowired
    SectionRepo sectionRepo;

    @PostMapping
    public String createSection(Section s) {
        if (s != null) {
            sectionRepo.save(s);
            return "Saved";
        } else return "something goes wrong";
    }

    @GetMapping
    public @ResponseBody
    Iterable<Section> getSections() {
        return sectionRepo.findAll();
    }

    @DeleteMapping
    public String removeSection(@RequestParam Integer id_section) {
        try {
            sectionRepo.deleteById(id_section);
            return "Removed";
        } catch (Exception e) {
            return "There is no element with that id";
        }
    }
    @PostMapping("/update")
    public String updateSection(Section s)
    {
        val sectionOptional=(sectionRepo.findById(s.getId_section()));
        if(sectionOptional.isPresent())
        {
            sectionRepo.update(s.getId_section(),s.getName());
            return "Updated";
        }
        else return "There is no element with that id";
    }

}
