package com.holub.database;

import com.holub.tools.ArrayIterator;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class XMLImporterTest {
    Reader in;
    XMLImporter importer;

    @Test
    void startTable(){
        try {
            in =  new FileReader( new File( "c:/dp2020", "XmlTest.xml" ));
            importer = new XMLImporter(in);

            importer.startTable();
            assertEquals(importer.loadTableName(), "test");

            ArrayList columnNames = new ArrayList();
            columnNames.add("name");
            columnNames.add("address");
            Iterator compareColumnNames = new ArrayIterator(columnNames.toArray());
            Iterator importerColumnNames = importer.loadColumnNames();
            while(importerColumnNames.hasNext()) {
                assertEquals(compareColumnNames.next(), importerColumnNames.next());
            }

            ArrayList<ArrayList<String>> list = new ArrayList<>();
            ArrayList<String> array1 = new ArrayList<>();
            array1.add("PHW");
            array1.add("HeukSeok");
            ArrayList<String> array2 = new ArrayList<>();
            array2.add("MJH");
            array2.add("SangDo");
            ArrayList<String> array3 = new ArrayList<>();
            array3.add("SJH");
            array3.add("BunDang");
            list.add(array1);
            list.add(array2);
            list.add(array3);

            System.out.println(importer.getList().get(0));
            assertEquals(list, importer.getList());

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void loadTableName() {
        try {
            in =  new FileReader( new File( "c:/dp2020", "XmlTest.xml" ));
            importer = new XMLImporter(in);

            importer.startTable();
            assertEquals(importer.loadTableName(), "test");
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void loadWidth(){
        try {
            in =  new FileReader( new File( "c:/dp2020", "XmlTest.xml" ));
            importer = new XMLImporter(in);

            importer.startTable();
            assertEquals(importer.loadWidth(), 2);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void loadColumnNames(){
        try {
            in =  new FileReader( new File( "c:/dp2020", "XmlTest.xml" ));
            importer = new XMLImporter(in);

            importer.startTable();
            ArrayList columnNames = new ArrayList();
            columnNames.add("name");
            columnNames.add("address");
            Iterator compareColumnNames = new ArrayIterator(columnNames.toArray());
            Iterator importerColumnNames = importer.loadColumnNames();
            while(importerColumnNames.hasNext()) {
                assertEquals(compareColumnNames.next(), importerColumnNames.next());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void loadRow() {
        try {
            in =  new FileReader( new File( "c:/dp2020", "XmlTest.xml" ));
            importer = new XMLImporter(in);

            importer.startTable();
            ArrayList<ArrayList<String>> list = new ArrayList<>();
            ArrayList<String> array1 = new ArrayList<>();
            array1.add("PHW");
            array1.add("HeukSeok");
            ArrayList<String> array2 = new ArrayList<>();
            array2.add("MJH");
            array2.add("SangDo");
            ArrayList<String> array3 = new ArrayList<>();
            array3.add("SJH");
            array3.add("BunDang");
            list.add(array1);
            list.add(array2);
            list.add(array3);

            for (int i = 0; i < importer.loadWidth(); i++) {
                Iterator compare = list.get(i).iterator();
                Iterator row = importer.loadRow();
                while (row.hasNext() && compare.hasNext()) {
                    assertEquals(row.next(), compare.next());
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void endTable() {}

}