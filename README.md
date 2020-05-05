
# IBM Challenge
---
[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.72-blue.svg)](https://kotlinlang.org)
[![Android](https://img.shields.io/badge/Android-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

#### Estrutura do Projeto
O projeto foi criado pensando na disponibilização de features dinâmicas, utilizando o novo formato de upload do Google. (https://developer.android.com/guide/app-bundle)

Esse projeto de exemplo foi separado em três módulos: **app**, **feature_login**, **feature_statement**.
Todos os módulos seguem os padrões da Clean Architecture, proposta por *Robert C. Martin* em um post em seu [blog](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).

Cada módulo está dividido em três grande pacotes:
- **Presentation**: Camada de apresentação, responsável pela integração visual do Android;
- **Domain**: Camada de domínio, onde são declaradas as regras de negócio do projeto;
- **Data**: Camada de dados, responsável pela integração com APIs, escrita em disco e cache; 

---

###### Módulo: app
Ele é responsável por manter classes abstratas e interfaces comuns a todas as features do projeto.

###### Módulo: feature_login
Feature dinâmica de instalação em conjunto com o aplicativo.

###### Módulo: feature_statement
Feature dinâmica com instalação em conjunto com o aplicativo.

// TODO: Continuar explicação completa...