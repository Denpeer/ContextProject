package com.funkydonkies.w4v3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.funkydonkies.controllers.PhysicsControllerTest;
import com.funkydonkies.gamestates.GameInputStateTest;

/**
 * Run all tests for EclEmma.
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ AppTest.class, BallTest.class, GameInputStateTest.class,
		PhysicsControllerTest.class, SplineCurveTest.class })
public class AllTests {

}
