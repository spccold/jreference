/**
 *
 *	Copyright 2016-2016 spccold
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *   	http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 *
 */
package com.geek.reference.jreference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 
 * @author spccold
 * @version $Id: PhantomReferenceApp.java, v 0.1 2016年8月27日 下午1:46:17 jileng Exp $
 */
public class PhantomReferenceApp {
    public static void main(String[] args) throws Exception{
        Object strongRef = new Object();
        ReferenceQueue<Object> refQueue = new ReferenceQueue<Object>();
        PhantomReference<Object> phantomReference = new PhantomReference<Object>(strongRef, refQueue);
           
        //always null
        System.out.println(null == refQueue.poll());;
        
        strongRef = null;
        System.gc();

        Thread.sleep(1000);
        System.out.println(phantomReference.isEnqueued());
        System.out.println(phantomReference == refQueue.remove());
    }
}
