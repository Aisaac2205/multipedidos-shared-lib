# MultiPedidos Common Library

Librería Maven compartida con lógica de negocio común para los microservicios de MultiPedidos S.A.

## Descripción

Esta librería contiene utilidades, validaciones, DTOs y excepciones reutilizables entre todos los microservicios del ecosistema MultiPedidos.

## Instalación

```bash
mvn clean install
```

La librería se instalará en el repositorio local de Maven: `~/.m2/repository/com/multipedidos/common-library/1.0.0/`

## Uso como Dependencia

Agregar en el `pom.xml` de tu microservicio:

```xml
<dependency>
    <groupId>com.multipedidos</groupId>
    <artifactId>common-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Componentes

### CalculadoraDescuentos
- Cálculo de IVA (15%)
- Descuentos escalonados (5%, 10%, 15%)
- Descuentos personalizados

### ValidadorCodigos
- Validación de emails
- Validación de códigos
- Generación de códigos (PED-XXXXXX, FAC-XXXXXX)

### Excepciones
- RecursoNoEncontradoException (404)
- DatosInvalidosException (400)

## Testing

```bash
mvn test
```

15 tests unitarios con JUnit 5

## Versión

1.0.0

## Licencia

Apache 2.0

