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

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 
 * @author spccold
 * @version $Id: WeakReferenceApp.java, v 0.1 2016年8月27日 下午2:40:57 jileng Exp $
 */
public class WeakReferenceApp {
    public static void main(String[] args) throws Exception{
        Object strongRef = new Object();
        ReferenceQueue<Object> refQueue = new ReferenceQueue<Object>();
        WeakReference<Object> weakReference = new WeakReference<Object>(strongRef, refQueue);
        System.out.println(weakReference.get() == strongRef);
        System.out.println(!weakReference.isEnqueued());

        strongRef = null;
        System.gc();
        System.out.println(weakReference.get() == null);
        Thread.sleep(1000);
        System.out.println(weakReference.isEnqueued());
        System.out.println(weakReference == refQueue.poll());
        System.out.println(weakReference.get());
    }
}
