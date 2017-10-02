[![Build Status](https://travis-ci.org/concretesolutions/mock-api.svg?branch=master)](https://travis-ci.org/concretesolutions/mock-api)
[![codecov.io](https://codecov.io/github/concretesolutions/mock-api/coverage.svg?branch=master)](https://codecov.io/github/concretesolutions/mock-api?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/67cdddf44d87495c84e3bddfdb5de074)](https://www.codacy.com/app/concrete/mock-api?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=concretesolutions/mock-api&amp;utm_campaign=Badge_Grade)

## Coverage History
![codecov.io](https://codecov.io/github/concretesolutions/mock-api/branch.svg?branch=master)

# Mock API

App criado para fazer mock com REST utilizando JSON

## Regras

Quando uma request é feita é seguido o seguinte fluxo:

* Existe na pasta do mock (conforme a propriedade `api.fileBase`)? Caso sim, retorna o mock
* A uri se encaixa em algum pattern da lista de `api.uriConfigurations[].pattern`? Caso sim, vai redirecionar conforme a configuração e fazer fazer cache conforme o field `backup`
* Se não entrar nos fluxos anteriores, vai redirecionar para o host padrão `api.host`

## Requisitos
* Java JDK 8
* Gradle 4

## Run

## Usando seu arquivo de propriedades
Crie seu arquivo de propriedade `src/main/resources/application-custom.yml` e rode com o argumento `-Dspring.profiles.active=custom`. Exemplo:
```
gradle bootRun -Dspring.profiles.active=custom
```

## Usando imagem Docker
Para gerar a imagem Docker do projeto, execute: 
```
gradle buildDocker
```

Por padrão, o nome da imagem será `concretesolutions/mock-api:VERSAO`.

Para rodar a aplicação, crie dois diretórios: um contendo o arquivo de configuração `application-custom.yml` e o outro contendo os arquivos de mock. Execute então:

```
docker run -d --name mock-api \
       -p 9090:9090 \
       -v /path/para/arquivo/application-custom.yml:/config/application.yml \
       -v /path/para/diretorio/dados/:/data \
       concretesolutions/mock-api:VERSAO
```

A porta `9090` expõe o serviço enquanto a porta `5000` é utilizada para debug da aplicação.

Para visualizar os logs da aplicação a partir do container: `docker logs -f mock-api`

## TODO
* Inseriri exemplo do "arquivo de propriedades" no README
* Separar testes unitários dos testes integrados
* Corrigir os testes ignorados
* Corrigir Code Style
* Adicionar plugin do FindBugs
* Revisar dependências (ver, por exemplo, se é mesmo necessário ter o GSON ou modelmapper)
* Usar objectmapper como component: `compile('com.fasterxml.jackson.datatype:jackson-datatype-jdk8')`
