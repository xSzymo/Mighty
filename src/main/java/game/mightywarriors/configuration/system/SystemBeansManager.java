package game.mightywarriors.configuration.system;

import game.mightywarriors.other.generators.RandomCodeFactory;
import game.mightywarriors.other.todo.PictureOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Properties;

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
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(SystemVariablesManager.EMAIL_HOST);
        mailSender.setPort(SystemVariablesManager.EMAIL_PORT);
        mailSender.setPassword(SystemVariablesManager.EMAIL_ADDRESS);
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
