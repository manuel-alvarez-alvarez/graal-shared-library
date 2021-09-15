package org.example;

import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.c.function.CEntryPoint;

@SuppressWarnings("unused")
public class ExampleLibrary {

    @CEntryPoint(name = "Java_org_example_ExampleLibrary_entry")
    public static void entry(final IsolateThread thread) {
    }

    public static void main(final String[] args) {
    }
}
