package br.gov.sp.cps.produtos_soap.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import br.gov.sp.cps.produtos_soap.model.ConsultarProdutoRequest;
import br.gov.sp.cps.produtos_soap.model.ConsultarProdutoResponse;
import br.gov.sp.cps.produtos_soap.repository.ProdutoRepository;
import br.gov.sp.cps.produtos_soap.exception.ProdutoNaoEncontradoException;

@Endpoint
public class ProdutoEndpoint {

	private static final String NAMESPACE = "http://cps.sp.gov.br/produtos";

	private final ProdutoRepository repository;

	public ProdutoEndpoint(ProdutoRepository repository) {
		this.repository = repository;
	}

	@PayloadRoot(namespace = NAMESPACE, localPart = "consultarProdutoRequest")
	@ResponsePayload
	public ConsultarProdutoResponse consultarProduto(@RequestPayload ConsultarProdutoRequest request) {

		ConsultarProdutoResponse response = new ConsultarProdutoResponse();

        var produto = repository.buscarPorCodigo(request.getCodigo())
                .orElseThrow(() -> new ProdutoNaoEncontradoException(request.getCodigo()));
        response.setNome(produto.nome());
        response.setDescricao(produto.descricao());
        response.setMarca(produto.marca());
        response.setQuantidadeEstoque(produto.quantidadeEstoque());

		return response;
	}
}
