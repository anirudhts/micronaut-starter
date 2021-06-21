package com.project.repository;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.policy.WritePolicy;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CustomerCachedRepository {

  private final AerospikeClient aerospikeClient;
  private final WritePolicy writePolicy;

  @Inject
  public CustomerCachedRepository(AerospikeClient aerospikeClient, WritePolicy writePolicy) {
    this.aerospikeClient = aerospikeClient;
    this.writePolicy = writePolicy;
  }

  public void insertRecord() {
    Key key = new Key("test", "sampleset", "1");
    Bin name = new Bin("name", "someperson");
    Bin phoneNo = new Bin("phone_number", "123456");
    aerospikeClient.put(writePolicy, key, name, phoneNo);
  }
}
