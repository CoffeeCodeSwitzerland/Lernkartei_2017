package test;

import static org.junit.Assert.*;

import org.junit.Test;

import debug.Supervisor;

public class SupervisorTest {

	public static void myTest () {
		Supervisor.setSupervisorExitState(3);
		assertEquals(3,Supervisor.getSupervisorExitState());

		Supervisor.setSupervisorExitState(99);
		assertEquals(4,Supervisor.getSupervisorExitState());

		Supervisor.setSupervisorExitState(-1);
		assertEquals(0,Supervisor.getSupervisorExitState());

		Supervisor.setSupervisorExitState(0);
		assertEquals(0,Supervisor.getSupervisorExitState());

		Supervisor.setSupervisorShowState(4);
		assertEquals(4,Supervisor.getSupervisorShowState());

		Supervisor.setSupervisorShowState(98);
		assertEquals(4,Supervisor.getSupervisorShowState());

		Supervisor.setSupervisorShowState(-2);
		assertEquals(0,Supervisor.getSupervisorShowState());

		Supervisor.setSupervisorShowState(0);
		assertEquals(0,Supervisor.getSupervisorShowState());
	}

	@Test
	public void test() {
		myTest();
	}

}
