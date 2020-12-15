package com.holub.database;

import java.io.*;
import java.util.*;

public class HTMLExporter implements Table.Exporter
{	private final Writer out;
    private 	  int	 width;

    public HTMLExporter( Writer out )
    {	this.out = out;
    }

    public void storeMetadata( String tableName,
                               int width,
                               int height,
                               Iterator columnNames ) throws IOException
    {	this.width = width;
        out.write("<!DOCTYPE html>\n");
        out.write("<html>\n");
        out.write("\t<head>\n");
        out.write("\t\t<title>");
        out.write(tableName == null ? "anonymous" : tableName + "</title>\n");
        out.write("\t</head>\n");
        out.write("\t<body>\n");
        out.write("\t\t<h2>");
        out.write(tableName == null ? "anonymous" : tableName + "</h2>\n");
        out.write("\t\t<table>\n");
        out.write("\t\t\t<tbody>\n");

        storeRow( columnNames );


    }

    public void storeRow( Iterator data ) throws IOException
    {	int i = width;
        if(data.hasNext()){
            out.write("\t\t\t\t<tr>\n");
        }
        while( data.hasNext() ) {
            Object datum = data.next();
            if( datum != null ){
                out.write("\t\t\t\t\t<td>");
                out.write( datum.toString() );
            }

            if( --i > 0 ){
                out.write("</td>\n");
            }
        }
        out.write("</td>\n");
        out.write("\t\t\t\t</tr>\n");
    }

    public void startTable() throws IOException {/*nothing to do*/}
    public void endTable()   throws IOException {
        out.write("\t\t\t</tbody>\n");
        out.write("\t\t</table>\n");
        out.write("\t</body>\n");
        out.write("</html>\n");
    }
}