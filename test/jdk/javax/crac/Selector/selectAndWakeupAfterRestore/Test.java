// Copyright 2013-2019 Azul Systems, Inc.  All Rights Reserved.
// DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
//
// This code is free software; you can redistribute it and/or modify it under
// the terms of the GNU General Public License version 2 only, as published by
// the Free Software Foundation.
//
// This code is distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
// A PARTICULAR PURPOSE.  See the GNU General Public License version 2 for more
// details (a copy is included in the LICENSE file that accompanied this code).
//
// You should have received a copy of the GNU General Public License version 2
// along with this work; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
//
// Please contact Azul Systems, 385 Moffett Park Drive, Suite 115, Sunnyvale,
// CA 94089 USA or visit www.azul.com if you need additional information or
// have any questions.


import java.nio.channels.Selector;

public class Test {

    private static void selectAndWakeup(Selector selector) throws java.io.IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(7000);
                    System.out.println(">> waking up");
                    selector.wakeup();
                } catch (InterruptedException ie) { throw new RuntimeException(ie); }
            }
        }).start();

        System.out.println(">> selecting");
        selector.select();
    }

    public static void main(String args[]) throws Exception {

        Selector selector = Selector.open();

        selectAndWakeup(selector); // just in case

        javax.crac.Core.tryCheckpointRestore();

        selectAndWakeup(selector);

        selector.close();
    }
}
