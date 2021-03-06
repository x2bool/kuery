# Kuery - strongly typed SQL in Kotlin

The library is a strongly typed alternative to plain text SQL. The main goal of this project is to make database-related code easier to develop and maintain. The project uses some of the Kotlin language features to achieve type safety.

## Features

* SQL-like syntax. Use language constructions you already know. Designed to cover the most common SQL features.
* Strongly typed DSL makes it harder to make mistakes. Some of the most common errors are catched at compile time.
* IDE's assist in code editing.
* Easier and safer refactoring/renaming.
* No reflection

## Foundation

Database structure is defined by classes/objects inherited from the **Table** class. Tables are **not** domain model classes. Their purpose is to simply define relationships between tables and columns.

```kotlin
import tel.egram.kuery.*

object Organizations : Table("organizations") {
	val id = Column("id")
	val name = Column("name")
}

object Employees : Table("employees") {
	val id = Column("id")
	val name = Column("name")
	val organizationId = Column("organization_id")
}
```

**Statements** are the building blocks of the library. A statement usually starts with one of the following function calls:
* over(table) - used for **CREATE TABLE** and **DROP TABLE** statements
* into(table) - used for **INSERT** statements
* from(table) - used for **SELECT**, **UPDATE** and **DELETE** statements

**Dialects** are responsible for converting statements into actual SQL:

```kotlin
import tel.egram.kuery.*
import tel.egram.kuery.sqlite.*

val statement = from(Employees).where { e -> e.id eq 1 }.select { e -> e.name }
val sql = statement.toString(SQLiteDialect)
print(sql) // SELECT "name" FROM "employees" WHERE "id" = 1
```

## Data Definition Language

Some parts of data definition language are specific to SQL dialects. An example for SQLite might look like this:

### CREATE TABLE statement

```kotlin
import tel.egram.kuery.*
import tel.egram.kuery.sqlite.*

// CREATE TABLE "organizations" ...
over(Organizations)
    .create {
        integer(it.id).primaryKey(autoIncrement = true)..
        text(it.name).unique().notNull()
    }
    
// CREATE TABLE "employees" ...
over(Employees)
    .create {
        integer(it.id).primaryKey(autoIncrement = true)..
        text(it.name).unique().notNull()..
        integer(it.organizationId).foreignKey(references = Organizations.id)
    }
```

### DROP TABLE statement

```kotlin
// DROP TABLE "employees"
over(Employees).drop()
```

## Data Manipulation Language

Data manipulation is the most powerfull and complex part of SQL. The library supports **INSERT**, **SELECT**, **UPDATE** and **DELETE** statements.

### INSERT statement

```kotlin
// INSERT INTO "employees"("name", "organization_id") VALUES("John Doe", 1)
into(Employees)
    .insert { e -> e.name("John Doe") .. e.organizationId(1) }
```

### SELECT statement

The library provides the following operators to compose queries:
* and
* or
* not
* eq (equals)
* ne (not equals)
* lt (less than)
* lte (less than or equal to)
* gt (greater than)
* gte (greater than or equal to)

```kotlin
// SELECT "id", "name" FROM "organizations" WHERE ...
from(Employees)
    .where { e -> (e.organizationId ne null) and (e.name eq "John Doe") }
    .groupBy { e -> e.name }
    .having { e -> e.id ne null }
    .orderBy { e -> e.name.asc .. e.id.desc }
    .limit { 10 }
    .offset { 10 }
    .select { e -> e.id .. e.name }
```

**JOINs** are also supported in select statements

```kotlin
// SELECT ... FROM "organizations" JOIN "employees" ON ...
from(Organizations)
    .join(Employees).on { o, e -> o.id eq e.organizationId }
    .select { o, e -> o.name .. e.name }
```

### UPDATE statement

```kotlin
// UPDATE "organizations" SET "name" = 'John Doe' WHERE "id" = 1
from(Organizations)
    .where { o -> o.id eq 1 }
    .update { o -> o.name("John Doe") }
```

### DELETE statement
```kotlin
// DELETE FROM "organizations" WHERE "id" = 0
from(Organizations)
    .where { o -> o.id eq 0 }
    .delete()
```

## Download

Maven:

```xml
<!-- Core library -->
<dependency>
  <groupId>tel.egram.kuery</groupId>
  <artifactId>core</artifactId>
  <version>0.5.3</version>
  <type>pom</type>
</dependency>

<!-- SQLite dialect -->
<dependency>
  <groupId>tel.egram.kuery</groupId>
  <artifactId>sqlite</artifactId>
  <version>0.5.3</version>
  <type>pom</type>
</dependency>
```

Gradle:

```groovy
// Core library
compile 'tel.egram.kuery:core:0.5.3'
// SQLite dialect
compile 'tel.egram.kuery:sqlite:0.5.3'
```
