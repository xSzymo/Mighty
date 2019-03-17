package game.mightywarriors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MightyWarriorsApplication {

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(20000);
        SpringApplication.run(MightyWarriorsApplication.class, args);
    }
}
