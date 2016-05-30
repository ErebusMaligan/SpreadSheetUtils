package spreadsheet.utils;

import spreadsheet.data.SpreadSheetCellData;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: Jul 29, 2014, 3:09:47 AM 
 */
public class LabelUtils {
	
	public static String getCellName( SpreadSheetCellData data ) {
		return getColumnName( data.getColumn() ) + ( data.getRow() + 2 );
	}
	
	public static String getColumnName( int column ) {
		String ret = "";
		int a = 26;
		if ( column > -1 && column < a ) {
			ret = String.valueOf( (char)( column + 'A') );
		} else if ( column > a ) {
			ret += getColumnName( column / a - 1 ) + getColumnName( column % a );
		}
		return ret;
	}

}
