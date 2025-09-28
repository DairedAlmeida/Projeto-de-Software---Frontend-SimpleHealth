# Sistema de CRUDs - SimpleHealth Módulo de Armazenamento

Este projeto implementa um sistema completo de CRUDs (Create, Read, Update, Delete) para o módulo de armazenamento do SimpleHealth, seguindo a arquitetura JavaFX com padrão MVC.

## Estrutura dos CRUDs

### 1. CRUD de Estoque
**Funcionalidades:**
- Criar, atualizar, deletar e listar estoques
- Cada estoque possui um local e uma coleção de itens
- Interface permite adicionar/remover itens do estoque

**Campos:**
- Local* (obrigatório)
- Lista de itens associados

### 2. CRUD de Item
**Funcionalidades:**
- Criar, atualizar, deletar e listar itens
- Suporte a 3 tipos específicos: Medicamento, Hospitalar e Alimento
- Campos específicos aparecem dinamicamente baseados no tipo selecionado

**Campos Comuns:**
- Nome* (obrigatório)
- Descrição
- Tipo* (obrigatório) - MEDICAMENTO, HOSPITALAR, ALIMENTO
- Unidade de Medida* (obrigatório)
- Quantidade Total
- Validade
- Lote

**Campos Específicos por Tipo:**

**Medicamento:**
- Prescrição
- Composição
- Bula
- Tarja (LIVRE, VERMELHA, PRETA)
- Modo de Consumo

**Hospitalar:**
- Descartável (checkbox)
- Uso (GERAL, CIRÚRGICO, CURATIVO)

**Alimento:**
- Alérgenos
- Tipo de Armazenamento (PERECÍVEL, NÃO PERECÍVEL, REFRIGERADO)

### 3. CRUD de Pedido
**Funcionalidades:**
- Criar, atualizar, deletar e listar pedidos
- Selecionar vários itens para um fornecedor
- Controle de status do pedido

**Campos:**
- Data do Pedido* (obrigatório)
- Status* (obrigatório) - PENDENTE, PROCESSANDO, ENVIADO, ENTREGUE, CANCELADO
- Nota Fiscal
- Fornecedor* (obrigatório)
- Lista de itens do pedido

### 4. CRUD de Fornecedor
**Funcionalidades:**
- Criar, atualizar, deletar e listar fornecedores
- Gerenciamento de dados de contato e endereço

**Campos:**
- Nome* (obrigatório)
- CNPJ* (obrigatório)
- Contato
- Endereço

## Fluxo de Operações

Todos os CRUDs seguem o mesmo padrão de operação:

### Operação "Novo"
1. Clique no botão "Novo"
2. Formulário é exibido com campos limpos
3. Preencha os campos obrigatórios (marcados com *)
4. Para campos específicos (Item), selecione o tipo primeiro
5. Clique em "Confirmar" para salvar ou "Cancelar" para descartar

### Operação "Atualizar"
1. Selecione um registro na tabela
2. Clique no botão "Atualizar"
3. Formulário é preenchido com os dados atuais
4. Modifique os campos desejados
5. Clique em "Confirmar" para salvar ou "Cancelar" para descartar

### Operação "Deletar"
1. Selecione um registro na tabela
2. Clique no botão "Deletar"
3. Confirme a exclusão na janela de diálogo
4. O registro será removido do sistema

### Funcionalidades Especiais

#### Gerenciamento de Itens em Estoque/Pedido
- Use o ComboBox "Adicionar Item" para selecionar itens disponíveis
- Clique em "Adicionar Item" para incluir na lista
- Selecione um item na lista e clique em "Remover Item" para excluir

#### Seleção Dinâmica de Tipos (Item)
- Ao selecionar um tipo no ComboBox, os campos específicos aparecem automaticamente
- Medicamento: campos relacionados a prescrição e farmacologia
- Hospitalar: campos para uso médico e descarte
- Alimento: campos para alérgenos e armazenamento

## Arquitetura do Sistema

### Camadas
1. **View (FXML)**: Interface gráfica JavaFX
2. **Controller**: Lógica de apresentação e controle
3. **Service**: Comunicação com APIs REST
4. **Model**: Entidades de domínio

### Padrões Utilizados
- **MVC (Model-View-Controller)**: Separação de responsabilidades
- **Abstract Factory**: `AbstractCrudController` para operações comuns
- **Observer**: Listeners para eventos da interface
- **ViewModel**: Classes de view para binding com JavaFX

### APIs Consumidas
O sistema consome APIs REST nos seguintes endpoints:
- `http://localhost:8080/api/estoques`
- `http://localhost:8080/api/itens`
- `http://localhost:8080/api/pedidos`
- `http://localhost:8080/api/fornecedores`

## Como Executar

### Via IDE (Desenvolvimento)
1. Execute a classe `CrudDemoApp.java`
2. A aplicação abrirá com abas para cada CRUD

### Via Scripts
- **Windows**: Execute `run.bat`
- **Linux/Mac**: Execute `run.sh`

### Compilação Manual
```bash
mvn clean compile exec:java -Dexec.mainClass="br.com.simplehealth.armazenamento.client.CrudDemoApp"
```

## Validações

### Campos Obrigatórios
- Estoque: Local
- Item: Nome, Tipo, Unidade de Medida
- Pedido: Data, Status, Fornecedor
- Fornecedor: Nome, CNPJ

### Validações de Formato
- Datas: Formato brasileiro (dd/MM/yyyy)
- CNPJ: Validação de formato
- Campos numéricos: Apenas números

## Tratamento de Erros

O sistema possui tratamento abrangente de erros:
- **Erro de Comunicação**: Problemas com API REST
- **Erro de Validação**: Campos obrigatórios não preenchidos
- **Erro de Formato**: Dados em formato inválido
- **Erro de Negócio**: Regras de domínio violadas

## Tecnologias Utilizadas

- **JavaFX 17**: Interface gráfica
- **Jackson**: Serialização JSON
- **Apache HTTP Client**: Comunicação REST
- **SLF4J + Logback**: Sistema de logs
- **Maven**: Gerenciamento de dependências

## Estrutura de Arquivos

```
src/main/java/br/com/simplehealth/armazenamento/
├── client/          # Aplicações principais
├── controller/      # Controladores JavaFX
├── model/          # Entidades de domínio
├── service/        # Serviços para API REST
└── view/           # ViewModels para JavaFX

src/main/resources/view/  # Arquivos FXML
├── estoque.fxml
├── item.fxml
├── pedido.fxml
└── fornecedor.fxml
```