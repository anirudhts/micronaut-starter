package com.project.repository;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.IndexType;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.project.config.AerospikeProperties;
import com.project.constants.AerospikeConstants;
import com.project.models.db.CustomerEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CustomerCachedRepository {

  private final AerospikeClient aerospikeClient;
  private final WritePolicy writePolicy;

  private final AerospikeProperties aerospikeProperties;

  @Inject
  public CustomerCachedRepository(
      AerospikeClient aerospikeClient,
      WritePolicy writePolicy,
      AerospikeProperties aerospikeProperties) {
    this.aerospikeClient = aerospikeClient;
    this.writePolicy = writePolicy;
    this.aerospikeProperties = aerospikeProperties;
  }

  public void saveRecord(CustomerEntity customer) {
    Key key =
        new Key(
            aerospikeProperties.getNamespace(),
            AerospikeConstants.CUSTOMER_SET,
            customer.getCustomerId());
    Bin name = new Bin(AerospikeConstants.CUSTOMER_NAME, customer.getName());
    Bin phoneNo = new Bin(AerospikeConstants.PHONE_NUMBER, customer.getPhoneNo());
    aerospikeClient.put(writePolicy, key, name, phoneNo);
  }

  public void deleteRecord(Long customerId) {
    Key deleteKey =
        new Key(aerospikeProperties.getNamespace(), AerospikeConstants.CUSTOMER_SET, customerId);

    aerospikeClient.delete(writePolicy, deleteKey);
  }

  public Optional<CustomerEntity> fetchRecord(Long customerId) {
    Key key =
        new Key(aerospikeProperties.getNamespace(), AerospikeConstants.CUSTOMER_SET, customerId);
    Optional<Record> maybeRecord = Optional.ofNullable(aerospikeClient.get(null, key));
    return maybeRecord.map(
        record -> CustomerEntity.getCustomerEntityFromRecord(record, customerId));
  }

  public void putObjectAsBlob(CustomerEntity customer) {
    Key key =
        new Key(
            aerospikeProperties.getNamespace(),
            AerospikeConstants.CUSTOMER_SET,
            customer.getCustomerId());

    Bin cust = new Bin("customer", customer);
    aerospikeClient.put(writePolicy, key, cust);
  }

  // This method is to read entire object as a Blob
  public CustomerEntity getObjectBlob(Long customerId) {
    Key key =
        new Key(aerospikeProperties.getNamespace(), AerospikeConstants.CUSTOMER_SET, customerId);

    Record record = aerospikeClient.get(null, key);

    return (CustomerEntity) record.getValue("customer");
  }

  public void addSecondaryKey(String secondaryKey, IndexType indexType) {
    aerospikeClient.createIndex(
        null,
        AerospikeConstants.NAMESPACE,
        AerospikeConstants.CUSTOMER_SET,
        "idx_" + secondaryKey,
        secondaryKey,
        indexType);
  }

  public void dropSecondaryKey(String secondaryKey) {
    aerospikeClient.dropIndex(
        null, AerospikeConstants.NAMESPACE, AerospikeConstants.CUSTOMER_SET, "idx_" + secondaryKey);
  }

  public List<CustomerEntity> fetchFromSecondaryKey(String secondaryKey, String value) {
    Statement statement = new Statement();
    statement.setNamespace(AerospikeConstants.NAMESPACE);
    statement.setSetName(AerospikeConstants.CUSTOMER_SET);
    statement.setFilter(Filter.equal(secondaryKey, value));
    RecordSet recordSet = aerospikeClient.query(null, statement);
    try {
      List<CustomerEntity> customerEntities = new ArrayList<>();
      recordSet.forEach(
          record ->
              customerEntities.add(
                  CustomerEntity.getCustomerEntityFromRecord(
                      record.record, record.record.getLong("customerId"))));
      return customerEntities;
    } finally {
      recordSet.close();
    }
  }
}
