# HashMap 笔记

## 一、put过程

### 1、put方法

```java
/**
 * Associates the specified value with the specified key in this map.
 * If the map previously contained a mapping for the key, the old
 * value is replaced.
 * 将指定值与指定键在这个map中相关联。如果这个key在map已经存在映射关系，那么旧的值就会被
 * 覆盖。
 *
 * @param key key with which the specified value is to be associated
 * @param value value to be associated with the specified key
 *
 * @return the previous value associated with <tt>key</tt>, or
 *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
 *         (A <tt>null</tt> return can also indicate that the map
 *         previously associated <tt>null</tt> with <tt>key</tt>.)
 * 与key关联的上一个值或null(当key没有对应的映射关系时)。
 * 返回null也可能表示与可以关联的上一个值为null
 */
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}
```

HashMap的put方法其实是调用了内部的putVal方法，并将hash方法的返回值作为putVal的第一个参数。

### 2、hash方法

```java
/**
 * 当key为null，则key的hash值为0。
 * 当key不为null，则将key的hashCode值与key的hashCode值无符号右移16位之后的值做异或。
 * 
 * hash方法计算出来的值主要用来计算数组的下标(即桶的位置)。
 * 这种计算方式主要是为了在计算桶的位置时减小hash碰撞的概率。
 */
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

### 3、putVal方法

```java
/**
 * Implements Map.put and related methods
 *
 * @param hash key的hash值
 * @param key 键
 * @param value 值
 * @param onlyIfAbsent 如果为true，则不改变已经存在的值
 * @param evict 如果为false，则表处于创建模式。
 * @return 前一个值，如果没有，则为null
 */
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    /*
     * 将HashMap的Node数组赋值给新建的Node数组tab，然后判断其是否为空:
     * (1):如果为空将调用resize()方法进行扩容,并将新数组赋值为tab,新数组长度赋值给n
     */
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    /*
     * (2):如果数组不为空，则计算数组的下标i通过:(n-1)&hash,即(数组长度-1) & hash(key).
     * (2.1):如果tab数组这个下标位置没有元素，则在该下标位置新创建一个Node
     */
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else {
        /*
         * (2.2):如果该下标位置存在元素：
         * (2.2.1):当该元素的key与新增元素的key相同时(key的hash相同并且key也相同)，将P赋值给Node节点e，之后执行(3)操作。
         */
        Node<K,V> e; K k;
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            //引用传递(地址传递)
            e = p;
        /*
         * (2.2.2):该下标元素是树结构式时：则向树中添加元素。
         */
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        /*
         * (2.2.3):不满足2.2.1与2.2.2时，说明出现hash碰撞，则通过尾插法将新元素插入链表，当链表长度大于8时，将链表转为红黑树。
         */
        else {
            //for循环链表，当新增key与链表中元素都不同时，取出链表最后一个元素，然后通过尾插法添加新元素，当链表长度大于8时(添加第9个元素)将链表转为红黑			//树
            for (int binCount = 0; ; ++binCount) {
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    if (binCount >= TREEIFY_THRESHOLD - 1)
                        treeifyBin(tab, hash);
                    break;
                }
                //判断新增元素key与链表中的元素是否相同，相同则直接跳出循环，执行(3)操作。
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                //类似于将指针指向下一个元素
                p = e;
            }
        }
        /*
         * (3):返回旧值，并根据情况将判断是否覆盖旧值。
         */
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;
    /*
     * (4):当map元素个数大于threshold时，进行resize()
     */
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}

