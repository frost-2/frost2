package code.basic.hashMap;

import java.util.HashMap;

/**
 * @author 陈伟平
 * @date 2020-5-23 15:25:39
 */
public class HashMapDemo001{

    public static void main(String[] args) {

        HashMap<HashMapDemo001, String> map = new HashMap<>();
        map.put(new HashMapDemo001(),"value1");
        map.put(new HashMapDemo001(),"value2");
        map.put(new HashMapDemo001(),"value3");
        map.put(new HashMapDemo001(),"value4");
        map.put(new HashMapDemo001(),"value5");
        map.put(new HashMapDemo001(),"value6");
        map.put(new HashMapDemo001(),"value7");
        map.put(new HashMapDemo001(),"value8");
        map.put(new HashMapDemo001(),"value9");
        map.put(new HashMapDemo001(),"value10");
        map.put(new HashMapDemo001(),"value11");
        map.put(new HashMapDemo001(),"value12");
        map.put(new HashMapDemo001(),"value13");


//        System.out.println(Integer.highestOneBit((17 - 1) << 1));
//        System.out.println(Integer.highestOneBit((16 - 1) << 1));
//        System.out.println(Integer.highestOneBit((15 - 1) << 1));

    }

    @Override
    public int hashCode() {
        return 0;
    }
}
