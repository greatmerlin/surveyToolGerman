package ch.ffhs.pa.answerium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AnsweriumApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnsweriumApplication.class, args);
    }

    @GetMapping("/")
    public Message home() {
        return new Message("Hallo von Theo, Chantale und Oliver!");
    }

    private class Message {
        private final String msg;
        public Message(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }
    }
}
