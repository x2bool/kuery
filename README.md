# Kuery - Strongly typed SQL in Kotlin

The library is a strongly typed alternative to plain text SQL. The main goal of this project is to make database-related code easier to develop and evolve. The project uses some of the Kotlin language features to achieve a certain level of type safety.

**WARNING: the library is at an early development stage. The APIs are unstable and might be changed in the future.**

## Foundation

Database structure is defined by classes/objects inherited from the **Table** class. Tables are **not** domain model classes. Their purpose is to simply define relationships between tables and columns.

```kotlin
import com.nivabit.kuery.*

object OrganizationTable : Table("organizations") {
	val id = Column("id")
	val name = Column("name")
}

object EmployeeTable : Table("employees") {
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
import com.nivabit.kuery.*
import com.nivabit.kuery.sqlite.*

val statement = from(EmployeeTable).where { e -> e.id eq 1 }.select { e -> e.name }
val sql = statement.toString(SQLiteDialect)
print(sql) // SELECT "name" FROM "employees" WHERE "id" = 1
```

## Data Definition Language

Many parts of data definition language are specific to SQL dialects. An example for SQLite might look like this:

### CREATE TABLE statement

```kotlin
import com.nivabit.kuery.*
import com.nivabit.kuery.sqlite.*

// CREATE TABLE "organizations" ...
over(OrganizationTable)
    .create { e ->
        e.id.integer().primaryKey(autoIncrement = true) +
        e.name.text().unique().notNull()
    }
    
// CREATE TABLE "employees" ...
over(EmployeeTable)
    .create { e ->
        e.id.integer().primaryKey(autoIncrement = true) +
        e.name.text().unique().notNull() +
        e.organizationId.integer().foreignKey(references = OrganizationTable.id)
    }
```

### DROP TABLE statement

```kotlin
// DROP TABLE "employees"
over(EmployeeTable).drop()
```

## Data Manipulation Language

Data manipulation is the most powerfull and complex part of SQL. The library supports **INSERT**, **SELECT**, **UPDATE** and **DELETE** statements.

### INSERT statement

```kotlin
// INSERT INTO "employees"("name", "organization_id") VALUES(?, ?)
into(EmployeeTable)
    .insert { e -> e.name("?") + e.organizationId("?") }
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
from(EmployeeTable)
    .where { e -> (e.organizationId ne null) and (e.name eq "''") }
    .orderBy { e -> e.name.asc + e.id.desc }
    .limit { 10 }
    .offset { 10 }
    .select { e -> e.id + e.name }
```

**JOINs** are also supported in select statements

```kotlin
// SELECT ... FROM "organizations" JOIN "employees" ON ...
from(OrganizationTable)
    .join(EmployeeTable).on { o, e -> o.id eq e.organizationId }
    .select { o, e -> o.name + e.name }
```

### UPDATE statement

```kotlin
// UPDATE "organizations" SET "name" = ? WHERE "organizations"."id" = 1
from(OrganizationTable)
    .where { o -> o.id eq 1 }
    .update { o -> o.name("?") }
```

### DELETE statement
```kotlin
// DELETE FROM "organizations" WHERE "organizations"."id" = 0
from(OrganizationTable)
    .where { o -> o.id eq 0 }
    .delete()
```
