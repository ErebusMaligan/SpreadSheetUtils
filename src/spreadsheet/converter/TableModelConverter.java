package spreadsheet.converter;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import spreadsheet.data.SpreadSheetData;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: Jul 29, 2014, 3:26:39 PM 
 */
public class TableModelConverter {
	
	public static TableModel getTableModel( SpreadSheetData sheet ) {
		Object[][] ret = new Object[ sheet.getData().size() ][ sheet.getColumns().length ];
		for ( int i = 0; i < sheet.getData().size(); i++ ) {
			for ( int j = 0; j < sheet.getColumns().length; j++ ) {
				ret[ i ][ j ] = sheet.getData().get( i ).get( j ).getContent();
			}
		}
		return new DefaultTableModel( ret, sheet.getColumns() );
	}
	
	public static SpreadSheetData getSpreadSheetData( TableModel model, String sheetName ) {
		String[] columns = new String[ model.getColumnCount() ];
		for ( int i = 0; i < model.getColumnCount(); i++ ) {
			columns[ i ] = model.getColumnName( i );
		}
		SpreadSheetData d = new SpreadSheetData( sheetName, columns );
		for ( int r = 0; r < model.getRowCount(); r++ ) {
			Object[] content = new Object[ model.getColumnCount() ];
			for ( int c = 0; c < model.getColumnCount(); c++ ) {
				content[ c ] = model.getValueAt( r, c );
			}
			d.addRow( content );
		}
		return d;
	}
}
