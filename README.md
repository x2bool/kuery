# Kuery - typesafe SQL in Kotlin

The library offers an approach to work with a subset of SQL in Kotlin programming language. The main goal of this project is to make database-related code safer and easier to evolve. At the moment SQLite is the only supported dialect.

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

**Statement** class is the starting point for writing statements and queries. Resulting SQL can be obtained by terminating statement with either **.create()**, **.drop()**, **.insert()**, **.select()**, **.update()** or **.delete()** methods proceeded by **.toString()** call. An example of a **SELECT** statement might look like this:

```kotlin
val selectStatement = Statement.on(EmployeeTable).where { e -> e.id eq 1 }.select { e -> e.name }
```

There is also a shorter syntax for **INSERT**, **SELECT**, **UPDATE** and **DELETE** statements (see detailed documentation below):

```kotlin
into(EmployeeTable).insert { ... } // INSERT INTO ...
from(EmployeeTable).select { ... } // SELECT * FROM ...
from(EmployeeTable).update { ... } // UPDATE ...
from(EmployeeTable).delete { ... } // DELETE FROM ...
```

**Dialects** are responsible for converting statements into actual SQL:

```kotlin
import com.nivabit.kuery.sqlite.*

val sql = selectStatement.toString(SQLiteDialect)
print(sql) // SELECT "name" FROM "employees" WHERE "id" = 1
```

## Data Definition Language

Many parts of data definition language are specific to SQL dialects. An example for SQLite might look like this:

### CREATE TABLE statement

```kotlin
import com.nivabit.kuery.*
import com.nivabit.kuery.sqlite.*

// CREATE TABLE "organizations" ...
Statement.on(OrganizationTable)
    .create { e ->
        e.id.integer().primaryKey(autoIncrement = true) +
        e.name.text().unique().notNull()
    }
    
// CREATE TABLE "employees" ...
Statement.on(EmployeeTable)
    .create { e ->
        e.id.integer().primaryKey(autoIncrement = true) +
        e.name.text().unique().notNull() +
        e.organizationId.integer().foreignKey(references = OrganizationTable.id)
    }
```

### DROP TABLE statement

```kotlin
// DROP TABLE "employees"
Statement.on(EmployeeTable).drop()
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
