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
    DELETE, MOVE, RESIZE, RECT, RECT_FILLED, ELL, ELL_FILLED;

    @Override
    public String toString() {
	switch (this) {
	case DELETE:
	    return "Delete mode";
	case ELL:
	    return "Ellipse mode";
	case ELL_FILLED:
	    return "Filled ellipse mode";
	case MOVE:
	    return "Move mode";
	case RECT:
	    return "Rectangle mode";
	case RECT_FILLED:
	    return "Filled rectangle mode";
	case RESIZE:
	    return "Resize mode";
	default:
	    return "UNKNOWN MODE";

	}


    }
}