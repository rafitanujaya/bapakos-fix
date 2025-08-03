package org.bapakos.model.dto;

public class FindAllUserPaymentDto {
    private String id;
    private String bookingId;
    private int ammount;
    private FindAllPaymentOwnerKostDto.Status status;

    public enum Status {
        waiting,
        paid
    }

    public FindAllUserPaymentDto() {}

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

    public FindAllPaymentOwnerKostDto.Status getStatus() {
        return status;
    }

    public void setStatus(FindAllPaymentOwnerKostDto.Status status) {
        this.status = status;
    }
}
