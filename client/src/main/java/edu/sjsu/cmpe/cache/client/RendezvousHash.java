package edu.sjsu.cmpe.cache.client;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.common.hash.Hashing;

public class RendezvousHash<T> {

	private Set<T> nodes;
	//private volatile T[] ordered;
	
	public RendezvousHash( Collection<T> initialNodes) {
		this.nodes = Sets.newHashSet();
	//	this.ordered = (T[]) new Object[0];
		for (T node: (T[]) initialNodes.toArray()) {
			add(node);
		}
	}
	
	public synchronized boolean add(T node) {
		boolean ret = nodes.add(node);
		//T[] tmp = (T[]) nodes.toArray();
		//Arrays.sort(tmp);
	//	ordered = tmp;
		return ret;
	}
	
	//private sort
	
	public T get(int key) {
		long maxValue = Long.MIN_VALUE;
		T max = null;
		for (T node : nodes) {
			int index = node.toString().lastIndexOf(':');
		      int name = Integer.parseInt(node.toString().substring(index + 1));
		      
			long nodesHash = Hashing.murmur3_128().newHasher()
					.putLong(name)
					.putLong(key)
					.hash().asLong();
			//System.out.println(nodesHash+"...Hash value");
			if (nodesHash > maxValue) {
				max = node;
				maxValue = nodesHash;
			}
		}
		return max;
	}
}
