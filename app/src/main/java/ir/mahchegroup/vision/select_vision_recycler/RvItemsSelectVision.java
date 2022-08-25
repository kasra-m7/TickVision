package ir.mahchegroup.vision.select_vision_recycler;

public class RvItemsSelectVision {

    private String visionName;
    private String isTickVision;

    public RvItemsSelectVision(String visionName, String isTickVision) {
        this.visionName = visionName;
        this.isTickVision = isTickVision;
    }

    public String getVisionName() {
        return visionName;
    }

    public void setVisionName(String visionName) {
        this.visionName = visionName;
    }

    public String getIsTickVision() {
        return isTickVision;
    }

    public void setIsTickVision(String isTickVision) {
        this.isTickVision = isTickVision;
    }
}
