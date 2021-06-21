package com.project.repository;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.policy.WritePolicy;
import com.project.config.AerospikeProperties;
import com.project.constants.AerospikeConstants;
import com.project.models.Customer;
import com.project.models.db.CustomerEntity;
import java.util.Map;
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

  public void insertRecord(CustomerEntity customer) {
    Key key =
        new Key(
            aerospikeProperties.getNamespace(),
            AerospikeConstants.CUSTOMER_SET,
            customer.getCustomerId());
    Bin name = new Bin(AerospikeConstants.CUSTOMER_NAME, customer.getName());
    Bin phoneNo = new Bin(AerospikeConstants.PHONE_NUMBER, customer.getPhoneNo());
    aerospikeClient.put(writePolicy, key, name, phoneNo);
  }

  public Customer fetchRecord(Long customerId) {
    Key key =
        new Key(aerospikeProperties.getNamespace(), AerospikeConstants.CUSTOMER_SET, customerId);
    Map<String, Object> records = aerospikeClient.get(null, key).bins;
    return new Customer(
        (String) records.get(AerospikeConstants.CUSTOMER_NAME),
        (String) records.get(AerospikeConstants.PHONE_NUMBER));
  }
}
