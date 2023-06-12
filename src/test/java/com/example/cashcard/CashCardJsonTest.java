package com.example.cashcard;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

/**
 * Test class for CashCard JSON serialization and deserialization.
 */
@JsonTest
public final class CashCardJsonTest {
  /**
   * JacksonTester for serializing and deserializing a single CashCard object.
   */
  @Autowired
  private JacksonTester<CashCard> json;
  /**
   * JacksonTester for serializing and deserializing a list of CashCard objects.
   */
  @Autowired
  private JacksonTester<CashCard[]> jsonList;
  /**
   * An array of CashCard objects used for testing.
   */
  private CashCard[] cashCards;

  @BeforeEach
  void setUp() {
    cashCards = Arrays.array(
        new CashCard(99L, 123.45, "sarah1"),
        new CashCard(100L, 1.00, "sarah1"),
        new CashCard(101L, 150.00, "sarah1"));
  }

  /**
   * Test for serializing a single CashCard object to JSON.
   *
   * @throws IOException if an error occurs during serialization
   */
  @Test
  public void cashCardSerializationTest() throws IOException {
    CashCard cashCard = cashCards[0];
    assertThat(json.write(cashCard)).isStrictlyEqualToJson("single.json");
    assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id")
      .isEqualTo(99);
    assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
    assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount")
      .isEqualTo(123.45);
  }

  /**
   * Test for deserializing a list of CashCard objects from JSON.
   *
   * @throws IOException if an error occurs during deserialization
   */
  @Test
  void cashCardListDeserializationTest() throws IOException {
    String expected = """
                     [
                     { "id": 99, "amount": 123.45, "owner": "sarah1" },
                     { "id": 100, "amount": 1.00, "owner": "sarah1" },
                     { "id": 101, "amount": 150.00, "owner": "sarah1" }
                     ]
                     """;
    CashCard[] expectedCashCards = jsonList.parse(expected).getObject();
    assertThat(expectedCashCards).isEqualTo(cashCards);
  }
}
