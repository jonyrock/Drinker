package drinker.utils;

public class Pair<T,E> {
    public T first;
    public E second;
    
    public Pair(T first, E second){
        this.first = first;
        this.second = second;
    }
    
    public boolean isEqual(Pair<T,E> pair){
        return (this.first == pair.first) && (this.second == pair.second);
    }
    
}
