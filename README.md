Simulador de crédito
Este projeto é uma aplicação Spring Boot que atua como um simulador de crédito. Ele foi projetado para testar e monitorar o desempenho de chamadas a um serviço externo, registrando métricas como tempo de resposta, sucesso das chamadas e porcentagem de sucesso.

Funcionalidades
Simulação de Chamadas: Executa chamadas a um serviço externo para simular carga de trabalho.

Monitoramento de Desempenho: Coleta e armazena métricas de desempenho para análise.

Persistência de Dados: Salva os resultados das simulações em um banco de dados local.

Tecnologias Utilizadas
Spring Boot: Framework para desenvolvimento de aplicações Java.

Maven: Ferramenta de gerenciamento de dependências.

JPA/Hibernate: Para mapeamento e persistência de dados.

PostgreSQL: Banco de dados relacional para armazenamento dos resultados.

AspectJ: Para implementação de programação orientada a aspectos e interceptação de chamadas.

Como Executar a Aplicação
Pré-requisitos
JDK 17 ou superior

Maven

PostgreSQL

Acesso ao banco de dados externo (para a simulação)

Configuração do Banco de Dados
Crie um banco de dados local PostgreSQL e configure as credenciais no arquivo src/main/resources/application.properties. Certifique-se de que a sua configuração seja semelhante a esta:

spring.datasource.local.jdbc-url=jdbc:postgresql://localhost:5432/simulador
spring.datasource.local.username=postgres
spring.datasource.local.password=123
spring.datasource.local.driver-class-name=org.postgresql.Driver

Inicialização
Clone o repositório:
git clone https://github.com/seu-usuario/Simulador.git

Navegue até a pasta do projeto:
cd Simulador

Execute a aplicação:
./mvnw spring-boot:run

A aplicação estará acessível em http://localhost:8080.

Estrutura do Projeto
src/main/java/br/gov/caixa/Simulador: Código-fonte da aplicação.

src/main/java/br/gov/caixa.Simulador.config.local: Classes de configuração do banco de dados local.

src/main/java/br.gov.caixa.Simulador.config.external: Classes de configuração do banco de dados externo.

src/main/java/br.gov.caixa.Simulador.model.local: Classes de entidade para o banco de dados local.

src/main/resources/application.properties: Arquivo de configuração da aplicação.
