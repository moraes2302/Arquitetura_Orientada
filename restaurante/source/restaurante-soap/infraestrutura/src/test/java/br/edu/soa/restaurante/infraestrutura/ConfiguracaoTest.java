package br.edu.soa.restaurante.infraestrutura;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConfiguracaoTest {
    @Test void configuraIdentidadesComPapeisDistintos() {
        var credenciais = new Credenciais("admin-teste-12345", "garcom-teste-67890");
        assertEquals("ADMIN", credenciais.autenticar("admin", "admin-teste-12345"));
        assertEquals("GARCOM", credenciais.autenticar("garcom", "garcom-teste-67890"));
        assertNull(credenciais.autenticar("admin", "garcom-teste-67890"));
        assertNull(credenciais.autenticar("desconhecido", "admin-teste-12345"));
        assertNull(credenciais.autenticar("admin", null));
    }

    @Test void recusaSenhasAusentesCurtasOuCompartilhadas() {
        assertThrows(IllegalStateException.class, () -> new Credenciais(null, "garcom-teste-67890"));
        assertThrows(IllegalStateException.class, () -> new Credenciais("curta", "garcom-teste-67890"));
        assertThrows(IllegalStateException.class, () -> new Credenciais("mesma-senha-12345", "mesma-senha-12345"));
    }

    @Test void bancoSemUrlExplicaConfiguracaoAusente() {
        var erro = assertThrows(IllegalStateException.class, () -> Banco.configurar(null, "usuario", "senha"));
        assertEquals("Configure DB_URL, DB_USER e DB_PASSWORD.", erro.getMessage());
    }
}
