🏦 Simulador de Crédito

Este projeto é uma aplicação Spring Boot para simulação de crédito, testar e monitorar o desempenho de chamadas a serviços externos, registrando métricas como:

Tempo de resposta

Sucesso das chamadas

Porcentagem de sucesso

A aplicação roda de forma nativa ou em containers Docker via Docker Compose.

🚀 Funcionalidades

Simulação de Chamadas → Executa chamadas para serviços externos

Monitoramento de Desempenho → Coleta métricas detalhadas

Persistência de Dados → Armazena resultados no PostgreSQL local

Integração com Banco Externo → Conexão com SQL Server hospedado no Azure

Swagger UI → Documentação interativa da API disponível automaticamente

🛠 Tecnologias Utilizadas

Java 21

Spring Boot 3.5.4

Maven

JPA/Hibernate

PostgreSQL (banco local)

SQL Server (banco externo)

Azure Event Hubs (mensageria)

AspectJ (interceptação de chamadas)

Springdoc OpenAPI (Swagger UI)

Docker / Docker Compose

⚙️ Como Executar a Aplicação
✅ Pré-requisitos

Docker

Docker Compose

▶️ Executando com Docker

Clone o repositório:

git clone https://github.com/burgerpedro/Simulador.git
cd Simulador


Suba os containers com Docker Compose:

docker-compose up -d --build


Acesse a aplicação:

API: 👉 http://localhost:8080/simulador/v1

Swagger UI: 👉 http://localhost:8080/swagger-ui.html

📬 Collection Postman

Acesse a collection no Postman:
Simulador de Crédito - Collection

📂 Estrutura do Projeto
src/main/java/br/gov/caixa/Simulador                 → Código-fonte principal
src/main/java/br/gov/caixa/Simulador/config/local    → Configuração do banco local
src/main/java/br/gov/caixa/Simulador/config/external → Configuração do banco externo
src/main/java/br/gov/caixa/Simulador/model/local     → Entidades do banco local
src/main/java/br/gov/caixa/Simulador/model/external     → Entidades do banco externo
src/main/resources/application.properties            → Configuração da aplicação
