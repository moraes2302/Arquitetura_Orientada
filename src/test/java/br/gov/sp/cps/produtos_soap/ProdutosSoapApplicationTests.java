package br.gov.sp.cps.produtos_soap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"soap.security.username=test-user", "soap.security.password=test-password"})
class ProdutosSoapApplicationTests {

	@Test
	void contextLoads() {
	}

}
