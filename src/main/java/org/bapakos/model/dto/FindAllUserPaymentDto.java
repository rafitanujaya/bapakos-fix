package org.bapakos.model.dto;

public class FindAllUserPaymentDto {
    private String id;
    private String bookingId;
    private String nameKost;
    private int ammount;
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        waiting,
        paid
    }

    public FindAllUserPaymentDto() {}

    public String getNameKost() {
        return nameKost;
    }

    public void setNameKost(String nameKost) {
        this.nameKost = nameKost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

}
