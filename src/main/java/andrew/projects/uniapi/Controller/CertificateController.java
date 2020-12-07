package andrew.projects.uniapi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pepe.projects.unitest.Entities.Certificate;
import pepe.projects.unitest.Repos.CertificateRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/certificate")
public class CertificateController {
    @Autowired
    CertificateRepo certificateRepo;

    @GetMapping
    public Iterable<Certificate> getAllCertificates() {
        return certificateRepo.findAll();
    }

    @PostMapping
    public String createCertificate(Certificate c) {
        if (c != null) {
            int countOfThemes = certificateRepo.getCountOfThemes(c.getId_theme());
            c.setAllTasks(countOfThemes);
            certificateRepo.save(c);
            return "Saved";
        } else return "something goes wrong";
    }

    @DeleteMapping
    public String removeCertificate(@RequestParam Integer id_certificate) {
        try {
            certificateRepo.deleteById(id_certificate);
            return "Removed";
        } catch (Exception e) {
            return "There is no element with that id";
        }
    }

    @PostMapping("/all")
    public Iterable<Certificate> getAllCertificatesForAccount(@RequestParam Integer id_account) {
        return certificateRepo.findAllCertificatesForAccount(id_account);
    }

    @PostMapping("/increase")
    public String increaseThemeCount(@RequestParam Integer id_account, @RequestParam Integer id_theme) {
        Optional<Certificate> c = certificateRepo.findCertificate(id_account, id_theme);
        if (c.isPresent()) {
            if (c.get().getAllTasks() > c.get().getCompletedTasks()) {
                c.get().setCompletedTasks(c.get().getCompletedTasks() + 1);
                if (c.get().getCompletedTasks() == c.get().getAllTasks()) {
                    c.get().setDateOfReceiving(LocalDateTime.now());
                    certificateRepo.save(c.get());
                    return "Certificate finished";
                }
                certificateRepo.save(c.get());
                return "Counter increased";
            } else return "Certificate already completed";
        } else return "I cant find certificate with that data";
    }

    @GetMapping("/parse")
    public Iterable<Certificate> getAllCertificatesByAccount(@RequestParam Integer id_account) {
        return certificateRepo.findAllCertificatesForAccount(id_account);
    }
}
