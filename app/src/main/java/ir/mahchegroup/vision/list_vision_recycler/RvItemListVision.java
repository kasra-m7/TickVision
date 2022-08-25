package ir.mahchegroup.vision.list_vision_recycler;

public class RvItemListVision {

    private String nameVision;
    private int markIcon;

    public RvItemListVision(String nameVision, int markIcon) {
        this.nameVision = nameVision;
        this.markIcon = markIcon;
    }

    public String getNameVision() {
        return nameVision;
    }

    public void setNameVision(String nameVision) {
        this.nameVision = nameVision;
    }

    public int getMarkIcon() {
        return markIcon;
    }

    public void setMarkIcon(int markIcon) {
        this.markIcon = markIcon;
    }
}
