# 🚗  API de Gerenciamento de Veículos

Este módulo fornece um controller RESTful para cadastro, consulta, atualização, remoção e filtragem de veículos. Ele faz parte de uma aplicação desenvolvida com **Spring Boot**, utilizando boas práticas de validação e mapeamento de dados via DTOs.

---

## 📌 Endpoints Disponíveis

### 🔹 `POST /carros`
Cadastra um novo veículo.

- **Requisição:** `VehicleRequest` (JSON)
- **Resposta:** `201 Created` com o veículo salvo (`VehicleResponse`)

### 🔹 `GET /carros/{id}`
Busca um veículo pelo ID.

- **Resposta:** `200 OK` com os dados ou `404 Not Found`

### 🔹 `PUT /carros/{id}`
Atualiza um veículo existente.

- **Requisição:** `VehicleRequest` (JSON)
- **Resposta:** `200 OK` com o veículo atualizado ou `404 Not Found`

### 🔹 `DELETE /carros/{id}`
Remove um veículo.

- **Resposta:** `204 No Content`

### 🔹 `GET /carros/potencia`
Retorna a lista dos veículos com **maior potência**.

- **Resposta:** `200 OK` com lista de `VehicleResponse`

### 🔹 `GET /carros/economia`
Retorna a lista dos veículos com **maior economia**.

- **Resposta:** `200 OK` com lista de `VehicleResponse`  
- O retorno formata automaticamente:
  - `km/l` para veículos a combustão  
  - `km/kWh` para veículos elétricos

### 🔹 `GET /carros/eletricos`
Retorna todos os veículos do tipo **elétrico**.

- **Resposta:** `200 OK` com lista de `VehicleResponse`

---

## 🧪 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Web
- Jakarta Bean Validation
- DTOs (Request e Response)
- UUID para identificação
- RESTful API

---

## 📦 Exemplo de JSON de Requisição

```json
{
  "marca": "Tesla",
  "modelo": "Model 3",
  "ano": 2024,
  "potencia": 283,
  "economia": 6.1,
  "tipo": "ELETRICO",
  "preco": 260000
}
