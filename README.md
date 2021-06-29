# Atlantico User-Api

Projeto responsável por prover funcionalidades para gerenciamento de usuários do sistema.

## Considerações

* Utilização de JWT gerenciado pelo próprio back-end
* Redis para sessão do usuaŕio que foi colocado a expiração em segundos igual a expiração do token.
* Redis para guardar cache dos usuários administradores para que outras aplicações possam utilizar
esta informação.
* Projeto lombok e mapStruct foram utilizados para conversão de entidade para DTO e vice-versa
* Para profile dev foram criados vários usuários e somente um administrador. No profile prod é criado somente
o usuário administrador.
  
## Pontos de melhoria

* Coloca a linha 56 do arquivo SecurityConfig.java somente para profile de dev. Deixei desta maneira somente para
  o projeto rodar com o database h2 dentro do docker. O correto é ter uma instância de banco, criar o database, as tabelas. 
  Não deixar o hibernate criar sozinho as tabelas em produção.
* Usar um servidor de SSO (Exemplo RedHat Keycloak) para ter um ponto central de autenticação e autorização. 
* Implementar a parte de testes da aplicação. (O mais importante de todos)

## Rodando o projeto

* O projeto pode ser rodado de maneira local usando o docker-compose através do arquivo
  [docker](https://github.com/holocaster/atlantico-user-api/blob/master/src/main/docker/docker-compose.yml)

