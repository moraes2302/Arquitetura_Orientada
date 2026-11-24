# Validação da atualização

Data: 24/11/2026.

## Ambiente e comando

JDK 17 (Microsoft OpenJDK 17.0.21), Maven 3.9.9, Linux. O teste HTTP usa Tomcat embutido 10.1.55 e banco H2 em modo MySQL.

Executado na pasta `restaurante`:

```bash
mvn clean install
```

Neste ambiente foram usados um arquivo temporário de configuração do proxy do Maven e o truststore do sistema para baixar dependências. Esses ajustes pertencem ao ambiente de execução e não foram inseridos no projeto.

Resultado: **BUILD SUCCESS**, com os oito projetos do reactor aprovados. O comando inclui compilação, testes, geração de clientes a partir dos WSDLs, empacotamento do WAR e instalação dos módulos no repositório local.

## Testes

| Classe | Executados e aprovados | Ignorados |
|---|---:|---:|
| RestauranteTest | 3 | 0 |
| RestauranteBancoTest | 18 | 0 |
| ErrosMySqlTest | 2 | 0 |
| ConfiguracaoTest | 3 | 0 |
| VerificarAmbienteTest (diagnóstico das variáveis) | 1 | 0 |
| RestauranteHttpIT | 5 | 0 |
| MySqlLocalTest | 0 | 1 |
| **Total** | **32** | **1** |

Foram descobertos 33 testes; 32 executaram sem falhas ou erros. O diagnóstico de variáveis é herdado do material de aula e não contém asserções de negócio.

Os cinco testes HTTP iniciam o WAR realmente empacotado em porta aleatória e verificam:

1. Os quatro WSDLs protegidos: ausência/senha incorreta rejeitadas e credenciais válidas aceitas.
2. Garçom pode consultar, mas não cadastrar mesas.
3. Cadastro administrativo, abertura de pedido, bloqueio de mesa ocupada, inclusão, total, preservação de preço histórico, fechamento, cancelamento e proteção contra exclusão de registro referenciado.
4. API pública JSON, ausência de pedidos nessa projeção, HTML/JS/CSS presentes no WAR e rota pública não permitida retornando 404.
5. Número inválido para QR, geração PNG e decodificação do QR para conferir a URL exata do cardápio.

Também foi conferida a sintaxe dos scripts Bash e o assistente de sessão com entradas válidas e senhas inválidas.

## O que ainda exige conferência local

- MySQL real: não executado aqui. `MySqlLocalTest` é habilitado apenas com as variáveis específicas de teste e banco separado. H2 não substitui essa validação.
- Abertura visual das telas Swing e uso no Windows/Git Bash: as telas foram compiladas e seu cliente SOAP foi exercitado por HTTP, mas não houve sessão visual de demonstração no Windows.
- Foto externa e leitura do QR pelo celular: dependem de internet, IP da rede e configuração do computador.
- A instalação de ferramentas no computador da aluna não é feita pelo repositório. O README orienta a preparação.
- A análise audiovisual foi por telas selecionadas e pelo código acumulado do professor. Não houve transcrição integral dos 74 vídeos; confira instruções eventualmente dadas apenas no áudio antes da entrega final.

O exercício anterior de produtos, na raiz, não recebeu alterações de código nesta atualização. Os nove testes históricos dessa etapa não foram reexecutados nesta validação.
