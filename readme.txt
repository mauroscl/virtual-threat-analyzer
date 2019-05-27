No DockerFile instalei o bash para conseguir resolver a questão de obter o host e a porta do MySql
para garantir que esteja disponível quando iniciar a aplicação do Spring.
Precisei disso porque a conexão está com a string completa.
Apenas com o sh, que veio na imagem, não consegui resolver. Tentei uma solução mais elegante com
esta expressão regular

^jdbc:mysql:\/\/(\w+)\:(\d+)\/.+$

mas não funcionou no script.

A solução do problema principal das expressões regulares resolvi utilizando uma estratégia de cache,
para aumentar o throughput.
Fiz isso considerando que todas as inserções serão feitas pela aplicação.
Caso alguma inserção seja feito utilizando outra conexão com o banco, a cache não será invalidada e
podem aparecer resultados incorretos.
