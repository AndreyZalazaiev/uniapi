package andrew.projects.uniapi.Controller;

import andrew.projects.uniapi.Entities.Account;
import andrew.projects.uniapi.Repos.AccountRepo;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountRepo accountRepo;


    @GetMapping
    public Iterable<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam("nickname") String nickname, @RequestParam("password") String password) {
        val acc = accountRepo.login(nickname, password);
        if (acc.isPresent() && acc.get().getActivationCode() == null) {
            return ResponseEntity.accepted().body(acc.get());
        }
        String response = "Account is not exist or not activated";
        return ResponseEntity.accepted().body(response);
    }

    @PostMapping
    public String registration(Account a) {
        if (!accountRepo.isRegistered(a.getNickname(), a.getEmail()).isPresent()) {
            String code = UUID.randomUUID().toString();
            a.setActivationCode(code);
            if (a.getRole().isEmpty()) a.setRole("User");
            accountRepo.save(a);
            return "Account registered";
        } else return "Already registered";
    }
    @PostMapping("/update")
    public String updateAccount(Account a) {
        val accountOptional = (accountRepo.findById(a.getId_account()));
        if (accountOptional.isPresent()) {
            accountRepo.update(a.getId_account(), a.getEmail(), a.getNickname(), a.getFullName(), a.getPassword(), a.getRole());
            return "Updated";
        } else return "There is no element with that id";
    }



}
