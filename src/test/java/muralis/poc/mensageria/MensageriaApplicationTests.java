package muralis.poc.mensageria;

import muralis.poc.mensageria.config.DockerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MensageriaApplicationTests extends DockerConfig {

	@Test
	void contextLoads() {
	}

}
