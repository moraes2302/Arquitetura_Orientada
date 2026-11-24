# Roteiro de demonstração e perguntas

## Preparação

Execute o build, inicie MySQL e Tomcat, abra as duas telas e o navegador conforme o README. Crie uma mesa, uma categoria e dois produtos. Use valores fáceis de conferir. Evite gravar ou mostrar as senhas nos prints.

## Apresentação em sequência

1. Mostre o diagrama de camadas e explique domínio, aplicação, infraestrutura e adaptadores.
2. Mostre `restaurante.xsd` e um WSDL: o contrato é definido antes das classes Java geradas.
3. Entre como administrador. Cadastre mesa, categoria e produto.
4. Abra o cardápio e confirme que o produto cadastrado aparece sem digitar outra base de dados.
5. Filtre categoria, busque nome/ingrediente e mostre produto indisponível.
6. Entre como garçom. Abra pedido e adicione duas unidades de um produto de R$ 55,00: total R$ 110,00.
7. Tente abrir outro pedido para a mesma mesa: deve ser recusado.
8. Altere o preço do produto na administração: o item antigo mantém seu preço histórico.
9. Altere a quantidade ou remova um item; confira o cálculo do servidor.
10. Feche a conta: a mesa fica livre e o pedido não pode ser editado.
11. Abra outro pedido e cancele: a mesa volta a ficar livre e o histórico continua registrado.
12. Mostre o QR e o link com `?mesa=1`.
13. No SoapUI, mostre uma operação válida, uma senha inválida (HTTP 401) e uma operação administrativa feita por garçom (SOAP Fault).
14. Mostre o resultado de `mvn clean verify` e os relatórios de testes.

## Perguntas possíveis

**Por que há vários módulos?** Cada módulo tem uma responsabilidade e limita suas dependências. A regra de negócio não depende de Swing, de Servlet ou de JDBC.

**Qual a diferença entre interface Repositorio e JdbcRepositorio?** A interface define as operações de persistência de que a aplicação precisa. JdbcRepositorio implementa essas operações usando SQL e transações.

**Onde fica o cálculo do pedido?** Nos tipos e nas regras do servidor. A interface apresenta o resultado; não decide o valor final.

**Por que BigDecimal?** Valores monetários exigem precisão decimal, evitando as aproximações de ponto flutuante.

**Como impede dois pedidos abertos na mesma mesa?** A aplicação verifica o estado dentro de uma transação e o banco mantém uma restrição única para a mesa associada ao pedido aberto. Os testes de concorrência verificam a disputa entre duas solicitações.

**O que o Flyway faz?** Aplica e registra migrações versionadas para criar e evoluir o esquema do banco.

**Como funciona o histórico?** O item guarda nome e preço unitário da inclusão. Alterar o produto não recalcula o passado; fechar/cancelar não apaga o pedido.

**Por que SwingWorker?** A chamada de rede fica fora da thread de eventos da interface. A conclusão atualiza os componentes na EDT, evitando que a tela trave durante a operação.

**O cardápio faz pedidos?** Não. É uma consulta pública. As operações de pedidos são autenticadas e executadas pelo garçom.

**O que é o BFF?** É a entrada HTTP/JSON adaptada ao navegador, que usa os mesmos casos de uso e banco do servidor SOAP. Não precisa repassar credenciais administrativas para o navegador.

**A segurança é igual à atividade anterior?** Não. A raiz mantém UsernameToken. A etapa de restaurante segue as aulas com autenticação HTTP Basic e autorização por papel. Fora do laboratório, HTTPS é necessário.

**O que foi feito com referência do professor?** A implementação didática do professor foi usada e identificada como base. Foram acrescentados scripts Git Bash, integração automatizada e documentação. Não apresente o material-base como se tivesse sido escrito integralmente do zero.

**H2 equivale a MySQL?** Não. H2 permite testes isolados rápidos, mas diferenças de dialeto e de execução exigem também a validação no MySQL real da instalação.
