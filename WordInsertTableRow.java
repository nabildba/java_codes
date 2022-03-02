import java.io.*;
import org.apache.poi.xwpf.usermodel.*;

import  org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

public class WordInsertTableRow {

 public static void main(String[] args) throws Exception {

  XWPFDocument doc = new XWPFDocument(new FileInputStream("source.docx"));

  XWPFTable table = doc.getTableArray(0);

//insert new row, which is a copy of row 2, as new row 3:
  XWPFTableRow oldRow = table.getRow(1);
  CTRow ctrow = CTRow.Factory.parse(oldRow.getCtRow().newInputStream());
  XWPFTableRow newRow = new XWPFTableRow(ctrow, table);

  int i = 1;
  for (XWPFTableCell cell : newRow.getTableCells()) {
   for (XWPFParagraph paragraph : cell.getParagraphs()) {
    for (XWPFRun run : paragraph.getRuns()) {
     run.setText("New row 3 cell " + i++, 0);
    }
   }
  }

  table.addRow(newRow, 2);

//insert new last row, which is a copy previous last row:
  XWPFTableRow lastRow = table.getRows().get(table.getNumberOfRows() - 1);
  ctrow = CTRow.Factory.parse(lastRow.getCtRow().newInputStream());
  newRow = new XWPFTableRow(ctrow, table);

  i = 1;
  for (XWPFTableCell cell : newRow.getTableCells()) {
   for (XWPFParagraph paragraph : cell.getParagraphs()) {
    for (XWPFRun run : paragraph.getRuns()) {
     run.setText("New last row cell " + i++, 0);
    }
   }
  }

  table.addRow(newRow);

  doc.write(new FileOutputStream("result.docx"));
  doc.close();

 }
}


/*
 XWPFTable table; //this is the table to be populated..needs to be initialized  as per your need.

   //the row ID of the empty row, which is our template row with all the formatting in it
    int tempateRowId = 1;

    // the empty row
    XWPFTableRow rowTemplate = table.getRow(tempateRowId);

    // iterate over the reportData
     Arrays.stream(reportData).forEach(data -> {
    // create a new row from the template, which would copy the format of previous row
     XWPFTableRow oldRow = rowTemplate;
     CTRow ctrow = null;
       try {
            ctrow = CTRow.Factory.parse(oldRow.getCtRow().newInputStream());
           } catch (XmlException e) {e.printStackTrace();
           } catch (IOException e) { e.printStackTrace();
           }

    XWPFTableRow newRow = new XWPFTableRow(ctrow, table);
         newRow.getCell(0).setText(data.getDataForColumn1());
         newRow.getCell(1).setText(data.getDataForColumn2());

   // adding the newly created row tot he table   
    table.addRow(newRow);
});

table.removeRow(tempateRowId); // removing the template row
*/
