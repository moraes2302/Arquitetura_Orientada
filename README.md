# produtos-soap

Web Service SOAP (Spring Boot + Spring WS, abordagem *contract-first*) que recebe o **código de um produto**
e devolve **nome, descrição, marca e quantidade em estoque**.

## Tecnologias
Java 17 · Spring Boot 4.1.1 · Spring Web Services · JAXB (jaxb2-maven-plugin) · Maven

## Como executar
```bash
mvn clean compile        # gera as classes a partir do produtos.xsd
mvn spring-boot:run      # sobe o serviço na porta 8085
```
- WSDL: http://localhost:8085/ws/produtos.wsdl
- Endpoint SOAP: http://localhost:8085/ws

## Contrato (produtos.xsd)
| Requisição `consultarProdutoRequest` | Resposta `consultarProdutoResponse` |
|---|---|
| `codigo` (int) | `nome`, `descricao`, `marca` (string), `quantidadeEstoque` (int) |

## Produtos cadastrados (em memória)
Códigos de 1 a 5. Código inexistente retorna "Produto não encontrado" e estoque 0.

## Testando no SoapUI
1. File > New SOAP Project, informe o WSDL `http://localhost:8085/ws/produtos.wsdl`.
2. Abra `consultarProduto` > Request 1 e use o XML de `soapui-request-exemplo.xml` (`?` -> `1`).
3. Envie e confira a resposta.
