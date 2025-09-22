<h1 align="center">PassValidator API</h1>
<p align="center">Validação de senhas</p>
<p align="center">
 <a href="#sobre">Sobre o projeto</a> •
 <a href="#funcionalidades">Funcionalidades</a> •
 <a href="#pre-requisitos">Pré-requisitos</a> • 
 <a href="#execuacao">Rodando o projeto</a> • 
 <a href="#tecnologias">Tecnologias</a> • 
 <a href="#development-decisions">Decisões de Desenvolvimento</a> • 
 <a href="#autor">Autor</a>
</p>
<h4 align="center"> 
	PassValidator API 🚀 - Concluído
</h4>

## 💻 Sobre o projeto

O PassValidator API foi criado para realizar validação de senhas de acordo com regras de segurança pré-definidas. Projeto desenvolvido para um desafio técnico da empresa Sensedia.

## ⚙️ Validações

- [x] Ter **no mínimo 8 caracteres**.
- [x] Conter **pelo menos uma letra maiúscula** (`A-Z`).
- [x] Conter **pelo menos uma letra minúscula** (`a-z`).
- [x] Conter **pelo menos um número** (`0-9`).
- [x] Conter **pelo menos um caractere especial**, como `!@#$%^&*()-_+=`.
- [x] Não pode repetir as **últimas 5 senhas**.
- [x] Não pode ser uma senha comum (ex.: "123456", "password").
- [x] Não pode conter o nome de **usuário**.

## 🚀 Como executar o projeto

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
[Git](https://git-scm.com), Java 21, [Docker](https://www.docker.com/) e [Docker-Compose](https://docs.docker.com/compose/).

### Clone este repositório

```bash
$ git clone <https://github.com/GustavoNeery/PassValidator>
```

### Acesse o diretório do projeto no terminal

```bash
$ cd PassValidator
```

### Execute o comando abaixo para criar um arquivo .env na raiz do projeto

```bash
$ cp .env.example .env
```

### Execute o comando abaixo para buildar a aplicação e gerar o .jar

```bash
$ mvn clean package -DskipTests
```

### Execute o comando abaixo para criar a imagem da aplicação

Certifique-se de que você está dentro do diretório PassValidator

```bash
$ docker build -t img-validator .

# Para se certificar de que a imagem foi criada corretamente execute
$ docker images
```

### Execute o comando abaixo para criar o container da Aplicação e do Banco de Dados

```bash
$ docker compose up

# Para se certificar de que foram criados corretamente e estão rodando execute
$ docker ps

# Pronto a aplicação e o banco já estarão disponiveis para serem testados porta:8099 - http://localhost:8099/
```

## 🛠 Tecnologias

As seguintes ferramentas foram usadas na construção do projeto:

- [Spring Boot](https://spring.io/)
- [Java](https://www.java.com/pt-BR/)
- [MongoDB](https://www.mongodb.com/)

### Utilitários

- Editor: [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- Padrão de Commits: [Conventional Commits](https://conventionalcommits.org/en/v1.0.0/)
- Teste de API: [Insomnia](https://insomnia.rest/)

## 👨‍💻 Exemplo de requisição

  **[POST]** `/password-validations`

- Payload de exemplo:
  -   {
      "user": "joao",
      "password": "vYQIYxO&pyfI^r",
      "confirmPassword":"vYQIYxO&pyfI^r"
      }


