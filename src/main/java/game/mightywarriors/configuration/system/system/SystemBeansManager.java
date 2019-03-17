package game.mightywarriors.configuration.system.system;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.other.generators.RandomCodeFactory;
import game.mightywarriors.other.todo.PictureOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Properties;

@Configuration
@EnableSwagger2
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
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(SystemVariablesManager.EMAIL_HOST);
        mailSender.setPort(SystemVariablesManager.EMAIL_PORT);
        mailSender.setPassword(SystemVariablesManager.EMAIL_PASSWORD);
        mailSender.setUsername(SystemVariablesManager.EMAIL_USERNAME);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", SystemVariablesManager.EMAIL_TRANSPORT_PROTOCOL);
        props.put("mail.smtp.auth", SystemVariablesManager.EMAIL_SMTP_AUTH);
        props.put("mail.smtp.starttls.enable", SystemVariablesManager.EMAIL_SMTP_START_TLS_ENABLE);
        props.put("mail.debug", SystemVariablesManager.EMAIL_DEBUG);

        return mailSender;
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
