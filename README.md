# VisuVarejo - Backend Analítico para Inteligência no Varejo 

VisuVarejo é um backend RESTful projetado para transformar dados brutos de vendas em inteligência estratégica para o varejo. O sistema centraliza métricas críticas como crescimento, risco, ticket médio, tendência e alertas operacionais em uma API estruturada e segura. Seu objetivo é apoiar a tomada de decisão orientada por dados por meio de uma arquitetura escalável, organizada e preparada para evolução.

---

## Tecnologias Utilizadas

### Backend
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-green)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data-JPA-blue)
![Maven](https://img.shields.io/badge/Maven-Build-red)

- Java 17  
- Spring Boot  
- Spring Security (JWT)  
- Spring Data JPA  
- Maven  

---

### Frontend
API projetada para consumo por aplicações Web (SPA), Mobile ou dashboards analíticos.

---

### Stacks

| Linguagens & Ferramentas | Função |
| ------------------------ | ------ |
| ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) | Linguagem principal e foco central do sistema |
| ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) | Framework Java que coordena endpoints, segurança, isolamento e afins (Boot, Security, Data JPA) |
| ![Maven](https://img.shields.io/badge/apachemaven-C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white) | Gerenciador de depêndencias, compilamento, ambiente e estrutura |
| ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white) | ORM orquestrador de dados em conjunto do banco |
| ![PostgreSQL](https://img.shields.io/badge/postgresql-4169e1?style=for-the-badge&logo=postgresql&logoColor=white) | Banco relacional local definindo a persistência de dados |
| ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens) | Tokenização para multi-tenant, segurança e autenticação |
| ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white) | Teste de endpoints e alinhamento destes |
---

## Diferenciais Técnicos

-  Autenticação e autorização via JWT com proteção granular de endpoints
-  Arquitetura Hexagonal Light (Ports & Adapters simplificado)
-  Separação clara entre domínio, aplicação e infraestrutura
-  Casos de uso modelados explicitamente na camada de aplicação
-  Uso de DTOs para isolamento entre domínio e transporte
-  Regras analíticas encapsuladas em use cases independentes
-  Camada de segurança desacoplada da lógica de negócio
-  Design orientado a expansão futura (multi-tenant e SaaS)

---

## Arquitetura

O projeto segue uma abordagem **Hexagonal Light**, priorizando isolamento do domínio e organização por casos de uso.

### Estrutura Conceitual:

- **Adapters de Entrada (Controllers)**  
  Exposição de endpoints REST.

- **Application Layer (Use Cases)**  
  Orquestração das regras analíticas.

- **Domain Layer**  
  Entidades, agregados e regras centrais de negócio.

- **Adapters de Saída (Repositories)**  
  Persistência e comunicação com banco de dados.

Essa organização permite:

- Alta testabilidade
- Independência de frameworks
- Facilidade de manutenção
- Evolução para microsserviços no futuro

---

## Casos de Uso (Use Cases) Implementados

A camada de aplicação concentra a inteligência analítica do sistema. Cada métrica é encapsulada em um caso de uso específico, garantindo responsabilidade única e reutilização.

---

### 1. Análise de Crescimento

Responsável por calcular a evolução percentual de vendas em determinado período.

**O que entrega:**
- Comparação entre períodos (ex: mês atual vs mês anterior)
- Percentual de crescimento ou retração
- Identificação de aceleração ou desaceleração de receita

**Valor estratégico:**
Permite identificar expansão, estagnação ou queda de desempenho comercial.

---

### 2. Cálculo de Ticket Médio

Determina o valor médio gasto por cliente em um período.

**Fórmula aplicada:**
```
Ticket Médio = Receita Total / Número de Vendas
```

**Aplicação prática:**
- Avaliar performance de campanhas
- Medir eficiência de upsell/cross-sell
- Comparar desempenho entre períodos

---

### 3. Análise de Risco

Avalia indicadores que podem representar risco operacional ou financeiro.

**Pode envolver:**
- Queda consistente de vendas
- Redução abrupta de ticket médio
- Oscilações fora de padrão histórico

