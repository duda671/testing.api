# Multi-Tenancy Spring Boot Application

Este projeto é uma aplicação base para implementar **multi-tenancy** com Spring Boot. Ele oferece suporte para separar dados por `tenant`, utilizando uma arquitetura de schema por `tenant` para garantir segurança e organização dos dados.

## Visão Geral

Multi-tenancy (ou multitenância) permite que uma única aplicação atenda a múltiplos clientes (tenants) com dados isolados e configurações específicas. Neste projeto, cada `tenant` possui seu próprio schema no banco de dados, garantindo que os dados sejam armazenados separadamente.

### Tecnologias

- **Spring Boot**: Estrutura base da aplicação.
- **Hibernate**: ORM utilizado para gerenciar as entidades e suas operações no banco de dados.
- **PostgreSQL**: Banco de dados que suporta múltiplos schemas (pode ser adaptado para outros bancos de dados).
- **Flyway**: Gerenciamento de versões e migração do banco de dados.

## Configuração Inicial

### Pré-requisitos

- **Maven**
- **Banco de Dados**: PostgreSQL com suporte para múltiplos schemas.

### Configuração do Banco de Dados

Este projeto utiliza uma abordagem de schema por tenant. Para cada novo `tenant`, um schema será criado e migrado com as tabelas e dados iniciais.
