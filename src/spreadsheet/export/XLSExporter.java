package spreadsheet.export;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;

import spreadsheet.data.SpreadSheetCellData;
import spreadsheet.data.SpreadSheetData;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: Jul 29, 2014, 3:10:32 AM 
 */
public class XLSExporter {

	private static final float ROW_SIZE = 1.275f;
	
	public static File getExportFile( Component parent ) {
		File ret = null;
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter( new FileNameExtensionFilter( "Excel Spreadsheet (*.xls)", "xls" ) );
		if ( JFileChooser.APPROVE_OPTION == fc.showSaveDialog( parent ) ) {
			ret = fc.getSelectedFile();
		}
		if ( ret != null ) {
			if ( !ret.getName().endsWith( ".xls" ) ) {
				ret = new File( ret.getAbsolutePath() + ".xls" );
			}
		}
		return ret;
	}
	
	public static void exportSpreadSheet( Component parent, SpreadSheetData data, File f ) {
		List<SpreadSheetData> sheets = new ArrayList<SpreadSheetData>();
		sheets.add( data );
		exportSpreadSheet( parent, sheets, f );
	}
	
	public static void exportSpreadSheet( Component parent, List<SpreadSheetData> sheets, File f ) {  //parent can be null, it's just for dialog anyway
		if ( f == null ) {
			f = getExportFile( parent );
		}
		if ( f != null ) {
			FileOutputStream out = null;
			try {
				out = new FileOutputStream( f );
				
				HSSFWorkbook workbook = new HSSFWorkbook();
				Map<CellStyleData, HSSFCellStyle> styles = new HashMap<CellStyleData, HSSFCellStyle>();
				
				for ( SpreadSheetData data : sheets ) {
					HSSFSheet sheet = workbook.createSheet( (String)data.getContent() );
					Row row = sheet.createRow( 0 );

					//columns on top
					createHeaders( data.getColumns(), workbook, row, styles );
					
					//rest of the data
					createData( data.getData(), workbook, sheet, styles );
					
					//autosize columns
					for ( int i = 0; i < data.getColumns().length; i++ ) {
						sheet.autoSizeColumn( i );
					}
				}
				
				//save file
				workbook.write( out );
				out.close();
				
				JOptionPane.showMessageDialog( parent, "Exported to " + f.getAbsolutePath(), "Export Complete", JOptionPane.INFORMATION_MESSAGE );
			} catch ( Exception e ) {
				JOptionPane.showMessageDialog( parent, "Export Unsuccessful: " + e.getMessage() , "Export Failed", JOptionPane.ERROR_MESSAGE );
			} finally {
				try {
					if ( out != null ) {
						out.close();
					}
				} catch ( IOException e ) {} //if this fails... nobody cares
			}
		} else {
			JOptionPane.showMessageDialog( parent, "Export Unsuccessful: Invalid File Provided" , "Export Failed", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private static void createHeaders( String[] strings, HSSFWorkbook workbook, Row row, Map<CellStyleData, HSSFCellStyle> styles ) {
		int cellnum = 0;
		for ( String s : strings ) {
			Cell cell = row.createCell( cellnum++ );
			cell.setCellValue( s );
			CellStyleData header = new CellStyleData();
			header.BG = Color.BLACK;
			header.FG = Color.WHITE;
			header.SIZE = (short)12;
			header.BOLD = false;
			cell.setCellStyle( getStyle( header, workbook, styles ) );
			row.setHeightInPoints( 12f * ROW_SIZE );
		}
	}
	
	private static void createData( List<List<SpreadSheetCellData>> data, HSSFWorkbook workbook, HSSFSheet sheet, Map<CellStyleData, HSSFCellStyle> styles ) {
		int rownum = 1;
		for ( List<SpreadSheetCellData> r : data ) {
			Row row = sheet.createRow( rownum++ );
			int cellnum = 0;
			for ( SpreadSheetCellData c : r ) {
				Cell cell = row.createCell( cellnum++ );
				if ( c.getStyle() != null ) {	//color info
					cell.setCellStyle( getStyle( c.getStyle(), workbook, styles ) );
					if ( c.getStyle().SIZE != CellStyleData.DEFAULT_SIZE ) {
						row.setHeightInPoints( (float)c.getStyle().SIZE * ROW_SIZE );
					}
				}
				Object con = c.getContent();
				setCellValue( cell, con );
			}
		}
	}
	
	private static void setCellValue( Cell cell, Object con ) {
		if ( con != null ) {
			if ( con instanceof Date ) {
				cell.setCellValue( (Date)con );
			} else if ( con instanceof Boolean ) {
				cell.setCellValue( (Boolean)con );
			} else if ( con instanceof String ) {
				cell.setCellValue( (String)con );
			} else if ( con instanceof Double ) {
				cell.setCellValue( (Double)con );
			} else if ( con instanceof Integer ) {
				cell.setCellValue( ( (Integer)con ).doubleValue() );
			} else if ( con instanceof Float ) {
				cell.setCellValue( Double.parseDouble( "" + ( (Float)con ) ) );  //this is the only way I don't get constant rounding errors
			} else if ( con instanceof Long ) {
				cell.setCellValue( ( (Long)con ).doubleValue() );
			}
		}
	}
	
	private static HSSFCellStyle getStyle( CellStyleData sd, HSSFWorkbook workbook, Map<CellStyleData, HSSFCellStyle> map ) {
		HSSFCellStyle ret = null;		
		if ( map.containsKey( sd ) ) {
			ret = map.get( sd );
		} else {
			HSSFCellStyle style = workbook.createCellStyle();
			
			Color bg = sd.BG;
			Color fg = sd.FG;
			boolean b = sd.BOLD;
			short sz = sd.SIZE;
			
			if ( bg != null ) {
				style.setFillForegroundColor( workbook.getCustomPalette().findSimilarColor( bg.getRed(), bg.getGreen(), bg.getBlue() ).getIndex() );
				style.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND );
			}
			if ( b || sz != CellStyleData.DEFAULT_SIZE || fg != null ) {
				HSSFFont font = workbook.createFont();
				font.setFontName( HSSFFont.FONT_ARIAL );
				if ( b ) {
					font.setBoldweight( Font.BOLDWEIGHT_BOLD );
				}
				if ( sz != CellStyleData.DEFAULT_SIZE ) {
					font.setFontHeightInPoints( sz );
				}
				if ( fg != null ) {
					font.setColor( workbook.getCustomPalette().findSimilarColor( fg.getRed(), fg.getGreen(), fg.getBlue() ).getIndex() );
				}
				style.setFont( font );
			}
			ret = style;
			map.put( sd, style );
		}
		return ret;
	}
}