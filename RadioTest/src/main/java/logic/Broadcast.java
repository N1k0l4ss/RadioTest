package logic;

import controller.MainWindowController;
import logic.bcparts.BcPart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Broadcast implements Serializable {
    private String broadcast;
    private int length;
    private double profit;
    private List<BcPart> parts;

    public boolean isSuccessfullyAdded(MainWindowController mainWindowController){
        double paidTimeCounterSeconds = 0;
        double totalTimeCounterSeconds = 0;
        for (BcPart part : parts) {
            if (part.isPaidContent())
                paidTimeCounterSeconds += part.getLengthSeconds();
            totalTimeCounterSeconds += part.getLengthSeconds();
        }
        if (totalTimeCounterSeconds > length * 60) {
            parts.remove(parts.size()-1);
            mainWindowController.displayError("Additional content cannot exceed broadcast time");
            return false;
        } else if (paidTimeCounterSeconds > ((length * 60)/2)){
            parts.remove(parts.size()-1);
            mainWindowController.displayError("Paid content cannot take more than half of the broadcast");
            return false;
        }
        return true;
    }

    public void calcProfit(){
        for (BcPart part : parts) {
            profit += part.getProfit();
        }
    }

    public Broadcast(String broadcast, int length) {
        this.broadcast = broadcast;
        this.length = length;
        this.profit = 0;
        this.parts = new ArrayList<>();
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public List<BcPart> getParts() {
        return parts;
    }

    public void setParts(List<BcPart> parts) {
        this.parts = parts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Broadcast)) return false;
        Broadcast broadcast1 = (Broadcast) o;
        return length == broadcast1.length && Double.compare(broadcast1.profit, profit) == 0 && Objects.equals(broadcast, broadcast1.broadcast) && Objects.equals(parts, broadcast1.parts);
    }
    @Override
    public int hashCode() {
        return Objects.hash(broadcast, length, profit, parts);
    }
}
