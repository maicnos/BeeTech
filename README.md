<p align="center">
  <img src="src/main/resources/icons/loginIcon2.png" width="300">
</p>

✅ BeeTech - JavaFX CRUD com MVC, DAO, Singleton e MySQL

Aplicação desktop desenvolvida em Java com JavaFX, utilizando os padrões MVC, DAO, Singleton e integração com banco de dados MySQL.

📋 Checklist de Desenvolvimento

🔧 Tecnologias
- [x] Java 17
- [x] JavaFX configurado
  - Interface
    - [ ] Design em CSS (opcional)
    - [x] Tela de login
    - [x] Tela do Administrador
    - [x] Agente de Negócios
        - [x] Gerenciamento de Apicultores
          - [x] View
          - [x] Controller
        - [x] Gerenciamento de Apiários
          - [x] View
          - [x] Controller
        - [x] Gerenciamento de Caixas
          - [x] View
          - [x] Controller
        - [x] Gerenciamento de Gastos
          - [x] View
          - [x] Controller
        - [x] Gerenciamento de Inventário
          - [x] View
          - [x] Controller
        - [x] Gerenciamento da Produção
          - [x] View
          - [x] Controller
        - [ ] Relatórios
          - [ ] View
          - [ ] Controller
- [x] MySQL
- [x] JDBC
- [x] Scene Builder

🧠 Padrões de Projeto
- [x] MVC (Model-View-Controller)
- [x] DAO (Data Access Object)
- [x] Singleton (para conexão com o banco)

📁 Estrutura do Projeto
- [x] model/ → Classes de domínio
- [x] resources/ → Interfaces gráficas (.fxml + .css + icons/)
- [x] controller/ → Lógica de controle
- [x] dao/ → Acesso ao banco de dados e ConnectioFactory com Singleton
- [x] util/ → Classes utilitárias (hash, autenticação)
- [x] test/ → Classes de testes (conexão, script .sql)

🗃️ Funcionalidades CRUD
- [x] Criar registros
- [x] Listar registros
- [x] Atualizar registros
- [x] Deletar registros

🛠️ Banco de Dados
- [x] Script SQL para criação das tabelas
- [x] Conexão via JDBC

🧪 Testes
- [x] Teste de conexão - ConnectionFactory
- [x] Teste de conexão - DAO
- [x] Teste de Hash e senha

📦 Como Executar
1. Clonar o repositório
2. Importar em IDE (IntelliJ, Eclipse, etc.)
3. Configurar conexão com MySQL e executar test
4. Executar a aplicação

📚 Documentação
- [x] README com checklist do trabalho
- [ ] Wiki com detalhes técnicos

---

## License

This project is licensed under the BSD 3-Clause License - see the [LICENSE](LICENSE) file for details.

