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

