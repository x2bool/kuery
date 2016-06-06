# Kuery - safe SQL with Kotlin

The library offers an approach to work with a subset of SQL in Kotlin programming language. The main goal of this project is to make database-related code safer and easier to evolve. At the moment SQLite is the only supported dialect.

**WARNING: the library is at the early development stage. The APIs are unstable and might be changed in the future.**

## Data Definition Language

Database structure is defined by classes/objects inherited from the **Table** class. Tables are **not** domain model classes. Their purpose is to simply define relationships between tables and columns.

```kotlin
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

**Statement** class is the starting point for writing statements and queries. Resulting SQL can be obtained by terminating statement with either .create(), .drop(), .insert(), .select(), .update() or .delete() methods proceeded by .toString() call.

### CREATE TABLE statement

```kotlin
// CREATE TABLE "employees" ...
Statement.on(EmployeeTable)
		.create { e ->
			e.id.integer().primaryKey(autoIncrement = true).notNull() +
			e.name.text().unique().notNull() +
			e.organizationId.integer()
		}

// CREATE TABLE "organizations" ...
Statement.on(OrganizationTable)
		.create { e ->
			e.id.integer().primaryKey(autoIncrement = true).notNull() +
			e.name.text().unique().notNull()
		}
```

### DROP TABLE statement

```kotlin
// DROP TABLE "employees"
Statement.on(EmployeeTable).drop()
```

## Data Manipulation Language

Data manipulation is the most powerfull and complex part of SQL. The library supports insert, select, update and delete statements.

### INSERT statement

```kotlin
// INSERT INTO "organizations"("name", "organization_id") VALUES(?, ?)
Statement.on(EmployeeTable)
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
Statement.on(EmployeeTable)
		.where { e -> (e.organizationId ne null) and (e.name eq "''") }
		.orderBy { e -> e.name + e.id.desc }
		.limit { 10 }
		.offset { 10 }
		.select { e -> e.id + e.name }
```

**JOINs** are also supported in select statements

```kotlin
// SELECT ... FROM "organizations" JOIN "employees" ON ...
Statement.on(OrganizationTable)
		.join(EmployeeTable).on { o, e -> o.id eq e.organizationId }
		.select { o, e -> o.name + e.name }
```

### UPDATE statement

```kotlin
// UPDATE "organizations" SET "name" = ? WHERE "organizations"."id" = 1
Statement.on(OrganizationTable)
		.where { o -> o.id eq 1 }
		.update { o -> o.name("?") }
```

### DELETE statement
```kotlin
// DELETE FROM "organizations" WHERE "organizations"."id" = 0
Statement.on(OrganizationTable)
		.where { o -> o.id eq 0 }
		.delete()
```
