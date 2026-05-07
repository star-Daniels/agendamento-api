#  Cadastro API

API REST desenvolvida com Java + Spring Boot para gerenciamento de usuários, utilizando autenticação JWT, controle de acesso por roles e arquitetura em camadas.

---

#  Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT (JSON Web Token)
- BCrypt
- MySQL
- Maven

---

#  Funcionalidades

##  Autenticação
- Login com JWT
- Registro de usuários
- Senhas criptografadas com BCrypt

---

##  CRUD de Usuários
- Criar usuário
- Buscar usuário
- Atualizar usuário
- Deletar usuário

---

##  Segurança
- Rotas protegidas com JWT
- Controle de acesso por roles (`USER` e `ADMIN`)
- Verificação de usuário dono da conta
- Confirmação de email e senha para exclusão de conta

---

##  Arquitetura
O projeto foi estruturado utilizando:

- Controller Layer
- Service Layer
- Repository Layer
- DTOs para entrada e saída de dados

---

#  Estrutura do Projeto

```bash
src/main/java/com/daniel/cadastro
│
├── config
├── controllers
├── dtos
├── entities
├── enums
├── repositories
├── security
├── services
