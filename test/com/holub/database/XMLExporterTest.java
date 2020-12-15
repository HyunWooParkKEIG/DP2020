package com.holub.database;

import com.holub.tools.ArrayIterator;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class XMLExporterTest {
    private String[] columnNames = {"name", "address"};
    private String tableName = "test";
    private LinkedList rowSet = new LinkedList();
    private String[] testXml = {
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>",
            "<table name=\"test\">",
            "\t<row>",
            "\t\t<name>PHW</name>",
            "\t\t<address>HeukSeok</address>",
            "\t</row>",
            "\t<row>",
            "\t\t<name>MJH</name>",
            "\t\t<address>SangDo</address>",
            "\t</row>",
            "\t<row>",
            "\t\t<name>SJH</name>",
            "\t\t<address>BunDang</address>",
            "\t</row>",
            "</table>"
    };

    @Test
    void test() {
        String[] row1 = {"PHW", "HeukSeok"};
        String[] row2 = {"MJH", "SangDo"};
        String[] row3 = {"SJH", "BunDang"};
        rowSet.add(row1);
        rowSet.add(row2);
        rowSet.add(row3);

        try {
            Writer xml = new FileWriter(new File( "c:/dp2020/XmlTest.xml"));
            XMLExporter exporter = new XMLExporter(xml);
            exporter.startTable();
            exporter.storeMetadata(tableName, columnNames.length, rowSet.size(), new ArrayIterator(columnNames));

            for (Iterator i = rowSet.iterator(); i.hasNext();)
                exporter.storeRow(new ArrayIterator((Object[]) i.next()));

            exporter.endTable();
            xml.close();

            FileReader rw = new FileReader("c:/dp2020/XmlTest.xml");
            BufferedReader br = new BufferedReader( rw );

            String readLine = null;
            int idx = 0;
//            for (int i = 0; i < 2; i++) {
//                assertEquals(br.readLine(), testHtml[i]);
//            }
            while ((readLine = br.readLine()) != null) {
                assertEquals(readLine, testXml[idx]);
                idx++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}