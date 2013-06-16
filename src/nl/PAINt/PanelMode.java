/**
 * PAINt
 * 
 * Created for the course intro Human-Computer Interaction at the
 * Radboud Universiteit Nijmegen
 * 
 * 2013
 */
package nl.PAINt;

public enum PanelMode {
	DELETE, MOVE, SELECT, RECTANGLE, TRIANGLE, ELLIPSE, ELL_FILLED, RECT_FILLED, LINE, TEXT, NONE;

	@Override
	public String toString() {
		switch (this) {
		case DELETE:
			return "Delete mode";
		case ELLIPSE:
			return "Ellipse mode";
		case ELL_FILLED:
			return "Filled ellipse mode";
		case MOVE:
			return "Move mode";
		case RECT_FILLED:
		case RECTANGLE:
			return "Rectangle mode";
		case TRIANGLE:
			return "Filled rectangle mode";
		case SELECT:
			return "Resize mode";
		case LINE:
			return "Line Mode";
		case TEXT:
			return "Text Mode";
		case NONE:
			return "No Mode";
		default:
			return "UNKNOWN MODE";

		}

	}
}