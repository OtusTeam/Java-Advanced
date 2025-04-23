package ecommerce;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.logging.Logger;

/**
 * gRPC client sample for productInfo service.
 */
public class ProductInfoClient {

    private static final Logger logger = Logger.getLogger(ProductInfoClient.class.getName());

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        ProductInfoGrpc.ProductInfoBlockingStub stub = ProductInfoGrpc.newBlockingStub(channel);

        ProductInfoOuterClass.ProductID productID = stub
                .addProduct(
                    ProductInfoOuterClass.Product
                            .newBuilder()
                            .setName("Samsung S10")
                            .setDescription("Samsung Galaxy S10 is the latest smart phone, launched in February 2019")
                            .setPrice(700.0f)
                            .build()
                );
        logger.info("Product ID: " + productID.getValue() + " added successfully.");

        ProductInfoOuterClass.Product product = stub.getProduct(productID);
        logger.info("Product: " + product.toString());

        final String badProductId = "abrakadabra";
        try {
            ProductInfoOuterClass.Product emptyProduct = stub.getProduct(
                    ProductInfoOuterClass.ProductID
                            .newBuilder()
                            .setValue(badProductId)
                            .build()
            );
            logger.info("Product: " + emptyProduct.toString());
        } catch (StatusRuntimeException e) {
            logger.warning("Product: " + badProductId + " not found!");
        }

        channel.shutdown();
    }
}
