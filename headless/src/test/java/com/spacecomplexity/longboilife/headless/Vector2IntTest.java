package com.spacecomplexity.longboilife.headless;

import static org.junit.jupiter.api.Assertions.*;

import com.spacecomplexity.longboilife.game.utils.Vector2Int;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Unit tests for the Vector2Int class. TEST REF: 17 */
class Vector2IntTest {

  @Test
  @DisplayName("Test Vector2Int default constructor has correct values")
  void testDefaultConstructor() {
    Vector2Int v = new Vector2Int();
    assertEquals(0, v.x, "Default x should be 0");
    assertEquals(0, v.y, "Default y should be 0");
  }

  @Test
  @DisplayName("Test Vector2Int constructor with parameters saves the parameters as correct values")
  void testParamConstructor() {
    Vector2Int v = new Vector2Int(3, 7);
    assertEquals(3, v.x, "x should match the passed value");
    assertEquals(7, v.y, "y should match the passed value");
  }

  @Test
  @DisplayName("Test Vector2Int correctly returns true when equaling itself")
  void testEqualsWithSameObject() {
    Vector2Int v = new Vector2Int(1, 2);
    assertTrue(v.equals(v), "An object should equal itself");
  }

  @Test
  @DisplayName("Test Vector2Int correctly returns false when equaling null")
  void testEqualsWithNull() {
    Vector2Int v = new Vector2Int(1, 2);
    assertFalse(v.equals(null), "An object should not be equal to null");
  }

  @Test
  @DisplayName("Test Vector2Int correctly returns false when equaling some different class")
  void testEqualsWithDifferentClass() {
    Vector2Int v = new Vector2Int(1, 2);
    // Using a different type (e.g., String) to ensure equals returns false
    assertFalse(v.equals("not a vector"), "Objects of different class should not be equal");
  }

  @Test
  @DisplayName(
      "Test Vector2Int correctly returns false when equaling another Vector2Int but with different values")
  void testEqualsWithDifferentValues() {
    Vector2Int v1 = new Vector2Int(1, 2);
    Vector2Int v2 = new Vector2Int(2, 3);
    assertNotEquals(v1, v2, "Vectors with different values should not be equal");
  }

  @Test
  @DisplayName(
      "Test Vector2Int correctly returns true when equaling another Vector2Int but with the same values")
  void testEqualsWithSameValues() {
    Vector2Int v1 = new Vector2Int(3, 4);
    Vector2Int v2 = new Vector2Int(3, 4);
    assertEquals(v1, v2, "Vectors with identical values should be equal");
  }

  @Test
  @DisplayName("Test Vector2Int correctly has the same hash code for a vector with same values")
  void testHashCodeSameValues() {
    Vector2Int v1 = new Vector2Int(5, 5);
    Vector2Int v2 = new Vector2Int(5, 5);
    assertEquals(v1.hashCode(), v2.hashCode(), "Equal objects should have the same hash code");
  }

  @Test
  @DisplayName(
      "Test Vector2Int correctly does not have the same hash code for a vector with different values")
  void testHashCodeDifferentValues() {
    Vector2Int v1 = new Vector2Int(5, 5);
    Vector2Int v2 = new Vector2Int(5, 6);
    assertNotEquals(
        v1.hashCode(), v2.hashCode(), "Different objects may have different hash codes");
  }

  @Test
  @DisplayName(
      "Test Vector2Int correctly adds to form new vector with addition of x and y parts separately")
  void testAdd() {
    Vector2Int v1 = new Vector2Int(1, 2);
    Vector2Int v2 = new Vector2Int(3, 4);
    Vector2Int result = v1.add(v2);
    assertEquals(new Vector2Int(4, 6), result, "Addition should yield (1+3, 2+4) = (4, 6)");
  }

  @Test
  @DisplayName(
      "Test Vector2Int correctly subtracts to form new vector with subtraction of x and y parts separately")
  void testSubtract() {
    Vector2Int v1 = new Vector2Int(5, 7);
    Vector2Int v2 = new Vector2Int(2, 3);
    Vector2Int result = v1.subtract(v2);
    assertEquals(new Vector2Int(3, 4), result, "Subtraction should yield (5-2, 7-3) = (3, 4)");
  }

  @Test
  @DisplayName("Vector2Int.mag2() correctly calculates the squared magnitude of a vector")
  void testMag2() {
    Vector2Int v = new Vector2Int(3, 4);
    int expected = 3 * 3 + 4 * 4;
    assertEquals(expected, v.mag2(), "Magnitude squared should be x² + y²");
  }

  @Test
  @DisplayName("Vector2Int.mag() correctly returns 0 for the zero vector")
  void testMagForZeroVector() {
    Vector2Int v = new Vector2Int();
    assertEquals(0.0f, v.mag(), 1e-6, "Magnitude of the zero vector should be 0");
  }

  @Test
  @DisplayName("Vector2Int.mag() correctly calculates the magnitude of a vector")
  void testMag() {
    Vector2Int v = new Vector2Int(3, 4);
    float expected = (float) Math.sqrt(3 * 3 + 4 * 4);
    assertEquals(expected, v.mag(), 1e-6, "Magnitude should be sqrt(x² + y²)");
  }
}
