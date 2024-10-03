package jpabook.jpashop.domain.item;

public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException() {
        super("Not enough stock");
    }

    public NotEnoughStockException(String messsge) {
        super(messsge);
    }

    public NotEnoughStockException(String messsge, Throwable cause) {
        super(messsge, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }

    public NotEnoughStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
