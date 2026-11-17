# Arelú · Gestor de Repostería

Aplicación web para gestionar un obrador de repostería artesanal: pedidos, ventas, compras de materia prima y cálculo del coste de cada pastel.

## Para qué sirve

Los encargos llegan por WhatsApp y las ventas se apuntan en sitios distintos, así que es difícil saber cuánto se vende cada mes o si un pastel deja beneficio. Esta aplicación lo reúne todo en un solo lugar:

- **Pedidos:** todos los encargos de pasteles, con su fecha de entrega.
- **Ventas:** qué se vendió, cuándo y por cuánto.
- **Materia prima:** cada compra queda registrada para ver cómo evolucionan los precios.
- **Calculadora:** el coste real de un pastel, más los otros gastos y el margen, para fijar un precio sin perder dinero.
- **Usuarios:** dos roles, administrador y subadministrador (con menos permisos).

## Stack

| Capa | Tecnología |
|---|---|
| Backend | Java 25 · Spring Boot 4 · Spring Data JPA |
| Base de datos | PostgreSQL · Flyway (migraciones) |
| Infraestructura | Docker · Docker Compose |
| Frontend | Next.js |

## Cómo arrancarlo

### Requisitos

- Java 25
- Docker con Docker Compose

### Pasos

1. Crea tu `.env` a partir de la plantilla y rellena los valores:

   ```bash
   cp .env.example .env
   ```

   En Windows (PowerShell): `Copy-Item .env.example .env`

2. Levanta PostgreSQL:

   ```bash
   docker compose up -d
   docker compose ps    # espera a que salga "(healthy)"
   ```

3. Arranca el backend:

   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

   En Windows (PowerShell): `.\mvnw spring-boot:run`

4. La API queda disponible en `http://localhost:8080`.

### Parar

```bash
docker compose down
```

> ⚠️ `docker compose down -v` también borra el volumen, es decir, **todos los datos** de la base de datos.

## Estado

🚧 En desarrollo.

- [ ] Fase 1 · Setup (repo, Spring Boot, PostgreSQL en Docker)
- [ ] Fase 2 · Materia prima y compras
- [ ] Fase 3 · Usuarios y seguridad
- [ ] Fase 4 · Pedidos
- [ ] Fase 5 · Ventas
- [ ] Fase 6 · Calculadora de costes
- [ ] Fase 7 · Frontend
- [ ] Fase 8 · Despliegue

## Autor

Anyell Mendoza · [GitHub](https://github.com/Anismelow)