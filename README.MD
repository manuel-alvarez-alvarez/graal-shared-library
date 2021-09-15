# Graal shared library example

## Requisites
[Docker](https://docs.docker.com/engine/install/) and [docker-compose](https://docs.docker.com/compose/install/) are needed in order to build this proejct.

## Introduction
The goal of this project is to build a graal shared library that can be compressed by [UPX](https://upx.github.io/)

## Build

### Without PIC

If you want to build the project without PIC (Position Independent Code):

````shell
docker-compose run native-image
````

The linker invoked by the `native-image` command will complain about a `DT_TEXTREL`:

```
native-image_1      | ># /usr/bin/ld: graal-shared-library.o: warning: relocation in read-only section `.svm_heap'
native-image_1      | ># /usr/bin/ld: warning: creating a DT_TEXTREL in a shared object.
```

And later on the upx command will fail with:

```
native-image_1      | upx: graal-shared-library.so: CantPackException: DT_TEXTREL found; re-compile with -fPIC
```

### With PIC

If you want to run it with PIC support:

````shell
docker-compose run native-image-pic
````

The `native-image` command will fail with lots of errors like:

```
native-image-pic_1  | Fatal error:com.oracle.graal.pointsto.util.AnalysisError$ParsingError: Error encountered while parsing com.oracle.svm.core.code.IsolateEnterStub.JNIInvocationInterface_AttachCurrentThreadAsDaemon_d1dce4d04352e910ee4dbda42768c5c580f6b066
(com.oracle.svm.jni.nativeapi.JNIJavaVM, com.oracle.svm.jni.nativeapi.JNIEnvironmentPointer, com.oracle.svm.jni.nativeapi.JNIJavaVMAttachArgs)
native-image-pic_1  | Parsing context: <no parsing context available>
native-image-pic_1  |
native-image-pic_1  |   at com.oracle.graal.pointsto.util.AnalysisError.parsingError(AnalysisError.java:126)
native-image-pic_1  |   at com.oracle.graal.pointsto.flow.MethodTypeFlow.createTypeFlow(MethodTypeFlow.java:311)
native-image-pic_1  |   at com.oracle.graal.pointsto.flow.MethodTypeFlow.ensureTypeFlowCreated(MethodTypeFlow.java:282)
native-image-pic_1  |   at com.oracle.graal.pointsto.flow.MethodTypeFlow.addContext(MethodTypeFlow.java:103)
native-image-pic_1  |   at com.oracle.graal.pointsto.BigBang$1.run(BigBang.java:413)
native-image-pic_1  |   at com.oracle.graal.pointsto.util.CompletionExecutor.executeCommand(CompletionExecutor.java:188)
native-image-pic_1  |   at com.oracle.graal.pointsto.util.CompletionExecutor.lambda$executeService$0(CompletionExecutor.java:172)
native-image-pic_1  |   at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.exec(ForkJoinTask.java:1426)
native-image-pic_1  |   at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:290)
native-image-pic_1  |   at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1020)
native-image-pic_1  |   at java.base/java.util.concurrent.ForkJoinPool.scan(ForkJoinPool.java:1656)
native-image-pic_1  |   at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1594)
native-image-pic_1  |   at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:183)
native-image-pic_1  | Caused by: com.oracle.svm.core.util.VMError$HostedError: Unexpected control flow structure after CEntryPointLeaveNode. Disallowed node 35|Deopt in method com.oracle.svm.core.code.IsolateEnterStub.JNIInvocationInterface_AttachCurrentThre
adAsDaemon_d1dce4d04352e910ee4dbda42768c5c580f6b066(JNIJavaVM, JNIEnvironmentPointer, JNIJavaVMAttachArgs)
native-image-pic_1  |   at com.oracle.svm.core.util.VMError.shouldNotReachHere(VMError.java:68)
native-image-pic_1  |   at com.oracle.svm.core.graal.nodes.CEntryPointLeaveNode.collectReturns(CEntryPointLeaveNode.java:180)
native-image-pic_1  |   at com.oracle.svm.core.graal.nodes.CEntryPointLeaveNode.collectReturns(CEntryPointLeaveNode.java:173)
native-image-pic_1  |   at com.oracle.svm.core.graal.nodes.CEntryPointLeaveNode.anchorReturnValue(CEntryPointLeaveNode.java:144)
native-image-pic_1  |   at com.oracle.svm.core.graal.nodes.CEntryPointLeaveNode.simplify(CEntryPointLeaveNode.java:114)
native-image-pic_1  |   at jdk.internal.vm.compiler/org.graalvm.compiler.phases.common.CanonicalizerPhase$Instance.tryCanonicalize(CanonicalizerPhase.java:437)
native-image-pic_1  |   at jdk.internal.vm.compiler/org.graalvm.compiler.phases.common.CanonicalizerPhase$Instance.processNode(CanonicalizerPhase.java:348)
native-image-pic_1  |   at jdk.internal.vm.compiler/org.graalvm.compiler.phases.common.CanonicalizerPhase$Instance.processWorkSet(CanonicalizerPhase.java:325)
native-image-pic_1  |   at jdk.internal.vm.compiler/org.graalvm.compiler.phases.common.CanonicalizerPhase$Instance.run(CanonicalizerPhase.java:284)
native-image-pic_1  |   at jdk.internal.vm.compiler/org.graalvm.compiler.phases.common.CanonicalizerPhase.run(CanonicalizerPhase.java:194)
native-image-pic_1  |   at jdk.internal.vm.compiler/org.graalvm.compiler.phases.common.CanonicalizerPhase.run(CanonicalizerPhase.java:75)
native-image-pic_1  |   at jdk.internal.vm.compiler/org.graalvm.compiler.phases.BasePhase.apply(BasePhase.java:212)
native-image-pic_1  |   at jdk.internal.vm.compiler/org.graalvm.compiler.phases.BasePhase.apply(BasePhase.java:147)
native-image-pic_1  |   at com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.parse(MethodTypeFlowBuilder.java:182)
native-image-pic_1  |   at com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.apply(MethodTypeFlowBuilder.java:321)
native-image-pic_1  |   at com.oracle.graal.pointsto.flow.MethodTypeFlow.createTypeFlow(MethodTypeFlow.java:293)
native-image-pic_1  |   ... 11 more
```
