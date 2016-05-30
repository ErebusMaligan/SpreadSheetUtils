package spreadsheet.export;

import java.awt.Color;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Mar 22, 2015, 9:09:11 PM 
 */
public class CellStyleData {
	
	public static final short DEFAULT_SIZE = -1;
	
	public boolean BOLD = false;
	
	public short SIZE = DEFAULT_SIZE;
	
	public Color BG = null;
	
	public Color FG = null;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((BG == null) ? 0 : BG.hashCode());
		result = prime * result + (BOLD ? 1231 : 1237);
		result = prime * result + ((FG == null) ? 0 : FG.hashCode());
		result = prime * result + SIZE;
		return result;
	}

//	@Override
//	public boolean equals( Object obj ) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		CellStyleData other = (CellStyleData) obj;
//		if (BG == null) {
//			if (other.BG != null)
//				return false;
//		} else if (!BG.equals(other.BG))
//			return false;
//		if (BOLD != other.BOLD)
//			return false;
//		if (FG == null) {
//			if (other.FG != null)
//				return false;
//		} else if (!FG.equals(other.FG))
//			return false;
//		if (SIZE != other.SIZE)
//			return false;
//		return true;
//	}
	
	@Override
	public boolean equals( Object obj ) {
		boolean ret = false;
		if ( obj != null ) {
			if ( getClass() == obj.getClass() ) {
				CellStyleData other = (CellStyleData) obj;
				boolean bg = BG == null && other.BG == null || BG.equals( other.BG );
				boolean fg = FG == null && other.FG == null || FG.equals( other.FG );
				boolean bd = BOLD == other.BOLD;
				boolean sz = SIZE == other.SIZE;
				if ( bg && fg && bd && sz ) {
					ret = true;
				}
			}
		}
		return ret;
	}
}
