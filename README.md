# Implement EventStore

In this challenge, you will create a class that implements the `EventStore` 
interface.
 
```java
public interface EventStore {
    void insert(Event event);

    void removeAll(String type);

    EventIterator query(String type, long startTime, long endTime);
}
```

Your implementation should store events in memory, using any data structures 
you see fit for the task. The required behavior for the interface is described in the
provided code javadocs, please see `EventStore` and `EventIterator`
interfaces inside the `src/main/java` directory.
 
The implementation should be correct, fast, memory-efficient, and thread-safe. 
You may consider that insertions, deletions, queries, and iterations 
will happen frequently and concurrently. This will be a system hotspot. Optimize at will. 

We expect you to:
* Write tests;
* Provide some evidence of thread-safety;
* Justify design choices, arguing about costs 
  and benefits involved. You may write those as comments 
  inline or, if you wish, provide a separate document 
  summarizing those choices;
* Write all code and documentation in english.
  
You may use external libraries, but their use has to be 
properly justified as well.
 
This challenge is intentionally simple, we expect a simple,
elegant, and polished solution. There is no unique solution to this challenge. 
The intent is to evaluate candidate's coding proficiency and familiarity with 
tools and best practices.
