package com.geek.reference.jreference.example;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CleanUpExample {
     InputStream input;

    public CleanUpExample() {
        input = new ByteArrayInputStream("stream".getBytes());
//        CleanUpHelper.register(this, new CleanUpImpl(input));
        CleanUpHelper.register(this, new CleanUpImpl2(this));
    }

    static class CleanUpImpl implements CleanUp {
        private final InputStream input;

        public CleanUpImpl(InputStream input) {
            this.input = input;
        }

        public void cleanUp() {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Success!");
        }
    }

    static class CleanUpImpl2 implements CleanUp {
    	private final CleanUpExample example;
    	
    	public CleanUpImpl2(CleanUpExample example) {
    		this.example = example;
    	}
    	
    	public void cleanUp() {
    		try {
    			if (example.input != null) {
    				example.input.close();
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		
    		System.out.println("Success!");
    	}
    }
}