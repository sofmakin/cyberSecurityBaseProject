package sec.project.config;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired 
    private SignupRepository signupRepository;
     private Map<String, String> accountDetails;

    @PostConstruct
    public void init() {
        this.accountDetails = new TreeMap<>();
        this.accountDetails.put("ted", "$2a$06$rtacOjuBuSlhnqMO2GKxW.Bs8J6KI0kYjw/gtF0bfErYgFyNTZRDm");
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
     
        if (!signupRepository.findByName(name).isEmpty()) {
            throw new UsernameNotFoundException("No such user: " + name);
        }

        return new org.springframework.security.core.userdetails.User(
                name,
                this.accountDetails.get(name),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}
