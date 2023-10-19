# Inclusiwatcher

O **Inclusiwatcher** é uma plataforma que permite aos usuários registrar falhas de acessibilidade de forma anônima. O objetivo é promover uma experiência inclusiva e melhorar continuamente a acessibilidade dentro da Zup e StackSpot por meio do feedback e da colaboração dos usuários.

## Funcionalidades para Usuários:

### Tela de Cadastro de Falhas:

Os usuários podem cadastrar detalhes de falhas de acessibilidade por meio de um formulário fácil de usar. A submissão é anônima para garantir a privacidade do usuário.

![Tela de Cadastro de Falhas parte 1](https://github.com/tiofaso/squad5/assets/133882071/f43c5160-e48a-49b1-9d3a-c6d439932589)


## Funcionalidades para Administradores:

### Tela de Login de Administrador:

A autenticação é necessária para acessar o painel de administração. Garantir que apenas administradores autorizados tenham acesso às informações sensíveis.

![Tela de Login de Administrador](https://github.com/tiofaso/squad5/blob/feature/src/main/java/com/catalisa/squad5/front/img/telaLogin.png?raw=true)

### Painel de Administração:

O painel de administração permite que os administradores visualizem os detalhes de todas as falhas de acessibilidade cadastradas. Isso é essencial para gerenciar e abordar efetivamente as questões de acessibilidade.

![Painel de Administração parte 1](https://github.com/tiofaso/squad5/blob/feature-115-alteracao-readme-adc-img-final/src/main/java/com/catalisa/squad5/front/img/historicoParte1.png?raw=true)
![Painel de Administração parte 2](https://github.com/tiofaso/squad5/blob/feature-115-alteracao-readme-adc-img-final/src/main/java/com/catalisa/squad5/front/img/historicoParte2.png?raw=true)
![Painel de Administração parte 3](https://github.com/tiofaso/squad5/blob/feature-115-alteracao-readme-adc-img-final/src/main/java/com/catalisa/squad5/front/img/historicoParte3.png?raw=true)

### Kanban para Organizar as Falhas:

Dentro do painel de administração, os administradores podem organizar as falhas em diferentes estágios, como "To Do," "In Progress," e "Done." Isso ajuda na gestão eficaz das correções de acessibilidade.


![Kanban de Organização das Falhas](https://github.com/tiofaso/squad5/blob/feature-115-alteracao-readme-img/src/main/java/com/catalisa/squad5/front/img/telaPainelDeFalhas.png?raw=true)

## Tecnologias Utilizadas:

- **Backend:** Spring Boot, Spring Security, JWT.
  - Endpoints:
    - POST para registrar falha.
    - GET para listar todas as falhas.
    - GET por ID.
    - PUT para atualizar falha por ID.
    - PUT para atualizar falha por Task.
  - Testes Unitários no Controller e no Service: Implementamos testes unitários rigorosos para garantir que os controladores e serviços funcionem corretamente. Esses testes ajudam a identificar e resolver problemas antes de serem implantados em produção, garantindo um código mais seguro e confiável.

- **Frontend:** HTML, CSS5, JavaScript

## Equipe:

Faz parte do grupo:
- Fabio
- Juliana
- Thaís

## Contribuindo

Ficaríamos felizes em receber contribuições para melhorar o Inclusiwatcher. Sinta-se à vontade para enviar pull requests ou relatar problemas.

