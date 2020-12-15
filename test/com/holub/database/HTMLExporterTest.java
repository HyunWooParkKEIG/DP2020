package com.holub.database;

import com.holub.tools.ArrayIterator;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class HTMLExporterTest {
    private String[] columnNames = {"name", "address"};
    private String tableName = "test";
    private LinkedList rowSet = new LinkedList();
    private String[] testHtml = {
            "<!DOCTYPE html>",
            "<html>",
            "\t<head>",
            "\t\t<title>test</title>",
            "\t</head>",
            "\t<body>",
            "\t\t<h2>test</h2>",
            "\t\t<table>",
            "\t\t\t<tbody>",
            "\t\t\t\t<tr>",
            "\t\t\t\t\t<td>name</td>",
            "\t\t\t\t\t<td>address</td>",
            "\t\t\t\t</tr>",
            "\t\t\t\t<tr>",
            "\t\t\t\t\t<td>PHW</td>",
            "\t\t\t\t\t<td>HeukSeok</td>",
            "\t\t\t\t</tr>",
            "\t\t\t\t<tr>",
            "\t\t\t\t\t<td>MJH</td>",
            "\t\t\t\t\t<td>SangDo</td>",
            "\t\t\t\t</tr>",
            "\t\t\t\t<tr>",
            "\t\t\t\t\t<td>SJH</td>",
            "\t\t\t\t\t<td>BunDang</td>",
            "\t\t\t\t</tr>",
            "\t\t\t</tbody>",
            "\t\t</table>",
            "\t</body>",
            "</html>"
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
            Writer html = new FileWriter(new File( "c:/dp2020/HtmlTest.html"));
            HTMLExporter exporter = new HTMLExporter(html);
            exporter.startTable();
            exporter.storeMetadata(tableName, columnNames.length, rowSet.size(), new ArrayIterator(columnNames));

            for (Iterator i = rowSet.iterator(); i.hasNext();)
                exporter.storeRow(new ArrayIterator((Object[]) i.next()));

            exporter.endTable();
            html.close();

            FileReader rw = new FileReader("c:/dp2020/HtmlTest.html");
            BufferedReader br = new BufferedReader( rw );

            String readLine = null;
            int idx = 0;
//            for (int i = 0; i < 2; i++) {
//                assertEquals(br.readLine(), testHtml[i]);
//            }
            while ((readLine = br.readLine()) != null) {
                assertEquals(readLine, testHtml[idx]);
                idx++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}