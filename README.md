# Kuery - easy SQL with Kotlin

The library provides a way to work with a subset of SQL in Kotlin programming language. SQLite is the only supported dialect right now.

**WARNING: the library is in developement stage. The APIs are unstable and might be changed in the future.**

## Data Definition Language

The first step is to define the tables by inheriting from the **Table** class.

```
object EmployeeTable : Table("employees") {
	val id = Column("id")
	val name = Column("name")
	val organizationId = Column("organization_id")
}

object OrganizationTable : Table("organizations") {
	val id = Column("id")
	val name = Column("name")
}
```

**Statement** class is a starting point for writing any code. Resulting object can be turned into SQL by calling **.toString()** method. 

```
Statement.on(EmployeeTable).drop().toString() // DROP TABLE "organizations"
```

### CREATE TABLE statement

```
// CREATE TABLE "employees" ...
val createEmployeeTable = Statement.on(EmployeeTable)
		.create { e ->
			e.id.integer().primaryKey(autoIncrement = true).notNull() +
			e.name.text().unique().notNull() +
			e.organizationId.integer()
		}

// CREATE TABLE "organizations" ...
val createOrganizationTable = Statement.on(OrganizationTable)
		.create { e ->
			e.id.integer().primaryKey(autoIncrement = true).notNull() +
			e.name.text().unique().notNull()
		}
```

### DROP TABLE statement

```
// DROP TABLE "employees"
val dropStatement = Statement.on(EmployeeTable).drop()
```

## Data Manipulation Language

Data manipulation is the most powerfull and complex part of SQL. The library supports insert, select, update and delete statements.

### INSERT statement

```
// INSERT INTO "organizations"("name", "organization_id") VALUES('?', '?')
val insertStatement = Statement.on(EmployeeTable)
		.insert { e -> e.name("?") + e.organizationId("?") } // invoke syntax is used to set a value for the column
```

### SELECT statement

The library provides the following operators (infix functions):
* and
* or
* eq (equals)
* ne (not equals)

```
// SELECT "id", "name" FROM "organizations" WHERE ...
val selectStatement = Statement.on(EmployeeTable)
		.where { e -> (e.organizationId ne null) and (e.name eq "''") }
		.orderBy { e -> e.name + e.id.desc }
		.limit { 10 }
		.offset { 10 }
		.select { e -> e.id + e.name }
```

Joining tables is also supported

```
// SELECT ... FROM "organizations" JOIN "employees" ON ...
val joinStatement = Statement.on(OrganizationTable)
		.join(EmployeeTable).on { o, e -> o.id eq e.organizationId }
		.select { o, e -> o.name + e.name }
```

### UPDATE statement

```
// UPDATE "organizations" SET ... WHERE "id" = 1
val updateStatement = Statement.on(OrganizationTable)
		.where { o -> o.id eq 1 }
		.update { o -> o.name("?") }
```

### DELETE statement
```
// DELETE FROM "organizations" WHERE "id" = 0
val deleteStatement = Statement.on(OrganizationTable)
		.where { o -> o.id eq 0 }
		.delete()
```

## TODO
* GROUP BY and HAVING clauses
* JOIN on more than two tables
* Subqueries (research and prototyping)