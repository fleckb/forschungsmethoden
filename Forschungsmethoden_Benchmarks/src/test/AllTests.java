package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	test.benchmark.BenchmarkerTest.class,
	test.benchmark.NaiveFinderTest.class
})
public final class AllTests {}
