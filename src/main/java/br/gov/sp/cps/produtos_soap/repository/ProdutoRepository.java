package br.gov.sp.cps.produtos_soap.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

/**
 * "Banco de dados" em memória, apenas para a atividade.
 */
@Component
public class ProdutoRepository {

	private final Map<Integer, Produto> produtos = List.of(
			new Produto(1, "Notebook Inspiron 15", "Notebook 15,6 pol, 16 GB RAM, SSD 512 GB", "Dell", 25),
			new Produto(2, "Mouse sem fio MX Master 3S", "Mouse ergonômico sem fio, 8000 DPI", "Logitech", 120),
			new Produto(3, "Teclado Mecânico K552", "Teclado mecânico ABNT2 com switch azul", "Redragon", 60),
			new Produto(4, "Monitor 24 Full HD", "Monitor LED 24 polegadas, 75 Hz, HDMI", "Samsung", 0),
			new Produto(5, "Headset Gamer H320", "Headset com microfone e conexão P2", "Logitech", 42)
	).stream().collect(Collectors.toUnmodifiableMap(Produto::codigo, Function.identity()));

	public Optional<Produto> buscarPorCodigo(int codigo) {
		return Optional.ofNullable(produtos.get(codigo));
	}
}
