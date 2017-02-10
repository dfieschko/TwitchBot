
/**
 * Your basic Bag class that doesn't use LinkedLists because I forgot how they work.
 * @author Darius
 * @param <E> is generic type
 */
public class Bag<E> {

    private E[] list;
    private int count;
    private static final int START_SIZE = 100;
    private static final double GROW_RATIO = 0.5;

    /**
     * Constructor that makes an empty bag.
     */
    public Bag() 
    {
        this.list = (E[]) new Object[START_SIZE];
    }
    
    /**
     * Constructor that makes an empty bag.
     * @param size the size of the bag
     */
    public Bag(int size) 
    {
        this.list = (E[]) new Object[size];
    }
    
    /**
     * Constructor that makes a bag out of an existing array.
     * @param list the input array
     */
    public Bag(E[] list)
    {
        this.list = list;
    }
    
    /**
     * @return the array in the bag
     */
    public E[] getArray()
    {
        return list;
    }
    
    /**
     * Adds object to the Bag.
     * @param obj the object being added
     */
    public void add(E obj)
    {
        if(count >= list.length - 1)
            grow();
        list[count] = obj;
        count++;
    }
    
    /**
     * Removes an item from the Bag and returns it.
     * @param index the location of the item being removed
     * @return the item removed
     * @throws IllegalArgumentException if you fuck up
     */
    public E remove(int index) throws IllegalArgumentException
    {
        E e = list[index];
        for(int i = index; i < count; i++)
            list[i] = list[i + 1];
        return e;
    }
    
    /**
     * Returns an item from the Bag.
     * @param index the location of the item
     * @return the item
     * @throws IllegalArgumentException if you fuck it up
     */
    public E get(int index) throws IllegalArgumentException
    {
        return list[index];
    }
    
    /**
     * Grows Bag by default ratio.
     */
    public void grow()
    {
        grow((int)(list.length * GROW_RATIO));
    }
    
    /**
     * Grows bag by specified amount.
     * @param size the amount the bag grows by
     */
    public void grow(int size)
    {
        E[] temp = (E[]) new Object[(int) (list.length + size)];
        System.arraycopy(list, 0, temp, 0, list.length);
        list = temp;
    }
    
    /**
     * @return number of items in the bag.
     */
    public int getCount()
    {
        return count;
    }
    
    /**
     * Returns the size (current maximum number of elements) of the Bag.
     * @return the size of the bag
     */
    public int getSize()
    {
        return list.length;
    }
    
    /**
     * Sets specified location to specified value.
     * Don't use this unless you're me.
     * @param index the location
     * @param value the value
     */
    public void set(int index, E value) {
        if(list[index] == null)
            count++; //sloppy
        this.list[index] = value;
    }
    
    /**
     * Finds index of Viewer that has a name matching the name being searched for.
     * @param name the name of the viewer being searched for
     * @return index of viewer being searched for, or -1 if it is not found.
     */
    public int findViewer(String name)
    {
        try
        {
            for(int i = 0; i < count; i++)
            {
                if(((Viewer) list[i]).getNick().equals(name))
                    return i;
            }
        }catch(Exception e)
        {
            return -1;
        }
        return -1;
    }
}
