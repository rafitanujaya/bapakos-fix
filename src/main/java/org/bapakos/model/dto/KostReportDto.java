package org.bapakos.model.dto;

public class KostReportDto {
    private int totalKost;
    private int maxPrice;
    private int minPrice;

    public KostReportDto() {}

    public int getTotalKost() {
        return totalKost;
    }

    public void setTotalKost(int totalKost) {
        this.totalKost = totalKost;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }
}