**Objetivo:**
Gerar sinais antecipados de problemas comerciais antes que se tornem críticos.

---

### 4. Análise de Tendência

Identifica padrões de comportamento nos dados de vendas.

**Abordagem:**
- Avaliação de direção (alta, baixa ou estabilidade)
- Comparação sequencial de períodos
- Identificação de consistência no movimento

**Benefício:**
Auxilia no planejamento estratégico e previsões de curto prazo.

---

### 5. Sistema de Alertas

Camada responsável por consolidar regras analíticas e emitir notificações lógicas.

**Exemplos de alertas:**
- Queda de receita acima de X%
- Ticket médio abaixo do limite esperado
- Tendência negativa prolongada

**Importante:**
Os alertas são derivados das análicas anteriores, funcionando como agregadores de inteligência.

---

### 6. Endpoint `/dashboard`

Endpoint agregador que consolida múltiplos casos de uso em uma única resposta.

**Retorna:**
- Crescimento
- Ticket médio
- Indicadores de risco
- Tendência
- Alertas ativos

**Vantagem arquitetural:**
Centraliza chamadas analíticas, reduzindo múltiplas requisições no frontend.

---

## Instalação e Execução

### Pré-requisitos

- Java 17+
- Maven 3.9+

---

### 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/visuvarejo.git
cd visuvarejo
```

---

### 2. Compilar o projeto

```bash
./mvnw clean install
```
ou no Windows:
```bash
mvnw.cmd clean install
```

---

### 3. Executar a aplicação

```bash
./mvnw spring-boot:run
```
ou:
```bash
mvnw.cmd spring-boot:run
```

---

---

# API Reference — VisuVarejo Backend

Documentação oficial dos endpoints expostos pelo backend do **VisuVarejo**.

> Base URL:
```
http://localhost:8080
```

> Todos os endpoints (exceto autenticação) requerem JWT no header:
```
Authorization: Bearer SEU_TOKEN
```

---

##  Autenticação

### ➤ Registro

```
POST /auth/register
```

#### Body

```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "123456"
}
```

---

### ➤ Login

```
POST /auth/login
```

#### Body

```json
{
  "email": "joao@email.com",
  "password": "123456"
}
```

#### Response

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

##  Catálogo

### Categories

### ➤ Criar Categoria

```
POST /categories
```

#### Body

```json
{
  "name": "Bebidas"
}
```

#### Response

```json
{
  "id": "uuid",
  "name": "Bebidas"
}
```

---

### ➤ Listar Categorias

```
GET /categories
```

#### Response

```json
[
  {
    "id": "uuid",
    "name": "Bebidas"
  }
]
```

---

### ➤ Buscar Categoria por ID

```
GET /categories/{id}
```

---

## Products

### ➤ Criar Produto

```
POST /products
```

#### Body

```json
{
  "name": "Coca-Cola 2L",
  "price": 12.90,
  "categoryId": "uuid-da-categoria"
}
```

#### Response

```json
{
  "id": "uuid",
  "name": "Coca-Cola 2L",
  "price": 12.90,
  "categoryId": "uuid-da-categoria",
  "active": true
}
```

---

### ➤ Listar Produtos

```
GET /products
```

---

### ➤ Buscar Produto por ID

```
GET /products/{id}
```

---

### ➤ Atualizar Produto

```
PUT /products/{id}
```

#### Body

```json
{
  "name": "Coca-Cola 2L",
  "price": 13.50,
  "categoryId": "uuid-da-categoria",
  "active": true
}
```

---

### ➤ Listar Produtos por Categoria

```
GET /products/by-category/{categoryId}
```

---

##  Vendas

---

### ➤ Registrar Venda

```
POST /sales
```

#### Body

```json
{
  "items": [
    {
      "productId": "uuid-produto-1",
      "quantity": 2
    },
    {
      "productId": "uuid-produto-2",
      "quantity": 1
    }
  ]
}
```

#### Response

```json
{
  "id": "uuid-da-venda",
  "date": "2026-03-01T15:30:00",
  "totalAmount": 38.70,
  "items": [
    {
      "productId": "uuid-produto-1",
      "quantity": 2,
      "unitPrice": 12.90
    }
  ]
}
```

---

### ➤ Buscar Vendas por Período

```
GET /sales?start=2026-01-01T00:00:00&end=2026-01-31T23:59:59
```

---

##  Analytics

Todos os endpoints analíticos recebem datas no formato:

> YYYY-MM-DDTHH:MM:SS

---

### ➤ Receita Total por Período

```
GET /analytics/revenue?start=2026-01-01T00:00:00&end=2026-01-31T23:59:59
```

#### Response

```json
{
  "start": "2026-01-01T00:00:00",
  "end": "2026-01-31T23:59:59",
  "totalRevenue": 50000.00
}
```

---

### ➤ Ticket Médio

```
GET /analytics/ticket-average?start=...&end=...
```

#### Response

```json
{
  "start": "...",
  "end": "...",
  "averageTicket": 200.50
}
```

---

### ➤ Risco por Produto

```
GET /analytics/products/risk?start=...&end=...
```

#### Response

```json
[
  {
    "productId": "uuid",
    "productName": "Coca-Cola 2L",
    "slope": -0.45,
    "volatility": 0.32,
    "riskLevel": "HIGH"
  }
]
```

---

### ➤ Growth Score por Produto

```
GET /analytics/products/growth-score?start=...&end=...
```

#### Response

```json
[
  {
    "productId": "uuid",
    "growthScore": 82.5,
    "slope": 0.75,
    "averageRevenue": 12000.00,
    "volatility": 0.20
  }
]
```

---

### ➤ Overview Geral

```
GET /analytics/overview?start=...&end=...
```

#### Response

```json
{
  "start": "...",
  "end": "...",
  "totalRevenue": 50000.00,
  "totalItemsSold": 450,
  "salesCount": 250,
  "averageTicket": 200.00
}
```

---

### ➤ Comparação de Tendência

```
GET /analytics/trend-comparison?
previousStart=...&
previousEnd=...&
currentStart=...&
currentEnd=...
```

#### Response

```json
0.18
```

> (Valor percentual representando variação entre períodos)*

---

### ➤ Alertas

```
GET /analytics/alerts?
previousStart=...&
previousEnd=...&
currentStart=...&
currentEnd=...
```

#### Response

```json
[
  {
    "type": "REVENUE_DROP",
    "severity": "HIGH",
    "message": "Queda significativa detectada"
  }
]
```

---

### ➤ Dashboard Consolidado

```
GET /analytics/dashboard?
previousStart=...&
previousEnd=...&
currentStart=...&
currentEnd=...
```

#### Response

```json
{
  "overview": {
    "totalRevenue": 50000.00,
    "totalItemsSold": 450,
    "salesCount": 250,
    "averageTicket": 200.00
  },
  "trend": 0.18,
  "healthScore": 82,
  "concentration": 0.42,
  "alerts": []
}
```

---

# Observações Técnicas

- Datas devem ser enviadas no padrão ISO-8601.
- Todos os IDs são UUID.
- Todos os valores monetários utilizam precisão decimal.
- Arquitetura baseada em Use Cases (Application Layer).
- Estrutura preparada para evolução SaaS e multi-tenant.

---

## Segurança

- Autenticação baseada em JWT
- Rotas protegidas via Spring Security
- Camada de autorização separada da regra de negócio
- Preparado para futura implementação de controle multi-tenant

---

## Objetivo do Projeto

VisuVarejo demonstra a construção de um backend analítico robusto utilizando princípios modernos de engenharia de software. O projeto evidencia domínio em:

- Arquitetura desacoplada
- Modelagem orientada a casos de uso
- Segurança com JWT
- Organização voltada para escalabilidade
- Design preparado para evolução SaaS

Ideal para demonstrar competências técnicas em processos seletivos para posições de Backend Engineer, Software Engineer ou Desenvolvedor Java/Spring.

---

## Autor: Eduardo Silva Pinheiro Teodoro

Projeto desenvolvido como iniciativa de aprofundamento em arquitetura backend, segurança e modelagem analítica aplicada ao varejo.


