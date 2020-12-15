package com.holub.database;

import java.io.*;
import java.util.*;

public class XMLExporter implements Table.Exporter
{	private final Writer out;
    private 	  int	 width;
    private String tableName;
    private String[] columnNames;
    public XMLExporter( Writer out )
    {	this.out = out;
    }

    public void storeMetadata( String tableName,
                               int width,
                               int height,
                               Iterator columnNames ) throws IOException {

        this.width = width;
        this.tableName = tableName;
        this.columnNames = new String[width];

        for(int i = 0; columnNames.hasNext(); i++){
            this.columnNames[i] = columnNames.next().toString();
        }

        out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        out.write("<table name=\"" + this.tableName +"\">\n");

    }

    public void storeRow( Iterator data ) throws IOException {
        int i = width;

        out.write("\t<row>\n");
        while( data.hasNext() ) {
            Object datum = data.next();

            if( datum != null ){
                out.write("\t\t<" + this.columnNames[width - i] + ">");
                out.write( datum.toString() );
            }

            if( i > 0 ){
                out.write("</" + this.columnNames[width - i] + ">\n");
                i--;
            }

        }
        out.write("\t</row>\n");
    }


    public void startTable() throws IOException {/*nothing to do*/}
    public void endTable()   throws IOException {
        out.write("</table>\n");
    }
}