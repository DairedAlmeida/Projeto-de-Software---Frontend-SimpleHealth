# SimpleHealth - Módulo de Armazenamento (Frontend)

Este é o frontend JavaFX para o módulo de armazenamento do sistema SimpleHealth. O projeto consome APIs REST desenvolvidas em Spring Boot para gerenciar itens de estoque, fornecedores, pedidos e estoques.

## Estrutura do Projeto

O projeto segue o padrão MVC (Model-View-Controller) e está organizado da seguinte forma:

```
src/main/java/br/com/simplehealth/armazenamento/
├── client/           # Classe principal da aplicação
├── controller/       # Controladores JavaFX
├── model/           # Modelos de dados (DTOs)
├── service/         # Serviços para consumir APIs
└── view/            # ViewModels para JavaFX

src/main/resources/
├── view/            # Arquivos FXML das interfaces
└── logback.xml      # Configuração de logging
```

## Funcionalidades

### Gerenciamento de Itens
- CRUD completo para itens de estoque
- Suporte a diferentes tipos de itens:
  - **Medicamentos**: prescrição, composição, bula, tarja, modo de consumo
  - **Itens Hospitalares**: descartável, uso (geral, cirúrgico, curativo)
  - **Alimentos**: alérgenos, tipo de armazenamento
- Validação de campos obrigatórios
- Interface adaptável baseada no tipo de item

### Gerenciamento de Fornecedores
- CRUD completo para fornecedores
- Busca por nome
- Campos: nome, CNPJ, contato, endereço

### Arquitetura

#### Modelos
- **Item**: Classe base para todos os itens
- **Medicamento**, **Hospitalar**, **Alimento**: Especializações de Item
- **Fornecedor**: Dados dos fornecedores
- **Pedido**: Pedidos de compra
- **Estoque**: Locais de armazenamento

#### Serviços
- **ItemService**: Consumo de APIs de itens
- **FornecedorService**: Consumo de APIs de fornecedores
- **PedidoService**: Consumo de APIs de pedidos
- **EstoqueService**: Consumo de APIs de estoque

#### Controladores
- **AbstractCrudController**: Controlador base com funcionalidades CRUD
- **ItemController**: Controlador específico para itens
- **FornecedorController**: Controlador específico para fornecedores

## Tecnologias Utilizadas

- **JavaFX 17**: Interface gráfica
- **Jackson**: Serialização/deserialização JSON
- **Apache HttpClient 5**: Cliente HTTP para consumo de APIs
- **SLF4J + Logback**: Logging
- **Maven**: Gerenciamento de dependências

## Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior
- Backend Spring Boot rodando na porta 8080

## Como Executar

1. **Clone o projeto**:
   ```bash
   git clone <url-do-repositorio>
   cd SimpleHealth_Frontend/moduloArmazenamento
   ```

2. **Compile o projeto**:
   ```bash
   mvn clean compile
   ```

3. **Execute a aplicação**:
   ```bash
   mvn javafx:run
   ```

   Ou usando o plugin Maven:
   ```bash
   mvn clean javafx:run
   ```

## Configuração da API

Por padrão, a aplicação espera que as APIs estejam rodando em `http://localhost:8080`. 

As seguintes rotas são utilizadas:
- `/api/itens` - Gerenciamento de itens
- `/api/fornecedores` - Gerenciamento de fornecedores
- `/api/pedidos` - Gerenciamento de pedidos
- `/api/estoques` - Gerenciamento de estoques

## Interface do Usuário

### Tela de Itens
- **Tabela**: Lista todos os itens com informações básicas
- **Formulário**: Campos para criar/editar itens
- **Campos Dinâmicos**: Mostrados baseados no tipo de item selecionado
- **Botões**: Novo, Atualizar, Deletar, Confirmar, Cancelar

### Tela de Fornecedores
- **Tabela**: Lista todos os fornecedores
- **Formulário**: Campos para dados do fornecedor
- **Busca**: Funcionalidade de busca por nome
- **Botões**: CRUD padrão + busca e filtros

## Logging

Os logs são salvos em:
- Console: Para desenvolvimento
- Arquivo: `logs/simplehealth-frontend.log`

Configuração em `src/main/resources/logback.xml`

## Estrutura de Classes

### Herança de Itens
```
Item (classe base)
├── Medicamento
├── Hospitalar
└── Alimento
```

### Padrão CRUD
Todos os controladores herdam de `AbstractCrudController` que fornece:
- Operações CRUD via API
- Validação de campos
- Gerenciamento de estado da interface
- Notificações entre telas

## Desenvolvimento

### Adicionando Novos Módulos

1. **Criar o Modelo**: Adicionar em `model/`
2. **Criar o ViewModel**: Adicionar em `view/`
3. **Criar o Serviço**: Adicionar em `service/`
4. **Criar o Controlador**: Estender `AbstractCrudController`
5. **Criar o FXML**: Adicionar em `resources/view/`
6. **Adicionar Tab**: Modificar `MainApp`

### Padrões de Código

- Usar SLF4J para logging
- Validar entrada do usuário
- Tratar exceções de I/O
- Seguir convenções JavaFX para binding de propriedades
- Usar Jackson annotations para serialização JSON

## Troubleshooting

### Problemas Comuns

1. **Erro de conexão com API**:
   - Verificar se backend está rodando
   - Verificar URL base nos serviços

2. **Campos não aparecem**:
   - Verificar IDs no FXML
   - Verificar binding no controlador

3. **Erro de serialização JSON**:
   - Verificar annotations Jackson
   - Verificar formato de data/hora

## Contribuição

1. Seguir padrões de código existentes
2. Adicionar testes para novas funcionalidades
3. Documentar mudanças no README
4. Usar mensagens de commit descritivas

## Licença

Este projeto é parte do trabalho acadêmico para o curso de Ciência da Computação.