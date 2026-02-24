package io.github.kiriinteo.visuvarejo.core.alerts;

import java.util.UUID;

public class Alert {

    private final AlertType type;
    private final String message;
    private final String severity;
    private final UUID productId;
    private final String productName;

    public Alert(AlertType type, 
                 String message,
                 String severity,
                 UUID productId,
                 String productName) {

        this.type = type;
        this.message = message;
        this.severity = severity;
        this.productId = productId;
        this.productName = productName;
    }

    public AlertType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getSeverity() {
        return severity;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }


}