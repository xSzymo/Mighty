package game.mightywarriors.configuration.system;

import game.mightywarriors.other.PictureOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class system {
    @Bean
    public PictureOperations pictureOperations() {
        return new PictureOperations();
    }
}