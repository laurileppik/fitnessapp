package fitnesssystem.dataobjects;

import org.alternativevision.gpx.beans.GPX;

public class ActivityTrack {
    private GPX gpx;

    public ActivityTrack(GPX gpx) {
        this.gpx = gpx;
    }

    public GPX getGpx() {
        return gpx;
    }

    public void setGpx(GPX gpx) {
        this.gpx = gpx;
    }
}
