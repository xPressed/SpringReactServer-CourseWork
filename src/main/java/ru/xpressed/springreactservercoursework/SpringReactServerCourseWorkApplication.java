package ru.xpressed.springreactservercoursework;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "SpringReact-CourseWork", contact = @Contact(name = "xPressed", url = "https://github.com/xPressed")))
public class SpringReactServerCourseWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringReactServerCourseWorkApplication.class, args);
    }

}
