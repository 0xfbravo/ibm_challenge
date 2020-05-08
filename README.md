# IBM Challenge
---
[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.72-blue.svg)](https://kotlinlang.org)
[![Android](https://img.shields.io/badge/Android-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)

#### Inicial
O projeto foi criado pensando na disponibilização de features dinâmicas, utilizando o novo formato de upload do Google. (https://developer.android.com/guide/app-bundle)

Esse projeto de exemplo foi separado em três módulos: **app**, **feature_login**, **feature_statement**.
Todos os módulos seguem os padrões da Clean Architecture, proposta por *Robert C. Martin* em um post em seu [blog](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).

Cada módulo está dividido em três grande pacotes:
- **Presentation**: Camada de apresentação, responsável pela integração visual do Android;
- **Domain**: Camada de domínio, onde são declaradas as regras de negócio do projeto;
- **Data**: Camada de dados, responsável pela integração com APIs, escrita em disco e cache; 

---
#### Estrutura do Projeto
##### Informações básicas
- BuildTools: 29.0.2
- CompileSdk: 29
- MinSdk: 19
- TargetSdk: 29
- Gradle: 3.6.3

##### Dependências
- [Koin for Android](https://github.com/InsertKoinIO/koin) [2.1.5]
- [RxKotlin](https://github.com/ReactiveX/RxKotlin) [3.0.0]
- [RxAndroid](https://github.com/ReactiveX/RxAndroid) [3.0.0]
- [OkHttp](https://github.com/square/okhttp) [3.12.11]
- [Retrofit](https://github.com/square/retrofit) [2.6.0]
- [GSON](https://github.com/google/gson) [2.8.5]
- [JodaTime](https://github.com/JodaOrg/joda-time) [2.10]
- [InputMask](https://github.com/RedMadRobot/input-mask-android) [6.0.0]
- [Lottie](https://github.com/airbnb/lottie-android) [3.4.0]
- [PaperDB](https://github.com/pilgr/Paper)  [2.6]
---

#### Arquitetura
###### Módulo: app
Ele é responsável por manter classes abstratas, interfaces e implementações comuns a todas as features do projeto.
Assim como também é o detentor de modelos de apresentação que são utilizados em todas as features.

###### Módulo: feature_login
Feature dinâmica, de instalação em conjunto com o aplicativo, responsável pelas regras de negócio de **login** como: validação de CPF, validação de e-mail, validação de senha e comunicação com o endpoint de login.

###### Módulo: feature_statement
Feature dinâmica, de instalação em conjunto com o aplicativo, responsável pelas regras de negócio de **extrato/lançamentos/conta** como a comunicação com o endpoint de extrato.