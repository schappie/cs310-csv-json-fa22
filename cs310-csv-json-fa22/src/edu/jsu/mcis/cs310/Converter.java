//(Project Version v2)
package edu.jsu.mcis.cs310;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
        
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings and which values should be encoded as integers, as
        well as the overall structure of the data!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
        
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
        
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and JSON.simple.  See the "Data
        Exchange" lecture notes for more details, including examples.
        
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            // Initialize CSV Reader and Iterator
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            /* INSERT YOUR CODE HERE */
            JSONArray ColHeaders = new JSONArray();
            JSONArray RowHeader = new JSONArray();
            JSONArray Data = new JSONArray();
            String[] dRows;
            
            ColHeaders.addAll(Arrays.asList(iterator.next()));
            while (iterator.hasNext()){
                JSONArray r = new JSONArray();
                dRows = iterator.next();
                RowHeader.add(dRows[0]);
                for (int i=1; i < dRows.length; i++){
                    r.add(dRows[0]);
                }
                Data.add(r);
                json.append("{\n    \"colHeaders\":").append(ColHeaders.toString());
            json.append(",\n    \"rowHeaders\":").append(RowHeader.toString()).append(",\n");
            dRows = Data.toString().split("],");
            json.append("    \"data\":");

            for (int i = 0; i < dRows.length; ++i){
                
                String a = dRows[i];        

                a = a.replace("\"","");    
                a = a.replace("]]","]");   
                
                json.append(a);  
                if ((i % dRows.length) != (dRows.length - 1))
                    json.append("],\n            ");
                
            }
            json.append("\n    ]\n}");
            
                
                
            }
            
        }
        catch(Exception e) { e.printStackTrace(); }
        
        // Return JSON String
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {
            
            // Initialize JSON Parser and CSV Writer
            
            JSONParser parser = new JSONParser();
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\\', "\n");
            
            /* INSERT YOUR CODE HERE */
            JSONObject jobject = (JSONObject) parser.parse(jsonString);
            JSONArray Col = (JSONArray) jobject.get("colHeaders");
            JSONArray Row = (JSONArray) jobject.get("rowHeaders");
            JSONArray Data = (JSONArray) jobject.get("data");
            String[] csvCol = new String[Col.size()];
            String[] csvRow = new String[Row.size()];
            String[] csvData = new String[Data.size()];
            String[] rowData;
            for (int i = 0; i < Col.size(); i++) {
                csvCol[i] = Col.get(i) + "";
            }
            for (int i = 0; i < Row.size(); i++) {
                
                csvRow[i] = Row.get(i) + "";
                csvData[i] = Data.get(i) + "";

            }
            csvWriter.writeNext(csvCol);
            for (int i = 0; i < csvData.length; i++){
                csvData[i] = csvData[i].replace("[","");
                csvData[i] = csvData[i].replace("]","");
                String[] ele = csvData[i].split(",");
                rowData = new String[ele.length + 1];
                rowData[0] = csvData[i];
                for(int d = 1; d < csvRow.length; d++){
					rowData[d] = csvRow[d];
            }
                csvWriter.writeNext(rowData);
            }
        }
        catch(Exception e) { e.printStackTrace(); }
        
        // Return CSV String
        
        return results.trim();
        
    }
	
}