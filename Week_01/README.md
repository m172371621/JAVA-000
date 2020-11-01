   Serial GC(串行GC)
   Serial GC 对年轻代使用mark_copy(标记-复制) 算法，
             对老年代mark_sweep_compact(标记-清理-整理)算法，两者都会出发STW,停止所有
             的应用线程。
   java -XX:UseSerialGC

   2015-05-26T14:45:37.987-0200: 
           151.126: [GC (Allocation Failure) 
           151.126: [DefNew: 629119K->69888K(629120K), 0.0584157 secs] 
           1619346K->1273247K(2027264K), 0.0585007 secs]
       [Times: user=0.06 sys=0.00, real=0.06 secs]
   2015-05-26T14:45:59.690-0200: 
           172.829: [GC (Allocation Failure) 
           172.829: [DefNew: 629120K->629120K(629120K), 0.0000372 secs]
           172.829: [Tenured: 1203359K->755802K(1398144K), 0.1855567 secs] 
           1832479K->755802K(2027264K), 
           [Metaspace: 6741K->6741K(1056768K)], 0.1856954 secs] 
       [Times: user=0.18 sys=0.00, real=0.18 secs]

       Minor GC(小型GC)
       2015-05-26T14:45:37.987-0200 – GC事件开始的时间. 其中-0200表示西二时区,而中国所在的东8区为 +0800。
       151.126 – GC事件开始时,相对于JVM启动时的间隔时间,单位是秒。
       GC – 用来区分 Minor GC 还是 Full GC 的标志。GC表明这是一次小型GC(Minor GC)
       Allocation Failure – 触发 GC 的原因。本次GC事件, 是由于年轻代中没有空间来存放新的数据结构引起的。
       DefNew – 垃圾收集器的名称。这个神秘的名字表示的是在年轻代中使用的: 单线程, 标记-复制(mark-copy), 全线暂停(STW) 垃圾收集器。
       629119K->69888K – 在垃圾收集之前和之后年轻代的使用量。
       (629120K) – 年轻代总的空间大小。
       1619346K->1273247K – 在垃圾收集之前和之后整个堆内存的使用情况。
       (2027264K) – 可用堆的总空间大小。
       0.0585007 secs – GC事件持续的时间,以秒为单位。
       [Times: user=0.06 sys=0.00, real=0.06 secs] – GC事件的持续时间, 通过三个部分来衡量:
       user – 在此次垃圾回收过程中, 所有 GC线程所消耗的CPU时间之和。
       sys – GC过程中中操作系统调用和系统等待事件所消耗的时间。
       real – 应用程序暂停的时间。因为串行垃圾收集器(Serial Garbage Collector)只使用单线程, 因此 real time 等于 user 和 system 时间的总和。

       Full GC(完全GC)
       2015-05-26T14:45:59.690-0200 – GC事件开始的时间. 其中-0200表示西二时区,而中国所在的东8区为 +0800。
       172.829 – GC事件开始时,相对于JVM启动时的间隔时间,单位是秒。
       [DefNew: 629120K->629120K(629120K), 0.0000372 secs – 与上面的示例类似, 因为内存分配失败,在年轻代中发生了一次 minor GC。此次GC同样使用的是 DefNew 收集器, 让年轻代的使用量从 629120K 降为 0。注意,JVM对此次GC的报告有些问题,误将年轻代认为是完全填满的。此次垃圾收集消耗了 0.0000372秒。
       Tenured – 用于清理老年代空间的垃圾收集器名称。Tenured 表明使用的是单线程的全线暂停垃圾收集器, 收集算法为 标记-清除-整理(mark-sweep-compact )。
       1203359K->755802K – 在垃圾收集之前和之后老年代的使用量。
       (1398144K) – 老年代的总空间大小。
       0.1855567 secs – 清理老年代所花的时间。
       1832479K->755802K – 在垃圾收集之前和之后,整个堆内存的使用情况。
       (2027264K) – 可用堆的总空间大小。
       [Metaspace: 6741K->6741K(1056768K)] – 关于 Metaspace 空间, 同样的信息。可以看出, 此次GC过程中 Metaspace 中没有收集到任何垃圾。
       [Times: user=0.18 sys=0.00, real=0.18 secs] – GC事件的持续时间, 通过三个部分来衡量:
       user – 在此次垃圾回收过程中, 所有 GC线程所消耗的CPU时间之和。
       sys – GC过程中中操作系统调用和系统等待事件所消耗的时间。
       real – 应用程序暂停的时间。因为串行垃圾收集器(Serial Garbage Collector)只使用单线程, 因此 real time 等于 user 和 system 时间的总和。
   #------------------------------------------------------------------------------
    Parallel GC (并行GC)
    java -XX:UseParallelGC xxxxx

    [GC (Allocation Failure) [PSYoungGen: 131333K->21474K(153088K)] 131333K->44662K(502784K), 0.0151488 secs] [Times: user=0.01 sys=0.02, real=0.02 secs] 
	[GC (Allocation Failure) [PSYoungGen: 153058K->21493K(153088K)] 176246K->85337K(502784K), 0.0206689 secs] [Times: user=0.01 sys=0.04, real=0.02 secs] 
	[GC (Allocation Failure) [PSYoungGen: 153077K->21491K(153088K)] 216921K->125413K(502784K), 0.0177096 secs] [Times: user=0.02 sys=0.03, real=0.02 secs] 
	[GC (Allocation Failure) [PSYoungGen: 153065K->21490K(153088K)] 256986K->162809K(502784K), 0.0169118 secs] [Times: user=0.03 sys=0.02, real=0.02 secs] 
	[GC (Allocation Failure) [PSYoungGen: 153048K->21497K(153088K)] 294367K->206913K(502784K), 0.0183092 secs] [Times: user=0.04 sys=0.02, real=0.02 secs] 
	[GC (Allocation Failure) [PSYoungGen: 153081K->21502K(80384K)] 338497K->248861K(430080K), 0.0177171 secs] [Times: user=0.04 sys=0.00, real=0.02 secs] 
	[GC (Allocation Failure) [PSYoungGen: 80359K->37739K(116736K)] 307719K->268160K(466432K), 0.0097211 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
	[GC (Allocation Failure) [PSYoungGen: 96619K->46808K(116736K)] 327040K->285069K(466432K), 0.0154968 secs] [Times: user=0.03 sys=0.01, real=0.02 secs] 
	[GC (Allocation Failure) [PSYoungGen: 105408K->57365K(116736K)] 343669K->301847K(466432K), 0.0200571 secs] [Times: user=0.04 sys=0.00, real=0.02 secs] 
	[GC (Allocation Failure) [PSYoungGen: 116245K->36181K(116736K)] 360727K->316534K(466432K), 0.0190913 secs] [Times: user=0.04 sys=0.01, real=0.02 secs] 
	[GC (Allocation Failure) [PSYoungGen: 95061K->19432K(116736K)] 375414K->334197K(466432K), 0.0258610 secs] [Times: user=0.05 sys=0.02, real=0.03 secs] 
	[Full GC (Ergonomics) [PSYoungGen: 19432K->0K(116736K)] [ParOldGen: 314764K->238870K(349696K)] 334197K->238870K(466432K), [Metaspace: 2548K->2548K(1056768K)], 0.0653520 secs] [Times: user=0.13 sys=0.01, real=0.06 secs] 
	[GC (Allocation Failure) [PSYoungGen: 58261K->23608K(116736K)] 297131K->262479K(466432K), 0.0053901 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
	[GC (Allocation Failure) [PSYoungGen: 82488K->19550K(116736K)] 321359K->279344K(466432K), 0.0120874 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
	[GC (Allocation Failure) [PSYoungGen: 78212K->24288K(116736K)] 338006K->302665K(466432K), 0.0098548 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
	[GC (Allocation Failure) [PSYoungGen: 83131K->23042K(116736K)] 361508K->325079K(466432K), 0.0129958 secs] [Times: user=0.03 sys=0.00, real=0.02 secs] 
	[Full GC (Ergonomics) [PSYoungGen: 23042K->0K(116736K)] [ParOldGen: 302037K->266640K(349696K)] 325079K->266640K(466432K), [Metaspace: 2548K->2548K(1056768K)], 0.0504397 secs] [Times: user=0.12 sys=0.01, real=0.05 secs] 
	[GC (Allocation Failure) [PSYoungGen: 58880K->22934K(116736K)] 325520K->289575K(466432K), 0.0108284 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
	[GC (Allocation Failure) [PSYoungGen: 81814K->21224K(116736K)] 348455K->309456K(466432K), 0.0173877 secs] [Times: user=0.04 sys=0.00, real=0.02 secs] 
	[GC (Allocation Failure) [PSYoungGen: 79977K->20437K(116736K)] 368209K->328624K(466432K), 0.0090124 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
	[Full GC (Ergonomics) [PSYoungGen: 20437K->0K(116736K)] [ParOldGen: 308186K->285611K(349696K)] 328624K->285611K(466432K), [Metaspace: 2548K->2548K(1056768K)], 0.0588946 secs] [Times: user=0.13 sys=0.00, real=0.06 secs] 
	[GC (Allocation Failure) [PSYoungGen: 58880K->18840K(116736K)] 344491K->304451K(466432K), 0.0057809 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
	[GC (Allocation Failure) [PSYoungGen: 77720K->19142K(116736K)] 363331K->322509K(466432K), 0.0137975 secs] [Times: user=0.02 sys=0.01, real=0.01 secs] 
	[GC (Allocation Failure) [PSYoungGen: 78015K->23442K(116736K)] 381382K->344755K(466432K), 0.0227603 secs] [Times: user=0.05 sys=0.00, real=0.02 secs] 
	[Full GC (Ergonomics) [PSYoungGen: 23442K->0K(116736K)] [ParOldGen: 321313K->297293K(349696K)] 344755K->297293K(466432K), [Metaspace: 2548K->2548K(1056768K)], 0.0622566 secs] [Times: user=0.14 sys=0.01, real=0.06 secs] 
	[GC (Allocation Failure) [PSYoungGen: 58880K->22500K(116736K)] 356173K->319793K(466432K), 0.0129621 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 

        Minor GC(小型GC)
            2015-05-26T14:27:40.915-0200: 116.115: [GC (Allocation Failure) 
                [PSYoungGen: 2694440K->1305132K(2796544K)] 
            9556775K->8438926K(11185152K)
            , 0.2406675 secs] 
            [Times: user=1.77 sys=0.01, real=0.24 secs]
                2015-05-26T14:27:41.155-0200: 116.356: [Full GC (Ergonomics) 
                [PSYoungGen: 1305132K->0K(2796544K)] 
                [ParOldGen: 7133794K->6597672K(8388608K)] 8438926K->6597672K(11185152K), 
                [Metaspace: 6745K->6745K(1056768K)], 0.9158801 secs]
            [Times: user=4.49 sys=0.64, real=0.92 secs]
    
            2015-05-26T14:27:40.915-0200 – GC事件开始的时间. 其中-0200表示西二时区,而中国所在的东8区为 +0800。
            116.115 – GC事件开始时,相对于JVM启动时的间隔时间,单位是秒。
            GC – 用来区分 Minor GC 还是 Full GC 的标志。GC表明这是一次小型GC(Minor GC)
            Allocation Failure – 触发垃圾收集的原因。本次GC事件, 是由于年轻代中没有适当的空间存放新的数据结构引起的。
            PSYoungGen – 垃圾收集器的名称。这个名字表示的是在年轻代中使用的: 并行的 标记-复制(mark-copy), 全线暂停(STW) 垃圾收集器。
            2694440K->1305132K – 在垃圾收集之前和之后的年轻代使用量。
            (2796544K) – 年轻代的总大小。
            9556775K->8438926K – 在垃圾收集之前和之后整个堆内存的使用量。
            (11185152K) – 可用堆的总大小。
            0.2406675 secs – GC事件持续的时间,以秒为单位。
            [Times: user=1.77 sys=0.01, real=0.24 secs] – GC事件的持续时间, 
            通过三个部分来衡量:
            user – 在此次垃圾回收过程中, 由GC线程所消耗的总的CPU时间。
            sys – GC过程中中操作系统调用和系统等待事件所消耗的时间。
            real – 应用程序暂停的时间。在 Parallel GC 中, 这个数字约等于: (user time + system time)/GC线程数。 这里使用了8个线程。 请注意,总有一定比例的处理过程是不能并行进行的。
    
            Full GC(完全GC)
            2015-05-26T14:27:41.155-0200 : 116.356 : [Full GC (Ergonomics)
            [PSYoungGen: 1305132K->0K(2796544K)] [ParOldGen :7133794K->6597672K
            (8388608K)] 8438926K->6597672K (11185152K),
            [Metaspace: 6745K->6745K(1056768K)], 0.9158801 secs,
            [Times: user=4.49 sys=0.64, real=0.92 secs]
            2015-05-26T14:27:41.155-0200 – GC事件开始的时间. 其中-0200表示西二时区,而中国所在的东8区为 +0800。
            116.356 – GC事件开始时,相对于JVM启动时的间隔时间,单位是秒。 我们可以看到, 此次事件在前一次 MinorGC完成之后立刻就开始了。
            Full GC – 用来表示此次是 Full GC 的标志。Full GC表明本次清理的是年轻代和老年代。
            Ergonomics – 触发垃圾收集的原因。Ergonomics 表示JVM内部环境认为此时可以进行一次垃圾收集。
            [PSYoungGen: 1305132K->0K(2796544K)] – 和上面的示例一样, 清理年轻代的垃圾收集器是名为 “PSYoungGen” 的STW收集器, 采用标记-复制(mark-copy)算法。 年轻代使用量从 1305132K 变为 0, 一般 Full GC 的结果都是这样。
            ParOldGen – 用于清理老年代空间的垃圾收集器类型。在这里使用的是名为 ParOldGen 的垃圾收集器, 这是一款并行 STW垃圾收集器, 算法为 标记-清除-整理(mark-sweep-compact)。
            7133794K->6597672K – 在垃圾收集之前和之后老年代内存的使用情况。
            (8388608K) – 老年代的总空间大小。
            8438926K->6597672K – 在垃圾收集之前和之后堆内存的使用情况。
            (11185152K) – 可用堆内存的总容量。
            [Metaspace: 6745K->6745K(1056768K)] – 类似的信息,关于 Metaspace 空间的。可以看出, 在GC事件中 Metaspace 里面没有回收任何对象。
            0.9158801 secs – GC事件持续的时间,以秒为单位。
            [Times: user=4.49 sys=0.64, real=0.92 secs] – GC事件的持续时间, 通过三个部分来衡量:
            user – 在此次垃圾回收过程中, 由GC线程所消耗的总的CPU时间。
            sys – GC过程中中操作系统调用和系统等待事件所消耗的时间。
            real – 应用程序暂停的时间。在 Parallel GC 中, 这个数字约等于: (user time + system time)/GC线程数。 这里使用了8个线程。 请注意,总有一定比例的处理过程是不能并行进行的。
#------------------------------------------------------------------------------
    Concurrent Mark and  Sweep  CMS GC   (并发标记和清理)
    java -XX:UseConcMarkSweepGC xxxxx
    
    2015-05-26T16:23:07.219-0200: 64.322: [GC (Allocation Failure) 64.322: 
                [ParNew: 613404K->68068K(613440K), 0.1020465 secs]
                10885349K->10880154K(12514816K), 0.1021309 secs]
            [Times: user=0.78 sys=0.01, real=0.11 secs]
    2015-05-26T16:23:07.321-0200: 64.425: [GC (CMS Initial Mark) 
                [1 CMS-initial-mark: 10812086K(11901376K)] 
                10887844K(12514816K), 0.0001997 secs] 
            [Times: user=0.00 sys=0.00, real=0.00 secs]
    2015-05-26T16:23:07.321-0200: 64.425: [CMS-concurrent-mark-start]
    2015-05-26T16:23:07.357-0200: 64.460: [CMS-concurrent-mark: 0.035/0.035 secs] 
            [Times: user=0.07 sys=0.00, real=0.03 secs]
    2015-05-26T16:23:07.357-0200: 64.460: [CMS-concurrent-preclean-start]
    2015-05-26T16:23:07.373-0200: 64.476: [CMS-concurrent-preclean: 0.016/0.016 secs] 
            [Times: user=0.02 sys=0.00, real=0.02 secs]
    2015-05-26T16:23:07.373-0200: 64.476: [CMS-concurrent-abortable-preclean-start]
    2015-05-26T16:23:08.446-0200: 65.550: [CMS-concurrent-abortable-preclean: 0.167/1.074 secs] 
            [Times: user=0.20 sys=0.00, real=1.07 secs]
    2015-05-26T16:23:08.447-0200: 65.550: [GC (CMS Final Remark) 
                [YG occupancy: 387920 K (613440 K)]
                65.550: [Rescan (parallel) , 0.0085125 secs]
                65.559: [weak refs processing, 0.0000243 secs]
                65.559: [class unloading, 0.0013120 secs]
                65.560: [scrub symbol table, 0.0008345 secs]
                65.561: [scrub string table, 0.0001759 secs]
                [1 CMS-remark: 10812086K(11901376K)] 
                11200006K(12514816K), 0.0110730 secs] 
            [Times: user=0.06 sys=0.00, real=0.01 secs]
    2015-05-26T16:23:08.458-0200: 65.561: [CMS-concurrent-sweep-start]
    2015-05-26T16:23:08.485-0200: 65.588: [CMS-concurrent-sweep: 0.027/0.027 secs] 
            [Times: user=0.03 sys=0.00, real=0.03 secs]
    2015-05-26T16:23:08.485-0200: 65.589: [CMS-concurrent-reset-start]
    2015-05-26T16:23:08.497-0200: 65.601: [CMS-concurrent-reset: 0.012/0.012 secs] 
            [Times: user=0.01 sys=0.00, real=0.01 secs]
            
            有6个阶段：
            		1.CMS-initial-mark 						(初始化标记, 此阶段标记GC Roots, 以及年轻代存活的对象. 需要STW)
            		2.CMS-concurrent-mark 					(并发标记, 遍历老年代所有存活的对象. “并发阶段”, 所以是与业务线程同时运行. 不需要STW)
            		3.CMS-concurrent-preclean   			(并发预清理)
            		4.CMS-concurrent-abortable-preclean  	(并发可取消预清理)
            		5.CMS-Final-Remark                      (最终标记, 次阶段需要STW，因为上一个阶段preclean是并发的, 有可能对象引用发生变化)
            			YG occupancy   xxxx(xxxxx)		
            			Rescan (parallel),xxxxxx secs 	
            			weak ref processing xxxxx  secs	
            			[YG occupancy: 387920 K (613440 K)]						当前年轻代的使用量和总容量。消耗时间。  
        				65.550: [Rescan (parallel) , 0.0085125 secs]			重新扫描,存活的对象，是并行的。 消耗时间。  65.550开始时间，0.0085125 secs持续时间
        				65.559: [weak refs processing, 0.0000243 secs]			处理弱引用
        				65.559: [class unloading, 0.0013120 secs]				卸载不使用的类。
        				65.560: [scrub symbol table, 0.0008345 secs]			擦除符号表
        				65.561: [scrub string table, 0.0001759 secs]			擦除字符串表
        				[1 CMS-remark: 10812086K(11901376K)]					老年代使用容量和老年代总容量
        				11200006K(12514816K), 0.0110730 secs] 					堆的使用量和总容量 / 此阶段的持续时间
        			6.CMS-concurrent-sweep: 0.027/0.027 secs (并发清除, 不需要STW. 清除未被标记，不再使用的对象. 运行时间/实际时间)
        			7.CMS-concurrent-reset   (并发重置, 重置CMS算法的内部数据结构)