package tk.gvfs.automacaoresidencial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AutomacaoResidencialManagerApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(AutomacaoResidencialManagerApplication.class, args);
	}

} 
