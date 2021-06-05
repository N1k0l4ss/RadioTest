package logic.bcparts;

import java.io.Serializable;
import java.util.Objects;

abstract public class BcPart implements Serializable {
    protected Boolean paidContent;
    protected String info;
    protected double length; // minutes
    protected double lengthSeconds;
    protected String contentType;
    protected double profit;

    public BcPart(Boolean paidContent, String info, double length, String contentType, double priceOfASecond) {
        this.paidContent = paidContent;
        this.info = info;
        this.lengthSeconds = length;
        this.length = length / 60;
        this.contentType = contentType;
        this.profit = priceOfASecond * this.length * 60;
    }

    public Boolean isPaidContent() {
        return paidContent;
    }

    public void setPaidContent(Boolean paidContent) {
        this.paidContent = paidContent;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getContentType() {
        return contentType;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getLengthSeconds() {
        return lengthSeconds;
    }

    public void setLengthSeconds(double lengthSeconds) {
        this.lengthSeconds = lengthSeconds;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "BcPart{" +
                "paidContent=" + paidContent +
                ", info='" + info + '\'' +
                ", length=" + length +
                ", lengthSeconds=" + lengthSeconds +
                ", contentType='" + contentType + '\'' +
                ", profit=" + profit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BcPart)) return false;
        BcPart bcPart = (BcPart) o;
        return Double.compare(bcPart.length, length) == 0 && Double.compare(bcPart.lengthSeconds, lengthSeconds) == 0 && Double.compare(bcPart.profit, profit) == 0 && Objects.equals(paidContent, bcPart.paidContent) && Objects.equals(info, bcPart.info) && Objects.equals(contentType, bcPart.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paidContent, info, length, lengthSeconds, contentType, profit);
    }
}
