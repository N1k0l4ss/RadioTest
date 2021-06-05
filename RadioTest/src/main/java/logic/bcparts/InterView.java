package logic.bcparts;

import logic.OnAirTalent;

import java.util.Objects;

public class InterView extends BcPart {
    private OnAirTalent onAirTalent;

    public InterView(OnAirTalent onAirTalent, String info, int lengthSeconds) {
        super(
                true,
                "On-air Talent: " + onAirTalent.getName() + ". " + info,
                lengthSeconds,
                "Interview",
                0.5);
        this.onAirTalent = onAirTalent;
    }

    public OnAirTalent getOnAirTalent() {
        return onAirTalent;
    }

    public void setOnAirTalent(OnAirTalent onAirTalent) {
        this.onAirTalent = onAirTalent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InterView)) return false;
        if (!super.equals(o)) return false;
        InterView interView = (InterView) o;
        return Objects.equals(onAirTalent, interView.onAirTalent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), onAirTalent);
    }
}
