# TaskFlow

Aplicativo Android desenvolvido em **Kotlin** com **Jetpack Compose** para gerenciamento de tarefas.

O projeto foi criado com o objetivo de praticar desenvolvimento Android moderno, trabalhando com arquitetura, persistência local, consumo de APIs REST, navegação, gerenciamento de estado e testes.

## Funcionalidades

- Criar tarefas
- Listar tarefas cadastradas
- Marcar tarefas como concluídas
- Excluir tarefas
- Visualizar apenas tarefas concluídas
- Persistir tarefas localmente
- Navegação entre diferentes telas
- Consumir uma API REST externa
- Exibir estado de carregamento, sucesso e erro da API
- Testes instrumentados da camada de persistência
- Testes de interface com Jetpack Compose

## Tecnologias utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- ViewModel
- MVVM
- Navigation Compose
- Kotlin Coroutines
- Flow
- Room
- SQLite
- Repository Pattern
- Retrofit
- Gson
- REST API
- JSON
- JUnit
- Compose UI Testing
- Git

## Arquitetura

O projeto utiliza uma organização baseada em MVVM e separação de responsabilidades.

```text
Compose UI
    |
    v
ViewModel
    |
    v
Repository
   / \
  /   \
 v     v
Room  Retrofit
 |      |
 v      v
SQLite REST API
