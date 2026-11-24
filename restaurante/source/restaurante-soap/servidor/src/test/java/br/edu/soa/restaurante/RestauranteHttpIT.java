package br.edu.soa.restaurante;

import br.edu.soa.restaurante.soap.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.*;
import java.nio.file.*;
import java.util.*;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/** Verifica o WAR empacotado, HTTP real e o mesmo cliente SOAP usado pelas telas. */
class RestauranteHttpIT {
    @org.junit.jupiter.api.io.TempDir static Path temporario;
    private static Tomcat tomcat;
    private static String base;
    private static final String ADMIN = "admin-integracao-12345";
    private static final String GARCOM = "garcom-integracao-67890";
    private static final HttpClient HTTP = HttpClient.newHttpClient();
    private static final List<String> CHAVES = List.of("DB_URL", "DB_USER", "DB_PASSWORD",
            "RESTAURANTE_ADMIN_PASSWORD", "RESTAURANTE_GARCOM_PASSWORD", "PUBLIC_BASE_URL");
    private static final Map<String, String> anteriores = new HashMap<>();

    @BeforeAll static void iniciar() throws Exception {
        for (String k : CHAVES) anteriores.put(k, System.getProperty("restaurante." + k));
        System.setProperty("restaurante.DB_URL", "jdbc:h2:mem:http_" + UUID.randomUUID() + ";MODE=MySQL;DB_CLOSE_DELAY=-1");
        System.setProperty("restaurante.DB_USER", "sa");
        System.setProperty("restaurante.DB_PASSWORD", "");
        System.setProperty("restaurante.RESTAURANTE_ADMIN_PASSWORD", ADMIN);
        System.setProperty("restaurante.RESTAURANTE_GARCOM_PASSWORD", GARCOM);
        tomcat = new Tomcat();
        Files.createDirectories(temporario.resolve("webapps"));
        tomcat.setBaseDir(temporario.toString());
        tomcat.setPort(0);
        tomcat.getConnector();
        var ctx = tomcat.addWebapp("/restaurante", Path.of("target/restaurante.war").toAbsolutePath().toString());
        var scanner = (org.apache.tomcat.util.scan.StandardJarScanner) ctx.getJarScanner();
        scanner.setScanClassPath(false);
        scanner.setScanManifest(false);
        ctx.setParentClassLoader(RestauranteHttpIT.class.getClassLoader());
        tomcat.start();
        assertTrue(ctx.getState().isAvailable(), "O WAR deve iniciar sem erros");
        base = "http://localhost:" + tomcat.getConnector().getLocalPort() + "/restaurante";
        System.setProperty("restaurante.PUBLIC_BASE_URL", base);
        System.setProperty("soap.base", base);
    }

    @AfterAll static void parar() throws Exception {
        try { if (tomcat != null) { tomcat.stop(); tomcat.destroy(); } }
        finally {
            for (String k : CHAVES) {
                String antigo = anteriores.get(k);
                if (antigo == null) System.clearProperty("restaurante." + k);
                else System.setProperty("restaurante." + k, antigo);
            }
            System.clearProperty("soap.base");
        }
    }

