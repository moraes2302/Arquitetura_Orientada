# Origem do material e cobertura das aulas

Fonte fornecida pela aluna: [pasta de vídeos do professor](https://drive.google.com/drive/folders/1KWgCcuR6SEroKU2Kyw6STHyrMWJUA8wm).

Nas telas das aulas 10 e 11 foi identificado o repositório `Prof-Sandro/tutorial-restaurante-soa`. A branch `62-escrever-html-semantico`, commit `cd30154`, contém a implementação cumulativa demonstrada nas últimas aulas. O código dessa etapa foi incorporado como base didática, mantendo a identificação do professor e acrescentando integração, verificações e instruções locais.

Foram examinadas telas selecionadas dos vídeos 09.03, 10.01, 11, 13, 61 e 62 e consultado o código acumulado das branches de aula. **Não foi realizada uma transcrição integral nem uma revisão audiovisual completa dos 74 arquivos.** A cobertura abaixo é fundamentada no código de referência e nos títulos; exigências ditas somente no áudio podem precisar de conferência adicional.

## Entregas identificadas

| Aulas | Conteúdo | Local |
|---|---|---|
| Instalações, 07–09 | Java/Maven, MySQL, Tomcat, ambiente e Git | Guia principal e scripts de sessão |
| 10–11 | Fronteiras SOA, camadas e dependências | `diagramas/` e `docs/camadas.md` |
| 12 | Estrutura Maven multimódulo | POMs raiz e módulos |
| 13–19 | Estados, mesas, categorias, produtos, pedidos e itens | `dominio/` |
| 20–26 | Casos de uso, regras, porta de persistência e testes | `aplicacao/` |
| 27–32 | JDBC, Flyway, MySQL, credenciais, histórico e concorrência | `infraestrutura/` |
| 33–38 | XSD, quatro WSDLs e geração de Java | `contrato/` |
| 39–43 | WAR, composição, autenticação, autorização, Faults e DTOs | `servidor/` |
| 44–47 | Endpoints de mesa, categoria, produto e pedido | `servidor/` |
| 48–51 | QR, BFF HTTP/JSON público, filtros e Tomcat | `servidor/`, `QrCode` e scripts |
| 52–59 | Cliente SOAP, SwingWorker, cadastros e janelas | `desktop/` |
| 59.01 | URLs HTTPS das fotos externas | Produtos e cardápio |
| 60 | Integração das telas com SOAP | Cliente compartilhado, teste HTTP e roteiro manual |
| 61–62 | Cardápio HTML/CSS/JS e demonstração integrada | `cardapio-web/` |

## Adaptações nesta entrega

- Nova etapa isolada em `restaurante/`, preservando o exercício original e seu histórico.
- Testes de integração HTTP do WAR: autenticação, papéis, ciclo do pedido, arquivos estáticos e QR decodificado.
- Validação da configuração JDBC antes de ler a URL, evitando NullPointerException com variável ausente.
- Scripts Git Bash para configurar sessão, iniciar Tomcat e abrir cada tela.
- Correção dos textos de solicitação de senha do script PowerShell, sem mostrar senhas como prompts.
- Documentação de execução, limites de validação, demonstração e fonte do material.

## Arquivos da pasta fornecida

- [01.01Instalar-git-bash.mp4](https://drive.google.com/file/d/1ajj3ZnUmXcfqWpMnNOttxDW1DZP97LTm/view?usp=drivesdk)
- [01.02instalar-java.mp4](https://drive.google.com/file/d/1Ek6BNXb2_hnilwXWp-XpFV8mgdXp1Prx/view?usp=drivesdk)
- [01.03instalar-eclipse.mp4](https://drive.google.com/file/d/12af_MRaFmkT0nycBSnQcPV0nzNlBP-Rr/view?usp=drivesdk)
- [01.04instaler-maven.mp4](https://drive.google.com/file/d/1ljK6maqnT1cEo9LyEJW1bRJcZ0mVtTg2/view?usp=drivesdk)
- [01.05Instalar-xampp.mp4](https://drive.google.com/file/d/1cJoThe5pOHDNr5NrtseckE_RI_0jc-Dy/view?usp=drivesdk)
- [01.06Instalar-vscode.mp4](https://drive.google.com/file/d/1BkF1MpxdC23Z40kVDRhDdO0FatzUSaCX/view?usp=drivesdk)
- [01.07Instalr-php.mp4](https://drive.google.com/file/d/1pkzuk_IAyCisgdZVJ03RaM3IQbLkQjSk/view?usp=drivesdk)
- [01.08Instalar-composer.mp4](https://drive.google.com/file/d/1sg8b4ZGqIEt4f2APr0wF54TguxjJfQal/view?usp=drivesdk)
- [01.09instalarNode.mp4](https://drive.google.com/file/d/164A462vGhYi4OO0bBZ_-g05QTex9XpqP/view?usp=drivesdk)
- [07.criar_bancos_no_mysql.mp4](https://drive.google.com/file/d/1QYE9DlsIeb-5EkFzx_W4GWPHPz4p1ZCg/view?usp=drivesdk)
- [08.instalar_tomcat.mp4](https://drive.google.com/file/d/1eC_uSXY2r1qS8xoowE6_uFQHofsPb0rK/view?usp=drivesdk)
- [09.01.registrar o tomcat no eclipse.mp4](https://drive.google.com/file/d/1eQjO9WNucVGXPl6WiqREjzMwkmPl7vWS/view?usp=drivesdk)
- [09.02-estrutura-de-projetos.mp4](https://drive.google.com/file/d/12qxj6wTBWUtGO-E4-wK1M5OjC2wKjNkq/view?usp=drivesdk)
- [09.03configurando-git.mp4](https://drive.google.com/file/d/1BV1acNs2DunVsOKt22xn56LTw6i83sBe/view?usp=drivesdk)
- [10.01Desenhar as fronteiras SOA.mp4](https://drive.google.com/file/d/1figHO4TxLmWqMeL5wCIyfZ65Np6ZAdvB/view?usp=drivesdk)
- [10.02Desenhar as fronterias.mp4](https://drive.google.com/file/d/1FfHLzlGlJSu2TPm1c8dlHDj4UfkLZJ1j/view?usp=drivesdk)
- [11-definir-camadas-e-dependencias.mp4](https://drive.google.com/file/d/1jjwztZ9F9FMt0UmOxq-ncNaP3D59Epj1/view?usp=drivesdk)
- [12.01criar-os-primerios-poms-do-zero.mp4](https://drive.google.com/file/d/1p62sXm4ZEJwOlWIMtZHEhjPtdrz9g2UH/view?usp=drivesdk)
- [12.02criar-os-primerios-poms-do-zero.mp4](https://drive.google.com/file/d/1b-IwAhKCAfLzOS_ujpVTCo1P3XFSIUhM/view?usp=drivesdk)
- [12.03 importar projeto eclipse.mp4](https://drive.google.com/file/d/1cBVtz_7uq3-wzCvcqyygWw8BCQP2mqMS/view?usp=drivesdk)
- [13Criarmodulo dominio e seus estados.mp4](https://drive.google.com/file/d/1bTOjjHYS5vkXQFnAVyQh_hwhQwWJQxUa/view?usp=drivesdk)
- [14 Implementar Mesa.mp4](https://drive.google.com/file/d/1qsa-65RoorYEogRtGRpu2w3KEqswyfPY/view?usp=drivesdk)
- [15 implementar Categoria.mp4](https://drive.google.com/file/d/1Vy3pTvIiVnEt-i03YBniLvM3VLgygLri/view?usp=drivesdk)
- [16 impelmentar produto.mp4](https://drive.google.com/file/d/1Wraa7o4WUYgnolTxPFnT9fNkJTiTtaFs/view?usp=drivesdk)
- [17 implementar item pedido.mp4](https://drive.google.com/file/d/11obcxa2DBZs5WpEZfyV77MBGD6dDj8Px/view?usp=drivesdk)
- [18 implementar pedido.mp4](https://drive.google.com/file/d/1vKEdw72077PC8M2a8BETIk4GZahHz2pU/view?usp=drivesdk)
- [19 cnferir os relacionamentos do dominio.mp4](https://drive.google.com/file/d/1h-sKqq-E3OoABmeonNqN6eTwuRYR9isd/view?usp=drivesdk)
- [20 Criar aplicação e seus erros.mp4](https://drive.google.com/file/d/1dST_Xl6KSC1glUUgA1JA1xAUKjTSGUTc/view?usp=drivesdk)
- [21 Definir porta de persistencia.mp4](https://drive.google.com/file/d/1bhnsCQJfPXKep4zt0FsGDldVWrjpHI7p/view?usp=drivesdk)
- [22 implementar regras de mesas.mp4](https://drive.google.com/file/d/1zg2LBz4Y2jUDP_buWDhCEfMn5rT8cIwR/view?usp=drivesdk)
- [23Acrescentar regras de categorias.mp4](https://drive.google.com/file/d/1OHg4JAF6RYK4A1Z7mgzFsjU-8So2XJiC/view?usp=drivesdk)
- [24 Acrescentar regras do pedido.mp4](https://drive.google.com/file/d/1rsoZHQ5x1sWoLdphSfmGrifdhmt3wwt9/view?usp=drivesdk)
- [25 implementar ciclo do pedido.mp4](https://drive.google.com/file/d/1lxFtT_2ZSCA2XwlxvS_DFMulq5maDy-S/view?usp=drivesdk)
- [26 Escrever as primeiros testes de regras.mp4](https://drive.google.com/file/d/1kOIB26m_Sq-YNwv2NVDqAcudcix2x9WW/view?usp=drivesdk)
- [27 Criar o módulo de infraestrutura.mp4](https://drive.google.com/file/d/1SMfagUuGZ9WmrnkcpuIVWeQWfprGPwpL/view?usp=drivesdk)
- [28 Escrever a migração do MySQL.mp4](https://drive.google.com/file/d/1wF2-OIWzosDuC4JPwVGEwHUYXas3Ay8-/view?usp=drivesdk)
- [29 Configurar conexões e Flyway.mp4](https://drive.google.com/file/d/1sWz-LLYOPH7kibkuO0d_lRfxTghc_zwf/view?usp=drivesdk)
- [29.02 Configurar conexões e Flyway.mp4](https://drive.google.com/file/d/1FY_0PXcZaoz8_HpmqIU1tG4lNNujECzm/view?usp=drivesdk)
- [30 Implementar o repositório JDBC.mp4](https://drive.google.com/file/d/1tzxCPCeW37TTJ3ImQnCU9724VNG50C66/view?usp=drivesdk)
- [31 Implementar credenciais externas.mp4](https://drive.google.com/file/d/1BUDtK4KMb9fZY_InfF6-3hQP3N6Kn1pn/view?usp=drivesdk)
- [32 Testar repositório, histórico e concorrência.mp4](https://drive.google.com/file/d/1mbKpzut7XI2P3hp-8FUnKO-8RQnD8oTZ/view?usp=drivesdk)
- [33 Escrever os tipos e mensagens XSD.mp4](https://drive.google.com/file/d/1BHySojl3jl01Q61CJ6zJLFHdf65i3bX_/view?usp=drivesdk)
- [34 Escrever o WSDL de Mesa.mp4](https://drive.google.com/file/d/1FjKqyNOM1HW81ea3J4jSrrBFoi4vDUhe/view?usp=drivesdk)
- [35 Escrever o WSDL de Categoria.mp4](https://drive.google.com/file/d/1BPHN-UrJWBhK1GxeVA3hYE_4noSi8VG8/view?usp=drivesdk)
- [36 Escrever o WSDL de Produto.mp4](https://drive.google.com/file/d/1-1bziypW-iJOKz3LnKXn72zFmb22i6pt/view?usp=drivesdk)
- [37 Escrever o WSDL de Pedido.mp4](https://drive.google.com/file/d/15QX3mzrLrCh3CNAtBvVHZw-9LHryQTsA/view?usp=drivesdk)
- [38 Gerar Java a partir dos contratos.mp4](https://drive.google.com/file/d/1tLl9veMqQk5sUrwPAsM5tAC8G3iA0Voa/view?usp=drivesdk)
- [39 cria modulo war do servidor.mp4](https://drive.google.com/file/d/1Q8SmK5t4ZrPsSAtvhzFjS_hzLIINvGMh/view?usp=drivesdk)
- [40-compor aplicacao e banco no war.mp4](https://drive.google.com/file/d/1eJ_R9v9uhur3PC0LMw_ufLXq-YPegYZP/view?usp=drivesdk)
- [41-autenticarrequisiçoes soap.mp4](https://drive.google.com/file/d/1Vu4j3VePdo6dJQTx36lcxj5IHqQKni43/view?usp=drivesdk)
- [42 Autorizar operações e converter falhas em SOAP Fault.mp4](https://drive.google.com/file/d/1T_XyIDdoDem_Rl_OLjvBjQuof86MLSTX/view?usp=drivesdk)
- [43-Mapear domínio para DTOs SOAP.mp4](https://drive.google.com/file/d/1XM2Jn0idiXIZJgF3v7mQSpxzdCnnopXb/view?usp=drivesdk)
- [44 Implementar MesaEndpoint.mp4](https://drive.google.com/file/d/15aC9BWDQTegBEodYlAT9HOQorM30m7QN/view?usp=drivesdk)
- [45  Implementar CategoriaEndpoint.mp4](https://drive.google.com/file/d/1ikf5R9bvy-WgomaJ0ANig3v2SK4iWOTQ/view?usp=drivesdk)
- [46 Implementar PedidoEndpoint.mp4](https://drive.google.com/file/d/11q5EAcnc3lAgcA-CbvctAp9OXZTUdXXx/view?usp=drivesdk)
- [47 Implementar PedidoEndpoint.mp4](https://drive.google.com/file/d/1mUsH22lGlfyvr57HzoaLOj5vM9_-q477/view?usp=drivesdk)
- [48 Implementar a geração da imagem QR.mp4](https://drive.google.com/file/d/1Hq2gmlptCgqOKsXK7-ET3n5KtSB19yXK/view?usp=drivesdk)
- [49 Criar o BFF HTTPJSON público.mp4](https://drive.google.com/file/d/1B0QjKZZDJFux_TphgKtq6wxrTxJWD_CY/view?usp=drivesdk)
- [50 Registrar filtros e serviços no Tomcat.mp4](https://drive.google.com/file/d/1xhbFi66uwCIhalCbviWXVahKStAPUu-8/view?usp=drivesdk)
- [51.0  Publicar o SOAP no Tomcat localhost.mp4](https://drive.google.com/file/d/1sNYDOcuPBWNts3s2K3ZYu33UhfHtydt6/view?usp=drivesdk)
- [51.1 Publicar o SOAP no Tomcat localhost.mp4](https://drive.google.com/file/d/1ca2NZFIVBK_yVctYJon1akshPjXIF-Bf/view?usp=drivesdk)
- [51.2 Publicar o SOAP no Tomcat localhost.mp4](https://drive.google.com/file/d/1H6g6PRcmCUxok93w_d-aYLPVXW7PPHca/view?usp=drivesdk)
- [52 Criar o módulo desktop e o cliente SOAP.mp4](https://drive.google.com/file/d/1O1mtn0Sfjs6B8KEHzg7UlqvQErWq_Ahu/view?usp=drivesdk)
- [53 Construir a janela com SwingWorker.mp4](https://drive.google.com/file/d/18dLkZWuAYpnFBg9AiLme3qLCTLO4LvdG/view?usp=drivesdk)
- [54 Construir o painel comum de cadastro.mp4](https://drive.google.com/file/d/12rtpjne1Ufn8Lf55UVXc4Hg1kasmr-wj/view?usp=drivesdk)
- [55 Construir o cadastro Swing de mesas.mp4](https://drive.google.com/file/d/15UhV75KOIsgcafNMfAQzUgMOcq5dKH6j/view?usp=drivesdk)
- [56 Construir o cadastro Swing de categorias.mp4](https://drive.google.com/file/d/1iyJZKIwni1VtgcEE14XHFZOKecItBsan/view?usp=drivesdk)
- [57 Construir o formulário Swing de produtos.mp4](https://drive.google.com/file/d/1EuZQADuRWd5vYdbdZekcTTGIz_D1hlQb/view?usp=drivesdk)
- [58 Montar a janela administrativa.mp4](https://drive.google.com/file/d/1TkIunz25W1uHxKKTGDAiIw2qtk4nDOvi/view?usp=drivesdk)
- [59.00 Construir a janela do garçom.mp4](https://drive.google.com/file/d/1QLk3uKrqY6lN_QZ1r2QzIX8ENseEUlBh/view?usp=drivesdk)
- [59.01 servidor de imagens.mp4](https://drive.google.com/file/d/1dBYB3Fm9EoPl3S1wgcGIqpV7s5xdDoyi/view?usp=drivesdk)
- [60 Testar as duas telas contra o SOAP local.mp4](https://drive.google.com/file/d/1xeG4h-rD0GRcp7aM0yfSzRLsGf8NFadx/view?usp=drivesdk)
- [61.mp4](https://drive.google.com/file/d/1ry5teUKSHZPBKJwH7ZxTSzTzWYYfHTig/view?usp=drivesdk)
- [62.mp4](https://drive.google.com/file/d/1pbqZoxdJsCknlydEnWJbo3FoCwR5YwDY/view?usp=drivesdk)
