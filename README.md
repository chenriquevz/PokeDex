
# ![git-comment]
## Desafio Mobile - Quem é esse pokémon!

O objetivo desse projeto foi desenvolver uma Pokedex seguindo as orientações do desafio. Entre outros requisitos, o aplicativo funciona acessando informações da API pública disponível em pokeapi.co, além de listar os Pokémons em ordem númerica,
também é possível filtrar por tipo, conferir os stats, habilidades e cadeia de evolução de cada Pokémon. Os resultados são armazenados em cache automaticamente.

## Como utilizar

A forma mais fácil para rodar a aplicação, é clonar/download/forkar esse repositório e com Android Studio instalar o aplicativo em um emulador ou aparelho físico.

## Bibliotecas de destaque

-   **Jetpack Navigations**
-   **ViewModel**
-   **LiveData**
-   **Kotlin Coroutines**
-   **Room**
-   **Dagger2**
-   **Assisted inject**
-   **Retrofit2**
-   **paging library**
-   **ViewPager2**
-   **ViewBinding**
-   **Glide**



## O que ficou a desejar

-   Não foi possível adicionar testes com o tempo alocado.
-   Faltou shared elements transictions.
-   O design ficou usável mas acredito que pode melhorar muito.


## Observações

- Um "bug selvagem apareceu" ao final do desafio. Ao buscar um Pokémon, por nome ou número, usando emulador, e apertando a tecla **enter** para executar a busca,
a primeira imagem do Pokémon terá um leve fundo cinza. Só consegui reproduzir esse bug visual utilizando emulador e apertando a tecla **enter**. Infelizmente nem com repel ele saiu.

## Notas finais

Ficarei extremamente agradecido caso receba um feedback quanto a qualidade do código.
Muito obrigado pela oportunidade de participar do desafio.


