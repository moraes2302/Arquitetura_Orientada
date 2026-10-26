package br.gov.sp.cps.produtos_soap;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"soap.security.username=test-user", "soap.security.password=test-password"})
class SoapSecurityIntegrationTest {
    @Value("${local.server.port}")
    private int port;
    private final HttpClient client = HttpClient.newHttpClient();

    private HttpResponse<String> consultar(int codigo, String usuario, String senha) throws Exception {
        String header = usuario == null ? "" : """
            <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" s:mustUnderstand="1">
              <wsse:UsernameToken>
                <wsse:Username>%s</wsse:Username>
                <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">%s</wsse:Password>
              </wsse:UsernameToken>
            </wsse:Security>
            """.formatted(usuario, senha);
        String envelope = """
            <s:Envelope xmlns:s="http://schemas.xmlsoap.org/soap/envelope/" xmlns:p="http://cps.sp.gov.br/produtos">
              <s:Header>%s</s:Header>
              <s:Body><p:consultarProdutoRequest><p:codigo>%d</p:codigo></p:consultarProdutoRequest></s:Body>
            </s:Envelope>
            """.formatted(header, codigo);
        var request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + "/ws"))
            .header("Content-Type", "text/xml; charset=utf-8")
            .POST(HttpRequest.BodyPublishers.ofString(envelope)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void permiteCredenciaisValidas() throws Exception {
        var response = consultar(1, "test-user", "test-password");
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("Notebook Inspiron 15"));
        assertTrue(response.body().contains("quantidadeEstoque>25<"));
    }

    @Test
    void rejeitaAusenciaDeToken() throws Exception {
        assertSecurityFault(consultar(1, null, null));
    }

    @Test
    void rejeitaSenhaIncorreta() throws Exception {
        assertSecurityFault(consultar(1, "test-user", "incorreta"));
    }

    @Test
    void rejeitaUsuarioDesconhecido() throws Exception {
        assertSecurityFault(consultar(1, "desconhecido", "test-password"));
    }

    @Test
    void produtoInexistenteRetornaSoapFault() throws Exception {
        var response = consultar(999, "test-user", "test-password");
        assertEquals(500, response.statusCode());
        assertTrue(response.body().contains("Fault"));
        assertTrue(response.body().contains("Client"));
        assertTrue(response.body().contains("Produto não encontrado"));
        assertFalse(response.body().contains("consultarProdutoResponse"));
    }

    @Test
    void wsdlContinuaDisponivel() throws Exception {
        var request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + "/ws/produtos.wsdl")).GET().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("definitions"));
    }

    private void assertSecurityFault(HttpResponse<String> response) {
        assertEquals(500, response.statusCode());
        assertTrue(response.body().contains("Fault"));
        assertFalse(response.body().contains("Notebook Inspiron 15"));
        assertFalse(response.body().contains("test-password"));
    }
}
