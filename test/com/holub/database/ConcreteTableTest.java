package com.holub.database;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteTableTest {

    @Test
    void select() {
        ArrayList<String> column1Names = new ArrayList<>();
        column1Names.add("name");
        column1Names.add("address");
        column1Names.add("id");

        String[] col1 = column1Names.toArray(new String[column1Names.size()]);
        Table table1 = new ConcreteTable("test1", col1);

        ArrayList<String> column2Names = new ArrayList<>();
        column2Names.add("age");
        column2Names.add("job");
        column2Names.add("id");

        String[] col2 = column2Names.toArray(new String[column2Names.size()]);
        Table table2 = new ConcreteTable("test2", col2);

        ArrayList<String> requestedColumns = new ArrayList<>();
        requestedColumns.add("*star");

        ArrayList<Table> otherTables = new ArrayList<>();
        otherTables.add(table2);

        Selector where = Selector.ALL;

        Table test = table1.select(where, requestedColumns, otherTables);
        Cursor cur = test.rows();

        String[] compare = {"name", "address", "id", "age", "job", "id"};
        assertEquals(6, cur.columnCount());

        for(int i = 0; i < cur.columnCount(); i++) {
            assertEquals(compare[i], cur.columnName(i));
        }
    }
}