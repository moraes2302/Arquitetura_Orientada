# Restaurante SOA — sequência completa das aulas

Implementação didática do sistema mostrado nos vídeos do professor Sandro: Java 17, Maven multimódulo, MySQL, JDBC/Flyway, SOAP contract-first com Metro, Tomcat 10.1, cliente Swing e cardápio público HTML/CSS/JavaScript.

O código-base foi adaptado do [repositório apresentado nas aulas](https://github.com/Prof-Sandro/tutorial-restaurante-soa/tree/62-escrever-html-semantico), commit `cd30154`. Consulte [a origem e a cobertura](docs/ORIGEM_E_COBERTURA.md). Não se trata de código integralmente autoral da aluna. A etapa anterior de consulta de produtos permanece na raiz do repositório.

## Organização

| Pasta | Responsabilidade |
|---|---|
| `source/restaurante-soap/dominio` | Mesa, categoria, produto, pedido e item; estados e valores monetários. |
| `source/restaurante-soap/aplicacao` | Regras de negócio e interface de persistência. |
| `source/restaurante-soap/infraestrutura` | JDBC, transações, credenciais externas, migração Flyway e QR Code. |
| `source/restaurante-soap/contrato` | XSD, quatro WSDLs e geração de tipos/clientes com wsimport. |
| `source/restaurante-soap/servidor` | WAR, filtros, autorização por papel, endpoints SOAP e API pública de leitura. |
| `source/restaurante-soap/desktop` | Cadastros administrativos e operação de pedidos pelo garçom, com SwingWorker. |
| `cardapio-web` | Cardápio responsivo com filtro por categoria, busca, preços e indisponibilidade. |
| `diagramas` | Fronteiras, entidades e exemplo visual do material de aula. |

## 1. Preparar o ambiente

Instale JDK 17 ou superior, Maven 3.9+, MySQL 8+ e Tomcat **10.1**. Não use Tomcat 9: os serviços usam `jakarta.*`.

No Git Bash, dentro de `Arquitetura_Orientada`:

```bash
cd restaurante
java -version
javac -version
mvn -version
```

O projeto desta etapa tem seu próprio `pom.xml`. Execute os comandos abaixo **na pasta `restaurante`**, não na raiz do projeto anterior.

## 2. Compilar, testar e instalar os módulos locais

```bash
mvn clean install
```

`install` executa também a fase `verify` e registra os módulos no repositório Maven local, necessário para iniciar o desktop em outro terminal. Para somente validar, use `mvn clean verify`.

Os testes comuns usam H2 em modo MySQL. Os testes HTTP iniciam o WAR em um Tomcat de teste em porta aleatória e exercitam o mesmo cliente SOAP das telas. Não precisam do MySQL externo. A validação em MySQL real é opt-in e deve ser registrada separadamente.

O servidor será gerado em:

```text
source/restaurante-soap/servidor/target/restaurante.war
```

## 3. Criar o banco MySQL

Inicie seu MySQL. Abra o MySQL Workbench ou o cliente `mysql` e execute o conteúdo de `scripts/criar-banco.sql`.

Isso cria `restaurante_soap` e `restaurante_teste`. O Flyway cria as tabelas ao iniciar o servidor; não é necessário criá-las manualmente.

Use um usuário MySQL existente que tenha acesso ao banco. O assistente sugere `root` para laboratório local; informe o usuário e a senha reais da sua instalação. As senhas `admin` e `garcom` do aplicativo são escolhidas separadamente.

## 4. Configurar a sessão e iniciar o Tomcat

Na pasta `restaurante`, no Git Bash:

```bash
source scripts/configurar-sessao.sh
```

Responda às perguntas. A senha não aparece enquanto você digita. As senhas do administrador e do garçom devem ter pelo menos 12 caracteres e ser diferentes. As variáveis valem somente para essa sessão.

Defina a pasta onde o Tomcat foi extraído, usando seu caminho real:

```bash
export CATALINA_HOME='C:/Ferramentas/apache-tomcat-10.1.x'
bash scripts/iniciar-tomcat.sh
```

O script copia o WAR e inicia o Tomcat **nesse mesmo terminal**, para que ele receba as variáveis. Um Tomcat já aberto pelo Eclipse ou como serviço do Windows não herda automaticamente essa configuração. Pare a outra instância antes de usar a porta 8080.

Deixe o terminal aberto. Para encerrar o servidor, pressione Ctrl+C.

## 5. Abrir a administração e cadastrar os dados

Abra outro Git Bash, entre em `Arquitetura_Orientada/restaurante` e execute:

```bash
bash scripts/desktop.sh admin
```

Entre com usuário `admin` e a senha escolhida na configuração do servidor. Cadastre, nesta ordem:

1. Mesa número 1.
2. Categoria `Massas`, ativa.
3. Produto `Carbonara`, da categoria Massas, preço `55.00`, porção `1 pessoa`, disponível.

A imagem pode ser uma URL HTTPS, como as imagens utilizadas pelo professor. Nunca informe um caminho local `C:\...` como imagem do cardápio. As fotos do exemplo dependem de conexão externa.

## 6. Abrir a tela do garçom

Em outro terminal, na mesma pasta:

```bash
bash scripts/desktop.sh garcom
```

Entre com usuário `garcom` e a outra senha escolhida. Selecione a mesa, abra um pedido, selecione o produto e adicione quantidade 2. O total esperado para duas Carbonaras de R$ 55,00 é **R$ 110,00**.

Teste também alterar quantidade, remover item, fechar conta e cancelar outro pedido. Um pedido vazio só pode ser cancelado. Fechar ou cancelar libera a mesa. Pedidos encerrados não aceitam novos itens. O preço e o nome do produto são preservados no histórico de cada inclusão.

## 7. Abrir o cardápio e o QR Code

- Cardápio: <http://localhost:8080/restaurante/cardapio/index.html?mesa=1>
- JSON público: <http://localhost:8080/restaurante/api/public/cardapio>
- Imagem QR: <http://localhost:8080/restaurante/api/public/qr?mesa=1>

Os clientes consultam o cardápio sem senha e pedem ao garçom. A API pública não permite criar pedidos. Para ler o QR no celular, ajuste `PUBLIC_BASE_URL` antes de iniciar o servidor para o IP do computador acessível na mesma rede, por exemplo `http://192.168.0.10:8080/restaurante`. `localhost` no celular aponta para o próprio celular.

## 8. Testar no SoapUI

Nesta etapa, seguindo a aula 41, a autenticação é **HTTP Basic** com papéis `ADMIN` e `GARCOM`. O exercício anterior da raiz usa WS-Security UsernameToken; são configurações diferentes.

Importe um destes WSDLs usando as credenciais HTTP Basic do servidor:

- `http://localhost:8080/restaurante/soap/mesas?wsdl`
- `http://localhost:8080/restaurante/soap/categorias?wsdl`
- `http://localhost:8080/restaurante/soap/produtos?wsdl`
- `http://localhost:8080/restaurante/soap/pedidos?wsdl`

Na aba Auth da requisição, configure Basic com usuário e senha. Se necessário, habilite envio antecipado de autenticação. O WSDL também é protegido; não confunda falha de autenticação na importação com servidor indisponível. Em implantação fora do laboratório, use HTTPS.

Confira: usuário ausente/senha errada retorna HTTP 401; garçom tentando cadastrar retorna SOAP Fault `ACESSO_NEGADO`; mesa ocupada retorna `MESA_OCUPADA`; produto inexistente retorna erro de negócio; operações válidas devolvem os dados.

## 9. Validar com MySQL real (opcional no build, necessário para conferir sua instalação)

Use somente o banco separado `restaurante_teste`. Os dados de teste permanecem nesse banco.

```bash
export MYSQL_TEST_URL='jdbc:mysql://localhost:3306/restaurante_teste?connectionTimeZone=UTC'
export MYSQL_TEST_USER='root'
read -rsp 'Senha MySQL: ' MYSQL_TEST_PASSWORD; echo
export MYSQL_TEST_PASSWORD
mvn clean verify
```

Sem `MYSQL_TEST_URL`, esse teste fica ignorado intencionalmente. Um build com H2 aprovado não comprova a instalação MySQL do computador.

## Evidências e apresentação

Veja [o roteiro de demonstração](docs/DEMONSTRACAO.md) e [o relatório de validação](docs/VALIDACAO.md). O cadastro inicial está vazio de propósito: cadastre seus dados na administração para apresentar a integração entre telas, SOAP, banco e cardápio.
