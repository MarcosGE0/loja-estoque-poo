# Loja de Informática e Administração de Estoque

Sistema de informação desenvolvido em Java para administrar usuários, produtos, serviços técnicos e pedidos de uma loja de informática.

O projeto foi desenvolvido no contexto da disciplina Programação Orientada a Objetos da Universidade Federal de Alagoas, Campus Arapiraca.

## Desenvolvedores

- Edson Ponciúncula
- Gabriel Nicolas
- Marcos Geovane

## Requisitos

- Java 21 ou superior
- Maven
- Git

## Execução

Clone o repositório:

```bash
git clone https://github.com/MarcosGE0/loja-estoque-poo.git
cd loja-estoque-poo
```

Compile o projeto:

```bash
mvn compile
```

Gere o pacote da aplicação:

```bash
mvn package
```

No terminal do Windows, compile e execute com os comandos abaixo:

```powershell
New-Item -ItemType Directory -Force target/classes
$sources = Get-ChildItem src/main/java -Recurse -Filter *.java |
    ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d target/classes $sources
java -cp target/classes br.ufal.aracomp.loja.Main
```

## Funcionalidades

### Usuários

- Cadastrar pessoa física
- Cadastrar pessoa jurídica
- Cadastrar administrador
- Listar usuários
- Buscar usuário por identificador
- Desativar usuário
- Remover usuário

### Produtos

- Cadastrar periférico
- Cadastrar peça de hardware
- Cadastrar computador
- Cadastrar software
- Listar produtos
- Buscar produto por identificador
- Desativar produto
- Remover produto
- Adicionar estoque

### Serviços técnicos

- Cadastrar serviço técnico
- Listar serviços
- Buscar serviço por identificador
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
- `dados/servicos_tecnicos.dat`
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

## Testes

Os testes automatizados utilizam JUnit.

Para executar os testes:

```bash
mvn test
```

## UML

A modelagem UML do projeto está disponível no seguinte link:

https://drive.google.com/file/d/1KFozWWmDvlg4820GcrwlxFFWujZolUz4/view?usp=drive_link

## Uso de IA

Ferramentas de inteligência artificial foram utilizadas como apoio ao estudo do projeto. A IA auxiliou na compreensão de conceitos, revisão de arquitetura, proposta de correções e organização da documentação.

As decisões finais, revisões do código, testes, implementação de funções, regras de negócio e validações foram realizadas pelos integrantes do grupo.