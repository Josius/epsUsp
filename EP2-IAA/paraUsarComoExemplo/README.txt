Josimar Amaro de Sousa
11270715

Para compilar:
javac *.java
java -Xss250M Main entrada.txt saida.txt

Também estou enviando 6 testes e seus respectivos pngs. 
5 deles foram feitos com resolução de 512X512, com 512 variáveis de linhas de Koch e de RegionFill e o 6º eu fiz com 1280X720 e com 1280 variáveis de Koch e RegionFill, se há valores semelhantes que resultem em linhas de Koch ou RegionFill iguais entre si não sei dizer, porque joguei num for com todos os números variáveis e com esses testes e a sua dica de aumentar o stack, não houve estouro da pilha.

Adicionei dois métodos oriundos da classe Image, como não podia alterar esta eu usei o método limit com pequenas alterações para que não estourasse o percorrimento da matriz.
