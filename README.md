# Mock API

App criado para fazer mock com REST utilizando JSON

## Regras

Quando uma request é feita é seguido o seguinte fluxo:

* Existe na pasta do mock (conforme a propriedade `api.fileBase`)? Caso sim, retorna o mock
* A uri se encaixa em algum pattern da lista de `api.uriConfigurations[].pattern`? Caso sim, vai redirecionar conforme a configuração e fazer fazer cache conforme o field `backup`
* Se não entrar nos fluxos anteriores, vai redirecionar para o host padrão `api.host`

## Requisitos
* Java JDK 8
* Maven 3

## Run

## Usando seu arquivo de propriedades
Crie seu arquivo de propriedade `src/main/resources/application-custom.yml` e rode com o argumento `-Dspring.profiles.active=custom`. Exemplo:
```
mvn spring-boot:run -Dspring.profiles.active=custom
```

## TODO
* -Mudar para Gradle
* Adicionar a opção de fazer build com Docker
* Separar testes unitários dos testes integrados
* Corrigir os testes ignorados
* Corrigir Code Style
* Adcionar plugin do FindBugs
* Revisar dependências (ver, por exemplo, se é mesmo necessário ter o GSON ou modelmapper)
* Usar objectmapper como component: `compile('com.fasterxml.jackson.datatype:jackson-datatype-jdk8')`
* Atualizar versão do Spring Boot
