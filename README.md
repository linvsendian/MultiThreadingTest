# MultiThreadingTest

## Problema
JpaRepository con Native Query no funciona con async

## Pruebas
Las pruebas pertinentes se encuentra en la clase UserRepositoryTest.
En la clase se ha definido 2 pruebas de búsqueda.Uno con Native Query y otro no.

Tras la ejecución se puede ver que la prueba de No Native Query funciona perfectamente con Async. Cada petición tarda alrededor de 1.8s en ejecutar y el tiempo total también cae a este rango.
En cambio el caso de lleva un coste de tiempo porporcionado con el número de pararelismo.
