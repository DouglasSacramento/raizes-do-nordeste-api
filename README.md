### 1. Pré-requisitos
Certifique-se de ter instalado em sua máquina:
*   [Java JDK 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
*   [Maven](https://maven.apache.org/)
*   [Docker](https://www.docker.com/products/docker-desktop/) (Para rodar o banco de dados PostgreSQL isoladamente)

### 2. Clonando o Repositório

Abra o terminal e execute:
```bash
git clone https://github.com/DouglasSacramento/raizes-do-nordeste-api.git
cd raizes-do-nordeste-api
```

### 3. Configurando Variáveis de Ambiente

Este projeto utiliza variáveis de ambiente para proteger dados sensíveis e configurar a conexão com o banco de dados.
1. Na raiz do projeto, faça uma cópia do arquivo .env.example e renomeie para .env.
2. Abra o arquivo .env e defina uma chave segura para o token JWT:

JWT_SECRET=sua-chave-secreta-aqui

⚠️ Importante (Usuários de IDE): O arquivo .env só será lido automaticamente se você usar um plugin como 
o EnvFile (no IntelliJ) ou se exportar as variáveis no terminal antes de rodar a aplicação.


### 4. Subindo o Banco de Dados (PostgreSQL)
A aplicação utiliza o PostgreSQL como banco de dados principal. Com o Docker aberto na sua máquina, 
execute o comando abaixo na raiz do projeto para criar e rodar o contêiner do banco:
```bash
docker-compose up -d
```

### 5. Executando a aplicação
Após configurar o `.env` e subir o banco via Docker, execute o comando abaixo utilizando o Maven Wrapper que já vem incluso no projeto:

**No Windows:**
```bash
.\mvnw spring-boot:run
```
⚠️ Nota: Caso a aplicação apresente "Connection Refused", certifique-se de que o contêiner do Docker 
está rodando na porta 5432.

---

🌱 Migrations e Seeder (Fluxo Operacional)

Para facilitar e demonstrar o funcionamento real da operação multicanal,
o projeto conta com automação completa de infraestrutura e dados iniciais:

### 1. Flyway Migrations:
Ao iniciar a aplicação, o Flyway criará automaticamente todas as tabelas e relacionamentos
necessários no banco de dados PostgreSQL, seguindo os scripts da pasta db/migration.

### 2. Database Seeder e Perfis de Acesso (Roles):
Logo após a inicialização, a classe DatabaseSeeder irá popular automaticamente o banco de dados
com a estrutura mínima para testar as regras de negócio e a segregação de funções. 
O sistema simula a seguinte hierarquia:

* ADMIN (A Rede): Responsável por criar as unidades físicas (Filiais), 
definir o catálogo geral de produtos (Cardápio) e cadastrar os funcionários de alto nível (Gerentes).

* GERENTE (A Filial): Responsável por gerenciar a sua unidade específica, 
o que inclui o abastecimento rigoroso do estoque local para habilitar as vendas.

* CLIENTE: O consumidor final que realiza os pedidos consumindo os produtos disponibilizados.

Para facilitar os testes, o Seeder já cadastra unidades (Matriz e Centro), produtos (Baião e Tapioca), 
abastece o estoque inicial e cria contas prontas para uso.

**Credenciais de Acesso Geradas Automaticamente:**
Utilize os e-mails abaixo na rota /api/v1/auth/login para obter o Token JWT e testar os diferentes níveis de permissão da API:

* **Perfil Administrador (Acesso Total):**
    * **Login:** admin@email.com
    * **Senha:** 123456

* **Perfil Gerente (Ação Requerida - Teste Prático):**
    * *O Seeder intencionalmente não cria um gerente padrão.* Como primeiro passo prático, faça login com a conta de Administrador e utilize a rota de registro de funcionários (`POST /api/v1/funcionarios/registrar`) para criar o seu primeiro usuário com a role `GERENTE`. Com essa nova conta criada, faça login novamente para testar as funções exclusivas gerenciais, como o abastecimento de estoque da sua unidade!

* **Perfil Cliente (Criar Pedidos):**
    * **Login:** cliente@email.com
    * **Senha:** 123456

---

📖 Documentação e Testes

Você terá acesso às seguintes interfaces com a aplicação no ar:

### 1. Documentação Swagger (OpenAPI):
Para testar e visualizar os contratos da API, acesse: http://localhost:8080/swagger-ui/index.html

### 2. Postman Collection: 
Na raiz deste repositório, você encontrará o arquivo Raizes_do_Nordeste_Postman_Collection.json.
Importe este arquivo no seu Postman para ter acesso a todos os testes mapeados no plano de testes do projeto 
(Caminhos Felizes e Tratamentos de Erro).

*Desenvolvido por Douglas Jeronimo do Sacramento - RU: 4742114*