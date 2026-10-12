# Deploy  do BackEnd


## 1. Criar a rede Docker
```
docker network create -d bridge rede
```



## 2. Subir a instância do postgres
```
docker run -d --name postgres-aula -e POSTGRES_DB=auladb -e POSTGRES_USER=usuario -e POSTGRES_PASSWORD=senha --network=rede -p 5432:5432 postgres
```



### 3. Mude os parâmetros do GitHub Action

no arquivo /.github/workflows/deploy.yml existem 3 valores,
mude os nomes de usuário para o seu usuário do DockerHub

```
env:
  DOCKER_IMAGE: ezambomsantana/partida-ci
  CONTAINER_NAME: partida
  DOCKER_USER: ezambomsantana`
```


### 4. Crie os secrets

No seu fork do repositório, crie os segredos necessários, são
os mesmo que criamos no começo da disciplina.

```
DOCKERHUB_TOKEN
HOST_TEST
KEY_TEST
```


### 4. Mude as configs do Auth0

No arquivo `application.properties`, estão as configs do Auth0,
mude para apontar para a conta de vocês.