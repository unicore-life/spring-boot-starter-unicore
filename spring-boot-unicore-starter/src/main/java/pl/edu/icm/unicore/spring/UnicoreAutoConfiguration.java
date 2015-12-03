package pl.edu.icm.unicore.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("pl.edu.icm.unicore.spring")
public class UnicoreAutoConfiguration {

    @Bean
    public Some some() {
        return new Some();
    }
}
