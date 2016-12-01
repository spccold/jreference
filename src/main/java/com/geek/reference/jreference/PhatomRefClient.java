package com.geek.reference.jreference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

public class PhatomRefClient {

    public static void main(String[] args) {

        Person p = new Person();
        p.setId(1);
        p.setName("zhaosi");
        p.setAge(17);
        System.out.println(p);
        ReferenceQueue<Person> queue = new ReferenceQueue<Person>();
        PhantomReference<Person> phaRef = new PhantomReference<Person>(p, queue);

        p = null;
        System.gc();
        
        Person  reusedP= null;
        while (true) {
            Object o = queue.poll();
            if (o != null) {
                try {
                    Field rereferent = Reference.class
                            .getDeclaredField("referent");
                    rereferent.setAccessible(true);
                    Person result = (Person)rereferent.get(o);
                    System.out.println("system gc will kill person:" +result.getName());
                    System.out.println(result);
                    reusedP = result;
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        System.gc();
        try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        //对象从虚引用又变成了强引用了
        System.out.println(reusedP);
    }
    
    static final class Person{
    	private int id;
    	private int age;
    	private String name;
		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(int id) {
			this.id = id;
		}
		/**
		 * @return the age
		 */
		public int getAge() {
			return age;
		}
		/**
		 * @param age the age to set
		 */
		public void setAge(int age) {
			this.age = age;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
    }
}