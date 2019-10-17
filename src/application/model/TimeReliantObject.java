package application.model;

/**
 * 
 * @author masonfisher
 *
 */
public interface TimeReliantObject {

	/**
	 * a function called to update objects that implement this interface. 
	 * 
	 * this function should take a double that represents the difference in time
	 * since the last update.
	 * 
	 * @param deltaTime
	 */
	void update(double deltaTime);
}