    private HttpResponse<byte[]> get(String path, String auth) throws Exception {
        var req = HttpRequest.newBuilder(URI.create(base + path)).GET();
        if (auth != null) req.header("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        return HTTP.send(req.build(), HttpResponse.BodyHandlers.ofByteArray());
    }

    @Test void protegeOsQuatroServicos() throws Exception {
        for (String entidade : List.of("mesas", "categorias", "produtos", "pedidos")) {
            assertEquals(401, get("/soap/" + entidade + "?wsdl", null).statusCode());
            assertEquals(401, get("/soap/" + entidade + "?wsdl", "admin:incorreta").statusCode());
            var wsdl = get("/soap/" + entidade + "?wsdl", "admin:" + ADMIN);
            assertEquals(200, wsdl.statusCode());
            assertTrue(new String(wsdl.body(), java.nio.charset.StandardCharsets.UTF_8).contains("definitions"));
        }
    }

    @Test void garcomConsultaMasNaoCadastra() throws Exception {
        var garcom = new ConexaoSoap("garcom", GARCOM);
        assertNotNull(garcom.mesas().listarMesas());
        var entrada = new MesaEntrada(); entrada.setNumero(999);
        var falha = assertThrows(ErroNegocio_Exception.class, () -> garcom.mesas().criarMesa(entrada));
        assertEquals("ACESSO_NEGADO", falha.getFaultInfo().getCodigo());
    }

    @Test void cicloCompletoDoPedidoEPreservacaoDoHistorico() throws Exception {
        var admin = new ConexaoSoap("admin", ADMIN);
        var garcom = new ConexaoSoap("garcom", GARCOM);
        var m = new MesaEntrada(); m.setNumero(42);
        var mesa = admin.mesas().criarMesa(m);
        var c = new CategoriaEntrada(); c.setNome("Massas de teste"); c.setDescricao("Integração"); c.setAtivo(true);
        var categoria = admin.categorias().criarCategoria(c);
        var p = new ProdutoEntrada(); p.setCategoriaId(categoria.getId()); p.setNome("Carbonara");
        p.setDescricao("Massa artesanal"); p.setImagem(""); p.setPreco(new BigDecimal("55.00"));
        p.setPorcao("1 pessoa"); p.setDisponivel(true);
        var produto = admin.produtos().criarProduto(p);
        var pedido = garcom.pedidos().abrirPedido(mesa.getId());
        var duplicado = assertThrows(ErroNegocio_Exception.class, () -> garcom.pedidos().abrirPedido(mesa.getId()));
        assertEquals("MESA_OCUPADA", duplicado.getFaultInfo().getCodigo());
        var comItem = garcom.pedidos().adicionarItem(pedido.getId(), produto.getId(), 2);
        assertEquals(0, new BigDecimal("110.00").compareTo(comItem.getTotal()));
        p.setPreco(new BigDecimal("60.00")); admin.produtos().atualizarProduto(produto.getId(), p);
        assertEquals(0, new BigDecimal("110.00").compareTo(garcom.pedidos().consultarPedido(pedido.getId()).getTotal()));
        garcom.pedidos().fecharPedido(pedido.getId());
        assertEquals(SituacaoMesa.LIVRE, garcom.mesas().consultarMesa(mesa.getId()).getSituacao());
        assertEquals("PEDIDO_FECHADO", assertThrows(ErroNegocio_Exception.class,
                () -> garcom.pedidos().adicionarItem(pedido.getId(), produto.getId(), 1)).getFaultInfo().getCodigo());
        var novo = garcom.pedidos().abrirPedido(mesa.getId());
        garcom.pedidos().cancelarPedido(novo.getId());
        assertEquals(SituacaoMesa.LIVRE, garcom.mesas().consultarMesa(mesa.getId()).getSituacao());
        assertEquals("REFERENCIADO", assertThrows(ErroNegocio_Exception.class,
                () -> admin.produtos().excluirProduto(produto.getId())).getFaultInfo().getCodigo());
    }

    @Test void cardapioPublicoEArquivosWeb() throws Exception {
        var resposta = get("/api/public/cardapio", null);
        assertEquals(200, resposta.statusCode());
        var dados = new ObjectMapper().readTree(resposta.body());
        assertTrue(dados.get("categorias").isArray());
        assertTrue(dados.get("produtos").isArray());
        assertFalse(dados.has("pedidos"));
        for (String arquivo : List.of("index.html", "app.js", "style.css"))
            assertEquals(200, get("/cardapio/" + arquivo, null).statusCode());
        assertEquals(404, get("/api/public/pedidos", null).statusCode());
    }

    @Test void qrCodePublicoValidaMesaEGeraPng() throws Exception {
        assertEquals(400, get("/api/public/qr?mesa=abc", null).statusCode());
        assertEquals(400, get("/api/public/qr?mesa=0", null).statusCode());
        var qr = get("/api/public/qr?mesa=42", null);
        assertEquals(200, qr.statusCode());
        assertEquals("image/png", qr.headers().firstValue("Content-Type").orElseThrow());
        assertArrayEquals(new byte[] {(byte)137,80,78,71,13,10,26,10}, Arrays.copyOf(qr.body(), 8));
        var imagem = javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(qr.body()));
        var bitmap = new com.google.zxing.BinaryBitmap(new com.google.zxing.common.HybridBinarizer(
                new com.google.zxing.client.j2se.BufferedImageLuminanceSource(imagem)));
        assertEquals(base + "/cardapio/index.html?mesa=42", new com.google.zxing.MultiFormatReader().decode(bitmap).getText());
    }
}
