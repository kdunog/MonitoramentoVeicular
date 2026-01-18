# 🚗 Monitoramento Veicular IoT

## 📌 Sobre o Projeto

O **Monitoramento Veicular IoT** é um projeto backend desenvolvido em **Java com Spring Boot**, com o objetivo de simular o monitoramento em tempo real de veículos, utilizando conceitos de **telemetria**, **alertas inteligentes**, **autenticação de usuários**

O sistema permite que usuários se cadastrem, façam login, cadastrem seus veículos e tenham dados de telemetria gerados automaticamente, como velocidade, consumo e nível de combustível.

---

## 🎯 Objetivo

Criar uma aplicação de backend, simulando um cenário real de monitoramento veicular, aplicando boas práticas de:
- Arquitetura REST
- Separação de responsabilidades
- Validações
- Tratamento de exceções

O projeto foi desenvolvido com foco em aprendizado e consolidação de conhecimentos para entrada no mercado como desenvolvedor.

---

## ⚙️ Funcionalidades

### 👤 Usuário
- Cadastro de usuário
- Login com validação
- Tratamento de erros e exceções
- Vinculação de veículos ao usuário logado

### 🚙 Veículo
- Cadastro de veículos
- Associação de veículos a usuários
- Consulta de veículos por usuário

### 📡 Telemetria
- Geração automática de telemetria a cada **10 segundos**
- Dados simulados:
  - Velocidade
  - Consumo
  - Combustível
- O combustível diminui conforme as telemetrias são geradas
- Quando chega a 0%, o combustível é reabastecido automaticamente para 100%

### 🚨 Alertas Inteligentes
- Alertas automáticos com **cooldown de 10 minutos**
- Tipos de alertas:
  - Velocidade acima de 120 km/h
  - Consumo elevado
  - Combustível abaixo de 15%
- Evita alertas duplicados dentro do período de cooldown

---

## 🧠 Regras de Negócio Implementadas

- Um usuário precisa estar autenticado para cadastrar veículos
- Veículos pertencem a um único usuário
- Alertas respeitam um intervalo mínimo para não gerar spam
- Telemetria não pertence ao veículo diretamente, mas é associada corretamente ao contexto do monitoramento

---

## 🛠️ Tecnologias Utilizadas

- **Java**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **Bean Validation**
- **MySQL / H2**
- **Postman (para testes da API)**

---

## 🧪 Testes

Atualmente o projeto é executado e testado via **Postman**, com todos os fluxos funcionando corretamente:
- Cadastro
- Login
- Cadastro de veículos
- Geração de telemetria
- Consulta de alertas

---

## 📌 Status do Projeto

✅ Backend finalizado  
⏳ Frontend em planejamento (mapa em tempo real será implementado futuramente)

---

## 🚀 Próximos Passos (Evolução)

- Implementar frontend com interface minimalista
- Exibição de mapa em tempo real
- Autenticação com JWT
- Dashboard de telemetria
- Histórico de alertas

---

## 👨‍💻 Autor

Projeto desenvolvido como estudo e portfólio pessoal, com foco em aprendizado e preparação para oportunidades de estágio na área de desenvolvimento de software.
