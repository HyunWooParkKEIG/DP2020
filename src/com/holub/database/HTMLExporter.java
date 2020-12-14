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
        out.write("\t<head>\n");
        out.write("\t\t<title>");
        out.write(tableName == null ? "anonymous" : tableName );
        out.write("</title>\n");
        out.write("\t</head>\n");
        out.write("\t<body>\n");
        out.write("\t\t<h2>\n");
        out.write(tableName == null ? "anonymous" : tableName + "</h2>");
        out.write("\t\t<table>\n");
        out.write("\t\t\t<tbody>\n");
        storeRow( columnNames ); // comma separated list of columns ids
    }

    public void storeRow( Iterator data ) throws IOException
    {	int i = width;
        if(data.hasNext()){
            out.write("\t\t\t\t<tr>");
        }
        while( data.hasNext() )
        {	Object datum = data.next();
            if( datum != null ){
                out.write("<td>");
                out.write( datum.toString() );
            }

            if( --i > 0 ){
                out.write("</td>");
            }
        }
        out.write("</tr>\n");
    }

    public void startTable() throws IOException {
        out.write("<!DOCTYPE html>\n");
        out.write("<html>\n");
    }
    public void endTable()   throws IOException {
        out.write("\t\t\t</tbody>\n");
        out.write("\t\t</table>\n");
        out.write("\t</body>\n");
        out.write("</html>\n");
    }
}