# Loja de Informática e Administração de Estoque

Sistema de informação desenvolvido em Java para administrar usuários, produtos, serviços técnicos e pedidos de uma loja de informática.

O projeto foi desenvolvido no contexto da disciplina Programação Orientada a Objetos da Universidade Federal de Alagoas, Campus Arapiraca.

## Desenvolvedores

- Edson Ponciúncula
- Gabriel Nicolas
- Marcos Geovane

## Requisitos

- Java 21 ou superior
- Git, caso queira clonar o projeto pelo terminal

## Como baixar e executar

Clone o repositório:

```bash
git clone https://github.com/MarcosGE0/loja-estoque-poo.git
cd loja-estoque-poo
```

Também é possível baixar o projeto como ZIP pelo GitHub, extrair os arquivos e abrir o terminal na pasta do projeto.

### Windows

No PowerShell, dentro da pasta do projeto:

```powershell
java -version
javac -version
New-Item -ItemType Directory -Force target/classes
$arquivos = Get-ChildItem src/main/java -Recurse -Filter *.java
javac -encoding UTF-8 -d target/classes $arquivos.FullName
java -cp target/classes br.ufal.aracomp.loja.Main
```

Os dois primeiros comandos verificam se o Java está instalado. Os demais compilam e iniciam o sistema.

### Linux ou macOS

No terminal, dentro da pasta do projeto:

```bash
java -version
javac -version
mkdir -p target/classes
find src/main/java -name "*.java" > sources.txt
javac -encoding UTF-8 -d target/classes @sources.txt
java -cp target/classes br.ufal.aracomp.loja.Main
rm sources.txt
```

## Funcionalidades

### Usuários

- Cadastrar pessoa física
- Cadastrar pessoa jurídica
- Cadastrar administrador
- Validar CPF e CNPJ e impedir documentos duplicados
- Listar usuários
- Buscar usuário por identificador
- Alterar e-mail e endereço de usuário
- Desativar usuário
- Remover usuário

### Produtos

- Cadastrar periférico
- Cadastrar peça de hardware
- Cadastrar computador
- Cadastrar software
- Listar produtos
- Buscar produto por identificador
- Alterar preço do produto
- Desativar produto
- Remover produto
- Adicionar estoque

### Serviços técnicos

- Cadastrar serviço técnico
- Listar serviços
- Buscar serviço por identificador
- Alterar preço por hora do serviço técnico
- Desativar serviço
- Remover serviço

### Pedidos

- Cadastrar pedido
- Listar pedidos
- Buscar pedido por identificador
- Adicionar produto
- Adicionar serviço técnico
- Remover quantidade de item
- Aplicar desconto promocional
- Aplicar desconto por quantidade
- Remover desconto
- Finalizar pedido
- Cancelar pedido
- Remover pedido
- Controlar estoque durante a finalização

## Organização do projeto

```text
docs/
|-- Loja de Informática - Diagrama de classes.svg
`-- Loja de Informática - Diagrama de classes.xml
src/
|-- main/java/br/ufal/aracomp/loja/
|   |-- controller/
|   |-- exception/
|   |-- models/
|   |-- repository/
|   |-- service/
|   |-- views/
|   `-- Main.java
`-- test/java/br/ufal/aracomp/loja/
```

## Arquitetura MVC

O sistema utiliza o padrão arquitetural MVC:

- View: apresenta os menus e recebe as informações digitadas pelo usuário.
- Controller: recebe as operações da interface e chama os services.
- Service: concentra as regras de negócio e as validações.
- Repository: realiza o acesso aos dados persistidos.
- Model: representa as entidades e os comportamentos do domínio.

A camada de view não acessa diretamente os repositories.

## Padrões de Projeto

### Repository

As classes de repository isolam o acesso aos dados persistidos em arquivos por serialização.

`ArquivoRepository` fornece os métodos genéricos de leitura e escrita. Os repositories específicos reutilizam essa estrutura para usuários, produtos, pedidos e serviços técnicos.

### Strategy

Os descontos são implementados pela interface `DescontoStrategy`, que permite diferentes formas de cálculo:

- `SemDesconto`
- `DescontoPromocional`
- `DescontoPorQuantidade`

O pedido trabalha com o tipo abstrato da estratégia e pode trocar o cálculo em tempo de execução.

## Persistência

Os dados são persistidos em arquivos locais dentro do diretório `dados`, usando serialização Java:

- `dados/usuarios.dat`
- `dados/produtos.dat`
- `dados/servicos.dat`
- `dados/pedidos.dat`

As operações de leitura e escrita ficam centralizadas nas classes repository.

## Exceções

O projeto utiliza uma hierarquia de exceções de domínio baseada em `DominioException`.

Exemplos:

- `DadoEntradaException`
- `RegistroCadastradoException`
- `RegistroNaoEncontradoException`
- `UsuarioInativoException`
- `ProdutoIndisponivelException`
- `EstoqueInsuficienteException`
- `ServicoIndisponivelException`
- `PersistenciaException`

## Testes

Os testes automatizados utilizam JUnit e cobrem regras centrais de pedidos, produtos, usuários e serviços técnicos, incluindo validações, descontos, estoque, estados de pedidos e persistência de alterações.

Execute a suíte com Maven:

```bash
mvn test
```

## UML

A modelagem UML do projeto está disponível em `docs/`:

- `Loja de Informática - Diagrama de classes.svg`: diagrama para visualização.
- `Loja de Informática - Diagrama de classes.xml`: arquivo-fonte editável do diagrama.

## Uso de IA

Ferramentas de inteligência artificial foram utilizadas como apoio ao estudo do projeto, incluindo a compreensão de conceitos, revisão de arquitetura, sugestões de correções e organização da documentação.

As decisões finais, revisões do código, testes, implementação de funções, classes, interfaces, associações entre classes, regras de negócio e validações foram realizadas pelos integrantes do grupo.