# produtos-soap

Web Service SOAP (Spring Boot + Spring WS, abordagem *contract-first*) que recebe o **código de um produto**
e devolve **nome, descrição, marca e quantidade em estoque**.

## Tecnologias
Java 17 · Spring Boot 4.1.1 · Spring Web Services · JAXB (jaxb2-maven-plugin) · Maven

## Como executar
```bash
mvn clean compile        # gera as classes a partir do produtos.xsd
export SOAP_SECURITY_USERNAME=aluno
read -rsp 'Senha SOAP: ' SOAP_SECURITY_PASSWORD; echo
export SOAP_SECURITY_PASSWORD
mvn spring-boot:run      # sobe o serviço na porta 8085
```
- WSDL: http://localhost:8085/ws/produtos.wsdl
- Endpoint SOAP: http://localhost:8085/ws

## Contrato (produtos.xsd)
| Requisição `consultarProdutoRequest` | Resposta `consultarProdutoResponse` |
|---|---|
| `codigo` (int) | `nome`, `descricao`, `marca` (string), `quantidadeEstoque` (int) |

## Produtos cadastrados (em memória)
Códigos de 1 a 5. Código inexistente lança `ProdutoNaoEncontradoException` e retorna um SOAP Fault `Client` com a mensagem "Produto não encontrado".

## Testando no SoapUI
1. File > New SOAP Project, informe o WSDL `http://localhost:8085/ws/produtos.wsdl`.
2. Abra `consultarProduto` > Request 1 e use o XML de `soapui-request-exemplo.xml` (`?` -> `1`).
3. Envie e confira a resposta.

## Parte 2 — WS-Security e SOAP Fault

O endpoint exige um cabeçalho WS-Security `UsernameToken`, validado pelo Apache WSS4J antes de executar a consulta. Usuário e senha são lidos de `SOAP_SECURITY_USERNAME` e `SOAP_SECURITY_PASSWORD`, sem credenciais padrão no código. A aplicação exige essas variáveis para iniciar.

No SoapUI, configure uma **Outgoing WS-Security Configuration**, adicione **Username** e informe as mesmas credenciais do servidor. Selecione `PasswordText` e aplique a configuração à requisição. O arquivo XML de exemplo contém apenas o corpo funcional; sem o cabeçalho de segurança, a requisição será rejeitada. Use HTTPS fora do desenvolvimento local: WS-Security UsernameToken não cifra o transporte nem o corpo da mensagem.

- Credenciais válidas e código de 1 a 5: resposta SOAP normal.
- Token ausente, usuário desconhecido ou senha incorreta: SOAP Fault de segurança; a consulta não é executada.
- Credenciais válidas e código inexistente: SOAP Fault `Client`, "Produto não encontrado".
- Exceção inesperada: SOAP Fault `Server` com mensagem genérica, sem expor detalhes internos.

Execute `mvn clean verify`. Os testes de integração iniciam o servidor em porta aleatória e usam credenciais exclusivas de teste, sem depender de variáveis externas. Cobrem autenticação, acesso ao WSDL e produto inexistente.

### Fluxo Git

As alterações da parte 2 ficam na branch `seguranca`, em commits separados. Envie com `git push -u origin seguranca`. Após a validação, faça o merge na `main` e envie ao GitHub; não antecipe essa etapa antes da aprovação.
