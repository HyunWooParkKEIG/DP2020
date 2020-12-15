/*  (c) 2004 Allen I. Holub. All rights reserved.
 *
 *  This code may be used freely by yourself with the following
 *  restrictions:
 *
 *  o Your splash screen, about box, or equivalent, must include
 *    Allen Holub's name, copyright, and URL. For example:
 *
 *      This program contains Allen Holub's SQL package.<br>
 *      (c) 2005 Allen I. Holub. All Rights Reserved.<br>
 *              http://www.holub.com<br>
 *
 *    If your program does not run interactively, then the foregoing
 *    notice must appear in your documentation.
 *
 *  o You may not redistribute (or mirror) the source code.
 *
 *  o You must report any bugs that you find to me. Use the form at
 *    http://www.holub.com/company/contact.html or send email to
 *    allen@Holub.com.
 *
 *  o The software is supplied <em>as is</em>. Neither Allen Holub nor
 *    Holub Associates are responsible for any bugs (or any problems
 *    caused by bugs, including lost productivity or data)
 *    in any of this code.
 */
package com.holub.database;

import com.holub.tools.ArrayIterator;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/***
 *	Pass this importer to a {@link Table} constructor (such
 *	as
 *	{link com.holub.database.ConcreteTable#ConcreteTable(Table.Importer)}
 *	to initialize
 *	a <code>Table</code> from
 *	a comma-sparated-value repressentation. For example:
 *	<PRE>
 *	Reader in = new FileReader( "people.csv" );
 *	people = new ConcreteTable( new CSVImporter(in) );
 *	in.close();
 *	</PRE>
 *	The input file for a table called "name" with
 *	columns "first," "last," and "addrId" would look
 *	like this:
 *	<PRE>
 *	name
 *	first,	last,	addrId
 *	Fred,	Flintstone,	1
 *	Wilma,	Flintstone,	1
 *	Allen,	Holub,	0
 *	</PRE>
 *	The first line is the table name, the second line
 *	identifies the columns, and the subsequent lines define
 *	the rows.
 *
 * @include /etc/license.txt
 *
 * @see Table
 * @see Table.Importer
 * @see CSVExporter
 */

public class XMLImporter implements Table.Importer {
    private BufferedReader  in;			// null once end-of-file reached
    private ArrayList<String> columnNames = new ArrayList<>();
    private String          tableName;
    private ArrayList<ArrayList<String>> list = new ArrayList<>();

    private int row_len = 0;
    private int row_idx = 0;
    public XMLImporter( Reader in )
    {	this.in = in instanceof BufferedReader
            ? (BufferedReader)in
            : new BufferedReader(in)
    ;
    }
    public void startTable()			throws IOException
    {
        System.out.println(in.readLine());
        String temp = in.readLine().substring(13);
        tableName = temp.substring(0, temp.length() - 2);
        System.out.println(tableName);
        ArrayList<String> row = new ArrayList<>();

        while (true) {
            String str = in.readLine();
            if (str.equals("</table>")) {
                break;
            }

            if (str.equals( "\t<row>" ) || str.equals("\t</row>")) {
                if (str.equals("\t<row>")) {
                    System.out.println("row");
                    row_len++;
                }

                if (str.equals("\t</row>")) {
                    System.out.println("/row");
                    list.add(row);
                    row = new ArrayList<>();
                }

                continue;
            } else {
                str = str.trim();
                temp = str.split(">")[0].substring(1);
                if (row_len == 1) {
                    columnNames.add(temp);
                }

                temp = str.substring(temp.length() + 2, str.length() - (temp.length() + 3));
                row.add(temp);
            }


        }
    }
    public String loadTableName()		throws IOException
    {	return tableName;
    }
    public int loadWidth()			    throws IOException
    {	return columnNames.toArray().length;
    }
    public Iterator loadColumnNames()	throws IOException
    {	return new ArrayIterator(columnNames.toArray());  //{=CSVImporter.ArrayIteratorCall}
    }

    public Iterator loadRow()			throws IOException
    {	Iterator row = null;
        if( row_idx != row_len )
        {
            row = new ArrayIterator( list.get(row_idx).toArray());
            row_idx++;
        }
        return row;
    }

    public void endTable() throws IOException {}
}
