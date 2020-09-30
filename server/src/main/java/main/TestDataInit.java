package main;

import main.entity.Diagnosis;
import main.entity.Patient;
import main.entity.User;
import main.entity.Ward;
import main.repository.DiagnosisRepository;
import main.repository.PatientRepository;
import main.repository.UserRepository;
import main.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Collections;

@Component
public class TestDataInit implements CommandLineRunner {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DiagnosisRepository diagnosisRepository;

    @Autowired
    WardRepository wardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder pwdEncoder;

    @Override
    public void run(String[] args) throws Exception {
        Diagnosis d1 = new Diagnosis("K64.0");
        Diagnosis d2 = new Diagnosis("N48.4");
        Ward w1 = new Ward("firstTestWard", 10L);
        Ward w2 = new Ward("secondTestWard", 11L);
        wardRepository.save(w1);
        wardRepository.save(w2);
        diagnosisRepository.save(d1);
        diagnosisRepository.save(d2);
        patientRepository.save(new Patient("John", "Doe", "F", d2, w1));
        patientRepository.save(new Patient("Jane", "Smith", "X", d1, w2));

        userRepository.save(new User("user", pwdEncoder.encode("pwd"), Collections.singletonList("ROLE_USER")));
        userRepository.save(new User("admin", pwdEncoder.encode("apwd"), Collections.singletonList("ROLE_ADMIN")));
    }
}
