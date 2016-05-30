package spreadsheet.data;
import spreadsheet.export.CellStyleData;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: Jul 28, 2014, 1:46:47 AM 
 */
public class SpreadSheetCellData {
	
	protected int column = 0;
	
	protected int row = 0;
	
	protected Object content;
	
	protected CellStyleData style;
	
	public SpreadSheetCellData( int column, int row, Object content, CellStyleData style ) {
		this.column = column;
		this.row = row;
		this.content = content;
		this.style = style;
	}
	
	public SpreadSheetCellData( int column, int row ) {
		this( column, row, null, null );
	}

	public SpreadSheetCellData( int column, int row, Object content ) {
		this( column, row, content, null );
	}
	
	public SpreadSheetCellData( int column, int row, CellStyleData style ) {
		this( column, row, null, style );
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn( int column ) {
		this.column = column;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow( int row ) {
		this.row = row;
	}

	/**
	 * @return the content
	 */
	public Object getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(Object content) {
		this.content = content;
	}
	
	/**
	 * @return the style
	 */
	public CellStyleData getStyle() {
		return style;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(CellStyleData style) {
		this.style = style;
	}

	@Override
	public String toString() {
		return "(" + column + ", " + row + ") BG:" + style.BG + " <Content>" + content + "</Content>";
	}
}
