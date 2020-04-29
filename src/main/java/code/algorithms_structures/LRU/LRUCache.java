package code.algorithms_structures.LRU;

import java.util.HashMap;

/**
 * @author 陈伟平
 * @date 2020-4-16 10:45:30
 */
public class LRUCache {

    private HashMap<Integer,Node> map;

    private DoubleList cache;

    private int cap;

    public LRUCache(int capacity){
        this.cap = capacity;
        map = new HashMap<>();
        cache = new DoubleList();
    }

    public int get(int key){
        if(!map.containsKey(key)){
            return -1;
        }
        int val = map.get(key).val;
        put(key,val);
        return val;
    }

    public void put(int key,int val){
        Node x = new Node(key, val);

        if(map.containsKey(key)){
            //删除旧的节点，将新节点放到头部
            cache.remove(map.get(key));
            cache.addHead(x);
        }else{
            if(cap == cache.size()){
                //删除尾结点
                Node last = cache.removeLast();
                map.remove(last.key);
            }
            //添加头结点
            cache.addHead(x);
            map.put(key,x);
        }
    }

}
