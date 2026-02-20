# Sistema de Gestión de Operaciones Aeroportuarias

Sistema de gestión aeroportuaria en Java con Hibernate (MySQL), MongoDB Atlas, cifrado DES y firmas DSA.

## Stack
- **Java** · **Maven**
- **Hibernate** + **MySQL** (vuelos, aeropuertos, pasajeros, reservas, empleados)
- **MongoDB** (Atlas) para datos meteorológicos (`datos_clima`)
- **DES** para cifrar datos sensibles (pasajero)
- **DSA** para firmar reservas

## Configuración
- `src/main/resources/config/db.properties`: URL MySQL, usuario/contraseña y URI de MongoDB.
- Ejecutar `InstallApp` para generar claves DES y DSA antes del primer uso.

## Estructura
- `entryPoint/`: Main, InstallApp, CheckMongoConnection
- `controllers/`: Admin, Empleado, Flights, Reservation, Passengers, Weather
- `repositories/`: DAOs (Hibernate, MongoDB)
- `entities/`: JPA/Hibernate
- `utils/`: ValidationUtils, TablePrinter, LanguageUtils, SignatureUtil, DesUtil, etc.
- `config/`: HibernateUtils, MongoDbUtil

## Funcionalidades
- **Login** con selección de idioma (ES/EN)
- **Panel Admin**: vuelos, reservas, pasajeros, empleados, clima, verificación DSA, cifrado/descifrado DES
- **Panel Empleado**: vuelos, reservas, pasajeros, clima (lectura/consulta)
- **Reservas**: crear, buscar, listar, calcular precio total por pasajero, verificar firma DSA
- **Clima (MongoDB)**: insertar parte meteorológico (validación de rangos), consultar por aeropuerto, rango de fechas, niebla/tormenta
- **i18n** mediante `LanguageUtils`
- Tablas en consola con `TablePrinter`

## Ejecución
mvn compile
mvn exec:java -Dexec.mainClass="entryPoint.Main"
