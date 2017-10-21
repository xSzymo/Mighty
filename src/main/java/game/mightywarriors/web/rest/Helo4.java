package game.mightywarriors.web.rest;


import game.mightywarriors.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Helo4 {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("getPrincipal")
    public Object halo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal();
    }

    @GetMapping("getCurrentUser")
    public Object halo1() {
        return userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}