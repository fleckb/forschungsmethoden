package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	test.benchmark.BenchmarkerTest.class,
	test.benchmark.NaiveFinderTest.class,
	test.benchmark.StopWatchTest.class,
	test.benchmark.ResourceMonitorTest.class
})
public final class AllTests {}
