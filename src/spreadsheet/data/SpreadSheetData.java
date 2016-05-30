package spreadsheet.data;

import java.util.ArrayList;
import java.util.List;

import spreadsheet.export.CellStyleData;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: Jul 28, 2014, 1:42:07 AM 
 */
public class SpreadSheetData extends SpreadSheetCellData {
	
	protected String[] columns;
	
	protected List<List<SpreadSheetCellData>> data = new ArrayList<List<SpreadSheetCellData>>();
	
	public SpreadSheetData( String sheetName, String[] columns ) {
		super( 0, 0, sheetName );
		this.columns = columns;
		column = -1;
		row = -1;
	}
	
	public void setColumns( String[] columns ) {
		this.columns = columns;
	}
	
	public SpreadSheetCellData getData( int row, int col ) {
		return data.get( row ).get( col );
	}
	
	public void addRow( Object[] content, CellStyleData[] style ) {
		endRow();
		for ( int i = 0; i < content.length; i++ ) {
			add( content[ i ], style[ i ] );
		}
	}
	
	public void addRow( Object[] content, CellStyleData style ) {
		endRow();
		for ( int i = 0; i < content.length; i++ ) {
			add( content[ i ], style );
		}
	}
	
	public void addRow( Object[] content ) {
		addRow( content, new CellStyleData[ content.length ] ); //can't be null, because that is ambiguous
	}
	
	
	public void add( Object content, CellStyleData style ) {
		data.get( row ).add( new SpreadSheetCellData( ++column, row, content, style ) );
	}
	
	public void add( Object content ) {
		add( content, null );
	}
	
	public void endRow() {
		column = -1;
		data.add( new ArrayList<SpreadSheetCellData>() );
		row++;
	}
	
	@Override
	public String toString() {
		String ret = content + "\n1\t";
		for ( String c : columns ) {
			ret += c + "\t";
		}
		ret += "\n\n";
		for ( int i = 0; i < data.size(); i++ ) {
			ret += i + 2 + "\t";
			for ( SpreadSheetCellData s : data.get( i ) ) {
				ret += s.getContent() + "\t";
			}
			ret += "\n";
		}
		return ret;
	}
	
	public String[] getColumns() {
		return columns;
	}

	public List<List<SpreadSheetCellData>> getData() {
		return data;
	}
	
	//TEST PROGRAM
	public static void main( String args[] ) {
		SpreadSheetData d = new SpreadSheetData( "testSheet", new String[] { "a", "b", "c", "d" } );
		
		for ( int i = 0; i < 20; i++ ) {
			d.addRow( new String[] { "A", "B", "C", "D" } );
		}
		System.out.println( d + "\n\n");
		
		for ( List<SpreadSheetCellData> r : d.getData() ) {
			for ( SpreadSheetCellData c : r ) {
				System.out.println( "Content: " + c.getContent() );
			}
		}
	}
}