```

### resize方法

```java
final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table; //当前所有元素所在的数组，称为老的元素数组
        int oldCap = (oldTab == null) ? 0 : oldTab.length; //老的元素数组长度
        int oldThr = threshold;	// 老的扩容阀值设置
        int newCap, newThr = 0;	// 新数组的容量，新数组的扩容阀值都初始化为0
        if (oldCap > 0) {	// 如果老数组长度大于0，说明已经存在元素
            // PS1
            if (oldCap >= MAXIMUM_CAPACITY) { // 如果数组元素个数大于等于限定的最大容量（2的30次方）
                // 扩容阀值设置为int最大值（2的31次方 -1 ），因为oldCap再乘2就溢出了。
                threshold = Integer.MAX_VALUE;	
                return oldTab;	// 返回老的元素数组
            }
 
           /*
            * 如果数组元素个数在正常范围内，那么新的数组容量为老的数组容量的2倍（左移1位相当于乘以2）
            * 如果扩容之后的新容量小于最大容量  并且  老的数组容量大于等于默认初始化容量（16），那么新数组的扩容阀值设置为老阀值的2倍。（老的数组容量大于16意味着：要么构造函数指定了一个大于16的初始化容量值，要么已经经历过了至少一次扩容）
            */
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        }
 
        // PS2
        // 运行到这个else if  说明老数组没有任何元素
        // 如果老数组的扩容阀值大于0，那么设置新数组的容量为该阀值
        // 这一步也就意味着构造该map的时候，指定了初始化容量。
        else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
            // 能运行到这里的话，说明是调用无参构造函数创建的该map，并且第一次添加元素
            newCap = DEFAULT_INITIAL_CAPACITY;	// 设置新数组容量 为 16
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY); // 设置新数组扩容阀值为 16*0.75 = 12。0.75为负载因子（当元素个数达到容量了4分之3，那么扩容）
        }
 
        // 如果扩容阀值为0 （PS2的情况）
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);  // 参见：PS2
        }
        threshold = newThr; // 设置map的扩容阀值为 新的阀值
        @SuppressWarnings({"rawtypes","unchecked"})
            // 创建新的数组（对于第一次添加元素，那么这个数组就是第一个数组；对于存在oldTab的时候，那么这个数组就是要需要扩容到的新数组）
            Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;	// 将该map的table属性指向到该新数组
        if (oldTab != null) {	// 如果老数组不为空，说明是扩容操作，那么涉及到元素的转移操作
            for (int j = 0; j < oldCap; ++j) { // 遍历老数组
                Node<K,V> e;
                if ((e = oldTab[j]) != null) { // 如果当前位置元素不为空，那么需要转移该元素到新数组
                    oldTab[j] = null; // 释放掉老数组对于要转移走的元素的引用（主要为了使得数组可被回收）
                    if (e.next == null) // 如果元素没有有下一个节点，说明该元素不存在hash冲突
                        // PS3
                        // 把元素存储到新的数组中，存储到数组的哪个位置需要根据hash值和数组长度来进行取模
                        // 【hash值  %   数组长度】   =    【  hash值   & （数组长度-1）】
                        //  这种与运算求模的方式要求  数组长度必须是2的N次方，但是可以通过构造函数随意指定初始化容量呀，如果指定了17,15这种，岂不是出问题了就？没关系，最终会通过tableSizeFor方法将用户指定的转化为大于其并且最相近的2的N次方。 15 -> 16、17-> 32
                        newTab[e.hash & (newCap - 1)] = e;
 
                        // 如果该元素有下一个节点，那么说明该位置上存在一个链表了（hash相同的多个元素以链表的方式存储到了老数组的这个位置上了）
                        // 例如：数组长度为16，那么hash值为1（1%16=1）的和hash值为17（17%16=1）的两个元素都是会存储在数组的第2个位置上（对应数组下标为1），当数组扩容为32（1%32=1）时，hash值为1的还应该存储在新数组的第二个位置上，但是hash值为17（17%32=17）的就应该存储在新数组的第18个位置上了。
                        // 所以，数组扩容后，所有元素都需要重新计算在新数组中的位置。
 
 
                    else if (e instanceof TreeNode)  // 如果该节点为TreeNode类型
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);  // 此处单独展开讨论
                    else { // preserve order
                        Node<K,V> loHead = null, loTail = null;  // 按命名来翻译的话，应该叫低位首尾节点
                        Node<K,V> hiHead = null, hiTail = null;  // 按命名来翻译的话，应该叫高位首尾节点
                        // 以上的低位指的是新数组的 0  到 oldCap-1 、高位指定的是oldCap 到 newCap - 1
                        Node<K,V> next;
                        // 遍历链表
                        do {  
                            next = e.next;
                            // 这一步判断好狠，拿元素的hash值  和  老数组的长度  做与运算
                            // PS3里曾说到，数组的长度一定是2的N次方（例如16），如果hash值和该长度做与运算，那么该hash值可参与计算的有效二进制位就是和长度二进制对等的后几位，如果结果为0，说明hash值中参与计算的对等的二进制位的最高位一定为0.
                            //因为数组长度的二进制有效最高位是1（例如16对应的二进制是10000），只有*..0**** 和 10000 进行与运算结果才为00000（*..表示不确定的多个二进制位）。又因为定位下标时的取模运算是以hash值和长度减1进行与运算，所以下标 = (*..0**** & 1111) 也= (*..0**** & 11111) 。1111是15的二进制、11111是16*2-1 也就是31的二级制（2倍扩容）。
                            // 所以该hash值再和新数组的长度取摸的话mod值也不会放生变化，也就是说该元素的在新数组的位置和在老数组的位置是相同的，所以该元素可以放置在低位链表中。
                            if ((e.hash & oldCap) == 0) {  
                                // PS4
                                if (loTail == null) // 如果没有尾，说明链表为空
                                    loHead = e; // 链表为空时，头节点指向该元素
                                else
                                    loTail.next = e; // 如果有尾，那么链表不为空，把该元素挂到链表的最后。
                                loTail = e; // 把尾节点设置为当前元素
                            }
 
                            // 如果与运算结果不为0，说明hash值大于老数组长度（例如hash值为17）
                            // 此时该元素应该放置到新数组的高位位置上
                            // 例：老数组长度16，那么新数组长度为32，hash为17的应该放置在数组的第17个位置上，也就是下标为16，那么下标为16已经属于高位了，低位是[0-15]，高位是[16-31]
                            else {  // 以下逻辑同PS4
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) { // 低位的元素组成的链表还是放置在原来的位置
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {  // 高位的元素组成的链表放置的位置只是在原有位置上偏移了老数组的长度个位置。
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead; // 例：hash为 17 在老数组放置在0下标，在新数组放置在16下标；    hash为 18 在老数组放置在1下标，在新数组放置在17下标；                   
                        }
                    }
                }
            }
        }
        return newTab; // 返回新数组
    }
```

