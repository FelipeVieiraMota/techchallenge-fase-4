
# 📌 Documentação da API do Projeto

Abaixo, você encontrará detalhes sobre como acessar e interagir com os serviços disponíveis.

---

## 🏗 Arquitetura do Projeto

Este projeto segue uma abordagem baseada em **microsserviços**, cada um com sua responsabilidade específica. A comunicação entre os serviços é feita via **Spring Cloud Gateway** e **RabbitMQ** para mensageria assíncrona.

### 🔹 Componentes Principais

- **API Gateway**: Responsável pelo roteamento das requisições para os serviços correspondentes.
- **Order Management Service**: Gerencia pedidos e transações.
- **Delivery Logistic Service**: Gerencia a logística e entrega dos pedidos.
- **Product Catalog Service**: Gerencia o catálogo de produtos.
- **Client Service**: Gerencia informações dos clientes.
- **RabbitMQ**: Utilizado para comunicação assíncrona entre os serviços, principalmente para a criação de pedidos (interação entre Order Management e Delivery Logistic).
- **PostgreSQL**: Banco de dados utilizado por cada microsserviço.

---

## 🚀 Configuração e Execução

### ✅ Requisitos

- **Docker** e **Docker Compose** instalados na máquina.

### ▶️ Subindo o Projeto

Para iniciar todos os serviços do projeto, execute o seguinte comando:

```sh
docker-compose up -d
```
### ⏹ Parando o Projeto
Caso precise interromper os serviços, utilize:

```sh
docker-compose down
docker rm -f order-management-app-container delivery-logistic-app-container product-catalog-app-container client-app-container
docker rmi order-management-app-image delivery-logistic-app-image product-catalog-app-image client-app-image
```

# Projeto de Microsserviços

Este projeto utiliza uma arquitetura de microsserviços com comunicação interna e gateways para roteamento.

## Serviços Disponíveis

As únicas portas expostas no ambiente local são:

| Serviço      | Porta Exposta   | Descrição                                                             |
|--------------|-----------------|----------------------------------------------------------------------|
| API Gateway  | 8081            | Roteia requisições para os microsserviços.                           |
| RabbitMQ     | 5672 / 15672    | Usado para mensageria entre serviços, especialmente para criar pedidos.|

**Observação:** Todos os outros serviços operam dentro da rede interna `microservice-network` e não expõem portas externas.

## 🔀 API Gateway

O **Spring Cloud Gateway** é utilizado para gerenciar o roteamento das requisições entre os diferentes microsserviços.

### Regras de Roteamento

| Serviço           | Endpoint Gateway       |
|-------------------|------------------------|
| Order Management  | `/api/orders/**`       |
| Delivery Logistic | `/api/deliveries/**`   |
| Product Catalog   | `/api/produtos/**`     |
| Client Service    | `/api/clientes/**`     |

### Documentação das APIs

Você pode acessar a documentação dos microsserviços via **Swagger UI** através do API Gateway:

- [Swagger UI - API Gateway](http://localhost:8081)

## Tecnologias Utilizadas

- Spring Boot
- Spring Cloud Gateway
- Spring Batch
- RabbitMQ (para comunicação entre Order Management e Delivery Logistic)
- Docker & Docker Compose
- PostgreSQL
- OpenAPI (Swagger UI)

## O Swagger não está funcionando corretamente, pois o Spring Cloud Gateway não está roteando corretamente as requisições.