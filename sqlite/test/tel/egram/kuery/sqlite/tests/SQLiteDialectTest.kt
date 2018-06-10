package tel.egram.kuery.sqlite.tests

import tel.egram.kuery.*
import tel.egram.kuery.sqlite.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class SQLiteDialectTest {
    private object TestTable : Table("TestTable") {
        val id = Column("id")
        val component1 = Column("component1")
        val component2 = Column("component2")
    }

    @Test
    fun `test create table`() {
        val expected = "CREATE TABLE \"TestTable\"(\"id\" INTEGER PRIMARY KEY AUTOINCREMENT, \"component1\" INTEGER NOT NULL, \"component2\" TEXT NOT NULL)"
        val result = over(TestTable)
                .create {
                    integer(TestTable.id).primaryKey(autoIncrement = true)..
                            integer(TestTable.component1).notNull()..
                            text(TestTable.component2).notNull()
                }.toString(SQLiteDialect)

        assertEquals(expected, result)
    }

    @Test
    fun `test where with Strings`() {
        val expected = "SELECT \"component1\", \"component2\" FROM \"TestTable\" WHERE ((\"component1\" = 1) AND (\"component2\" = 'TEST'))"
        val result = from(TestTable).where { (it.component1 eq 1) and (it.component2 eq "TEST") }.select { it.component1..it.component2 }.toString(SQLiteDialect)
        assertEquals(expected, result)
    }

    @Test
    fun `test where with Strings containing single and double quotes`() {
        val expectedSingle = "SELECT \"component1\", \"component2\" FROM \"TestTable\" WHERE ((\"component1\" = 1) AND (\"component2\" = 'T''EST'))"
        val resultSingle = from(TestTable).where { (it.component1 eq 1) and (it.component2 eq "T'EST") }.select { it.component1..it.component2 }.toString(SQLiteDialect)
        assertEquals(expectedSingle, resultSingle)

        val expectedDouble = "SELECT \"component1\", \"component2\" FROM \"TestTable\" WHERE ((\"component1\" = 1) AND (\"component2\" = 'T\"EST'))"
        val resultDouble = from(TestTable).where { (it.component1 eq 1) and (it.component2 eq "T\"EST") }.select { it.component1..it.component2 }.toString(SQLiteDialect)
        assertEquals(expectedDouble, resultDouble)
    }
}