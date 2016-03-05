package pl.edu.icm.unicore.spring;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("pl.edu.icm.unicore.spring")
@Configuration
@EnableConfigurationProperties(UnicoreProperties.class)
public class UnicoreAutoConfiguration {
}
