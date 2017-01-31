package pl.edu.icm.unicore.spring;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("pl.edu.icm.unicore.spring")
@EnableConfigurationProperties({UnicoreProperties.class})
@SpringBootConfiguration
public class UnicoreAutoConfiguration {
}
