package beans;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScaleFxBean {

	private final StringProperty scaleReadingFull;
	private final FloatProperty scaleReading;
	private final StringProperty scaleStability;

	public ScaleFxBean() {
		this(null, 0, null);
	}

	public ScaleFxBean(String scaleReadingFull, Integer scaleReading, String scaleStability) {
		this.scaleReadingFull = new SimpleStringProperty(scaleReadingFull);
		this.scaleReading = new SimpleFloatProperty(scaleReading);
		this.scaleStability = new SimpleStringProperty(scaleStability);
	}

	public final StringProperty scaleReadingFullProperty() {
		return this.scaleReadingFull;
	}

	public final String getScaleReadingFull() {
		return this.scaleReadingFullProperty().get();
	}

	public final void setScaleReadingFull(final String scaleReadingFull) {
		this.scaleReadingFullProperty().set(scaleReadingFull);
	}

	public final StringProperty scaleStabilityProperty() {
		return this.scaleStability;
	}

	public final String getScaleStability() {
		return this.scaleStabilityProperty().get();
	}

	public final void setScaleStability(final String scaleStability) {
		this.scaleStabilityProperty().set(scaleStability);
	}

	public final FloatProperty scaleReadingProperty() {
		return this.scaleReading;
	}

	public final float getScaleReading() {
		return this.scaleReadingProperty().get();
	}

	public final void setScaleReading(final float scaleReading) {
		this.scaleReadingProperty().set(scaleReading);
	}

}
