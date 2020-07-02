# Cerebro 

Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar
contra los X-Men.

Te ha contratado a ti para que desarrolles un proyecto que detecte si un
humano es mutante basándose en su secuencia de ADN.

## Uso: 
	La Applicacion cuenta con 2 endpoint principalmente:

##POST:

```
/mutant/
```
Se envia un json para validar si el adn es mutante o no, en caso de serlo responde con un codigo 200 ok, en caso contrario retornara un error 403.

##Ejemplo
```
{
	"dna":["acgt","aatc","atag","aggg"]
}
```


##GET:

```
/Stats/
```
Una peticion get para obtene restadisticas de los mutantes evaluado

##Ejemplo
```
{
 “count_mutant_dna”:40,
 “count_human_dna”:100,
 “ratio”:0.4
}

```


## Entorno:

La aplicacion ha sido desplegada en [Heroku](https://cerebro-2020.herokuapp.com/) utilizando sus facilidades para integracion continua y conexion con Github.
Posee una base de datos PostgreSql pero es facilmente intercambiable a traves de conectores en el archivo POM.

## Configuracion: 
El proyecto cuenta con una tabla de configuraciones modificables por base de datos para aportar flexibilidad estos son 

>insert into config(code,description,value) values('VALID_LETTERS','Letras validas de cadenas de adn ','A,C,G,T');

Esta configuracion especifica las letras que se consideran validas para una cadena de ADN mutante.

>insert into config(code,description,value) values('VALIDATE_COUNT','Cantidad para dar como valido la cadena de adn ','4')

Esta configuracion especifica la cantidad de letras en secuencia para validar una cadena de ADN mutante.



##Postman:

Es posible obtener documentacion y ejemplo de llamadas utilizando el siguiente [Link](https://documenter.getpostman.com/view/10852748/T17FB94s)

	



