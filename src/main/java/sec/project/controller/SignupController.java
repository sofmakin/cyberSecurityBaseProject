package sec.project.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    private Map<String, String>events;
    
    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String password, @RequestParam String event, @RequestParam String description) { 
       if(!signupRepository.findByName(name).isEmpty()) {
           return "redirect:/doubleSignup?name=" + name;
       }else{
        events.put(event, description);
        signupRepository.save(new Signup(name,password));
        return "done";
    }
    }

    @RequestMapping(value="/doubleSignup", method = RequestMethod.GET)
    @ResponseBody
    public String doubleSignup(@RequestParam String name) {
        if(!signupRepository.findByName(name).isEmpty()) {
        return name + ": this user hasn't been registered.";
        
    }else{   
        return name + " has been registered with this address: " + signupRepository.findByName(name).get(0).getAddress();
        }
}
}