package com.mycompany.mavenproject1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Run all tests for EclEmma.
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ AppTest.class, BallTest.class, GameInputStateTest.class,
		PhysicsControllerTest.class, SplineCurveTest.class })
public class AllTests {

}
