package ecommerce;

import io.grpc.Status;
import io.grpc.StatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProductInfoImpl extends ProductInfoGrpc.ProductInfoImplBase {

    private Map productMap = new HashMap<String, ProductInfoOuterClass.Product>();

    @Override
    public void addProduct(
            ProductInfoOuterClass.Product request,
            io.grpc.stub.StreamObserver<ProductInfoOuterClass.ProductID> responseObserver
    ) {
        String randomUUIDString = UUID.randomUUID().toString();

        ProductInfoOuterClass.Product toSave = request
                .toBuilder()
                .setId(randomUUIDString)
                .build();
        productMap.put(randomUUIDString, toSave);

        ProductInfoOuterClass.ProductID id = ProductInfoOuterClass.ProductID
                .newBuilder()
                .setValue(randomUUIDString)
                .build();

        responseObserver.onNext(id);
        responseObserver.onCompleted();
    }

    @Override
    public void getProduct(
            ProductInfoOuterClass.ProductID request,
            io.grpc.stub.StreamObserver<ProductInfoOuterClass.Product> responseObserver
    ) {
        String id = request.getValue();

        if (productMap.containsKey(id)) {
            responseObserver.onNext((ProductInfoOuterClass.Product) productMap.get(id));
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new StatusException(Status.NOT_FOUND));
        }
    }
}
