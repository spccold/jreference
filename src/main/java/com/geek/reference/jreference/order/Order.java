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
package com.geek.reference.jreference.order;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 
 * @author spccold
 * @version $Id: Order.java, v 0.1 2016年8月27日 下午3:55:28 jileng Exp $
 */
public class Order {
    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
        Obj obj = new Obj();
        final ReferenceQueue<Obj> referenceQueue = new ReferenceQueue<Obj>();

        WeakReference<Obj> weakReference = new WeakReference<Obj>(obj, referenceQueue);
        PhantomReference<Obj> phantomReference = new PhantomReference<Obj>(obj, referenceQueue);
        Thread t = new Thread() {
            public void run() {
                /**
                 * when Obj not contain finalize method: 
                 *      class java.lang.ref.PhantomReference
                 *      class java.lang.ref.WeakReference
                 */
                while (true) {
                    try {
                        Object ref = referenceQueue.remove();
                        System.out.println(ref.getClass());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.setDaemon(true);
        t.start();
        obj = null;
        System.gc();
        Thread.sleep(1000);
    }
}
