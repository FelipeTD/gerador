# Implementações que poderiam ser feitas

- Utilização do campo DatGeracaoConjuntoDados para não executar novamente uma extração
- Utilização do Spring Batch para definir a execução do service extractData todo o dia 15 de cada mês
- Criação de testes unitários utilizando JUnit para cobertura de código
- Monitoramento da saúde da aplicação utilizando uma ferramenta de observabilidade
- Criação de uma lambda para execução da lógica de extração de dados
- Acionamento dessa (lambda) utilizando um EventBridge com cron para executar todo o dia 15 de cada mês

# Observações durante o desenvolvimento

- A primeira execução com salvamento de dados no banco demorou 4 minutos
  - Para melhorar o desempenho foi feito uma configuração no JPA
  - Essa configuração poderia ser melhorada com liberação de memória
  - Depois da configuração do JPA o saveAll foi para 29 segundos
- Percebi que vários dados salvos no banco de dados estavam duplicados
  - Fiz uma validação para não permitir dados duplicados
  - Essa validação foi dividida em duas partes
  - A primeira um SQL no banco de dados para não permitir dados duplicados
  - A segunda uma validação no código para não inserir dados que já existem no banco
  - Como o relatório é gerado 1 vez por mês, seria interessante:
    - Salvar o campo de data de geração do relatório
    - Utilizar esse campo para a validação de duplicidade
    - No exemplo eu utilizei os campos nome e power
    - O SQL de validação está no arquivo unique-data.sql
    - Não fiz a validação de dados já existentes no banco, mas seria interessante
- Nomes iguais com potências diferentes
  - Nesse caso não sei o que deveria ser feito
  - Se era para somar as potências
  - Se era para considerar somente a potência mais alta
  - Como não foi definido o que deveria fazer nesse caso não fiz nenhuma tratativa
- Troquei o valor de power de Double para BigDecimal 
  - A troca foi feita porque teve uma resposta com notação cientifica