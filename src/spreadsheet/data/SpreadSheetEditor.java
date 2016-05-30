package spreadsheet.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: Jul 29, 2014, 7:42:15 PM 
 */
public class SpreadSheetEditor {
	
	public static void removeColumnsByIndex( List<Integer> remove, SpreadSheetData data ) {
		List<String> modList = new ArrayList<String>();
		String[] columns = data.getColumns();
		for ( int i = 0; i < columns.length; i++ ) {
			if ( !remove.contains( i ) ) {
				modList.add( columns[ i ] );
			}
		}
		removeColumns( modList, data );
	}
	
	public static void removeColumns( List<String> modList, SpreadSheetData data ) {
		String[] columns = data.getColumns();
		
		List<Integer> columnTemplate = new ArrayList<Integer>();
		for ( int i = 0; i < columns.length; i++ ) {
			if ( modList.contains( columns[ i ] ) ) {
				columnTemplate.add( i );
			}
		}
		
		for ( int r = 0; r < data.getData().size(); r++ ) {
			List<SpreadSheetCellData> l = data.getData().get( r );
			List<Integer> remove = new ArrayList<Integer>();
			for ( SpreadSheetCellData d : l ) {
				if ( !columnTemplate.contains( d.getColumn() ) ) {
					remove.add( d.getColumn() );
				}
			}
			Collections.reverse( remove );
			for ( int rem : remove ) {
				l.remove( rem );
			}
		}
		data.setColumns( modList.toArray( new String[] {} ) );
	}
	
	public static void trimBlankRows( SpreadSheetData data ) {
		List<Integer> remove = new ArrayList<Integer>();
		for ( int r = 0; r < data.getData().size(); r++ ) {
			List<SpreadSheetCellData> l = data.getData().get( r );
			boolean clear = true;
			for ( SpreadSheetCellData d : l ) {
				if ( !( d.getContent() == null || d.getContent().equals( "" ) ) ) {
					clear = false;
				}
			}
			if ( clear ) {
				remove.add( r );
			}
		}
		Collections.reverse( remove );
		for ( int rem : remove ) {
			data.getData().remove( rem );
		}
	}
}