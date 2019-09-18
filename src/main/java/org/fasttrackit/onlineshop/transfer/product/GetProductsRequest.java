package org.fasttrackit.onlineshop.transfer.product;

public class GetProductsRequest {
    private  String partialName;
    private Integer minimumQuantity; //Wrapper class accepts so it will also accept null

    public String getPartialName() {
        return partialName;
    }

    public void setPartialName(String partialName) {
        this.partialName = partialName;
    }

    public Integer getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(Integer minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    @Override
    public String toString() {
        return "GetProductsRequest{" +
                "partialName='" + partialName + '\'' +
                ", minimumQuantity=" + minimumQuantity +
                '}';
    }
}
