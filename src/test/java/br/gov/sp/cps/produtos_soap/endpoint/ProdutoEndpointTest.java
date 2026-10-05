package br.gov.sp.cps.produtos_soap.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.gov.sp.cps.produtos_soap.model.ConsultarProdutoRequest;
import br.gov.sp.cps.produtos_soap.model.ConsultarProdutoResponse;
import br.gov.sp.cps.produtos_soap.repository.ProdutoRepository;

class ProdutoEndpointTest {

	private final ProdutoEndpoint endpoint = new ProdutoEndpoint(new ProdutoRepository());

	@Test
	void devolveDadosDoProdutoExistente() {
		ConsultarProdutoRequest request = new ConsultarProdutoRequest();
		request.setCodigo(1);

		ConsultarProdutoResponse response = endpoint.consultarProduto(request);

		assertEquals("Notebook Inspiron 15", response.getNome());
		assertEquals("Dell", response.getMarca());
		assertEquals(25, response.getQuantidadeEstoque());
	}

	@Test
	void devolveProdutoNaoEncontrado() {
		ConsultarProdutoRequest request = new ConsultarProdutoRequest();
		request.setCodigo(999);

		ConsultarProdutoResponse response = endpoint.consultarProduto(request);

		assertEquals("Produto não encontrado", response.getNome());
		assertEquals(0, response.getQuantidadeEstoque());
	}
}
