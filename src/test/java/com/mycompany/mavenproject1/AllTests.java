package com.mycompany.mavenproject1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.funkydonkies.w4v3.AppTest;
import com.funkydonkies.w4v3.SplineCurveTest;

/**
 * Run all tests for EclEmma.
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ AppTest.class, BallTest.class, GameInputStateTest.class,
		PhysicsControllerTest.class, SplineCurveTest.class })
public class AllTests {

}
