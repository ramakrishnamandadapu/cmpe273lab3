package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;

public class Client {

	public static void main(String[] args) throws Exception {
		System.out.println("Starting Cache Client...");
		CacheServiceInterface cache1 = new DistributedCacheService(
				"http://localhost:3000");
		CacheServiceInterface cache2 = new DistributedCacheService(
				"http://localhost:3001");
		CacheServiceInterface cache3 = new DistributedCacheService(
				"http://localhost:3002");

		List<CacheServiceInterface> servers = new ArrayList<CacheServiceInterface>();
		servers.add(cache1);
		servers.add(cache2);
		servers.add(cache3);

		System.out.println("********************************************");
		System.out.println("\nRendezvousHash Begins..........");
		RendezvousHash<CacheServiceInterface> rendezvousHashServers = new RendezvousHash<CacheServiceInterface>(
				 servers);

		System.out.println("Putting the values........");
		char value = 'a';
		for (int i = 1; i <= 10; i++) {
			
			CacheServiceInterface cache = rendezvousHashServers.get(i);
			cache.put(i, String.valueOf(value));
			System.out.println("put(" + i + ") => into" + cache.toString()+"..Value::"+  value);
			value++;
		}
		System.out.println("\nGetting the values........");
		for (int i = 1; i <= 10; i++) {
			
			CacheServiceInterface cache =rendezvousHashServers.get(i);
			String returnValue = cache.get(i);
			System.out.println("get(" + i + ") =>from " + cache.toString()+ "..Value::" + returnValue);
			value++;
		}

		System.out.println("RendezvousHash Ends...");
		System.out.println("Existing Cache Client...");
	}

}
