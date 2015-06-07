package com.funkydonkies.factories;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ObstacleFactoryTest.class, PenguinFactoryTest.class,
		TargetFactoryTest.class })
public class AllTests {

}
