package com.dooragami.dineindiet;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

  @Test
  public void useApp()
  {
    String usernameFromMain = MainActivity.STR_USER_NAME;

    String expectedUserName = "STR_USER_NAME";

    assertEquals(usernameFromMain, expectedUserName);
  }

//    @Test
//    public void shoudFail() throws Exception {
//        throw new Exception("Failure");
//    }
}