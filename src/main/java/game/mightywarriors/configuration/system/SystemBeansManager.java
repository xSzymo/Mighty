package game.mightywarriors.configuration.system;

import game.mightywarriors.other.generators.RandomCodeFactory;
import game.mightywarriors.other.todo.PictureOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SystemBeansManager {
    @Bean
    public PictureOperations pictureOperations() {
        return new PictureOperations();
    }

    @Bean
    public RandomCodeFactory randomCodeFactory() {
        return new RandomCodeFactory();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}