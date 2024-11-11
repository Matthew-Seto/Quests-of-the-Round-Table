Feature: Card Game

  Scenario: A1_Scenario
    Given A1 Scenario Starts
    When P1 draws quest with 4 stages
    Then P1 declines to sponsor
    And P2 decides to sponsor
    And P2 is the sponsor
    And P2 builds stage 1 with cards "F5,H10"
    And P2 builds stage 2 with cards "F15,S10"
    And P2 builds stage 3 with cards "F15,D5,B15"
    And P2 builds stage 4 with cards "F40,B15"
    And P2 built 4 valid stages with cards "F5,H10,B15,F15,S10,F15,D5,B15,F40"
    And P1 decides to participate in stage 1 and trims "F5"
    And P3 decides to participate in stage 1 and trims "F5"
    And P4 decides to participate in stage 1 and trims "F5"
    And Check participants are "P1,P3,P4"
    And P1 builds an attack with "D5,S10"
    And P3 builds an attack with "S10,D5"
    And P4 builds an attack with "D5,H10"
    And P1 resolves attack for stage 1
    And P3 resolves attack for stage 1
    And P4 resolves attack for stage 1
    And Check participants are "P1,P3,P4"
    And P1 decides to participate in stage 2 and trims " "
    And P3 decides to participate in stage 2 and trims " "
    And P4 decides to participate in stage 2 and trims " "
    And Check participants are "P1,P3,P4"
    And P1 builds an attack with "H10,S10"
    And P3 builds an attack with "B15,S10"
    And P4 builds an attack with "H10,B15"
    And P1 resolves attack for stage 2
    And P3 resolves attack for stage 2
    And P4 resolves attack for stage 2
    And Check participants are "P3,P4"
    And P3 decides to participate in stage 3 and trims " "
    And P4 decides to participate in stage 3 and trims " "
    And P3 builds an attack with "L20,H10,S10"
    And P4 builds an attack with "B15,S10,L20"
    And P3 resolves attack for stage 3
    And P4 resolves attack for stage 3
    And Check participants are "P3,P4"
    And P3 decides to participate in stage 3 and trims " "
    And P4 decides to participate in stage 3 and trims " "
    And P3 builds an attack with "B15,H10,L20"
    And P4 builds an attack with "D5,S10,L20,E30"
    And P3 resolves attack for stage 4
    And P4 resolves attack for stage 4
    And P1 should have 0 shields
    And P2 should have 0 shields
    And P3 should have 0 shields
    And P4 should have 4 shields
    And P2 handles the end of quest
    And P1 should have hand size 9
    And P2 should have hand size 12
    And P3 should have hand size 5
    And P4 should have hand size 4

  Scenario: 2winner_game_2winner_quest
    Given 2winner game 2winner quest starts
    When P1 draws quest with 4 stages
    Then P1 decides to sponsor
    And P1 is the sponsor
    And P1 builds stage 1 with cards "F10"
    And P1 builds stage 2 with cards "F5,S10"
    And P1 builds stage 3 with cards "F15,D5"
    And P1 builds stage 4 with cards "F15,B15"
    And P1 built 4 valid stages with cards "F10,F5,S10,F15,D5,F15,B15"
    And P2 decides to participate in stage 1 and trims "F5"
    And P3 decides to participate in stage 1 and trims "F5"
    And P4 decides to participate in stage 1 and trims "F5"
    And Check participants are "P2,P3,P4"
    And P2 builds an attack with "S10"
    And P3 builds an attack with "D5"
    And P4 builds an attack with "H10"
    And P2 resolves attack for stage 1
    And P3 resolves attack for stage 1
    And P4 resolves attack for stage 1
    And Check participants are "P2,P4"
    And P2 decides to participate in stage 2 and trims " "
    And P4 decides to participate in stage 2 and trims " "
    And P2 builds an attack with "D5,H10"
    And P4 builds an attack with "D5,S10"
    And P2 resolves attack for stage 2
    And P4 resolves attack for stage 2
    And Check participants are "P2,P4"
    And P2 decides to participate in stage 3 and trims " "
    And P4 decides to participate in stage 3 and trims " "
    And P2 builds an attack with "B15,L20"
    And P4 builds an attack with "H10,B15"
    And P2 resolves attack for stage 3
    And P4 resolves attack for stage 3
    And Check participants are "P2,P4"
    And P2 decides to participate in stage 4 and trims " "
    And P4 decides to participate in stage 4 and trims " "
    And P2 builds an attack with "E30"
    And P4 builds an attack with "E30"
    And P2 resolves attack for stage 4
    And P4 resolves attack for stage 4
    And P1 should have 0 shields
    And P2 should have 4 shields
    And P3 should have 0 shields
    And P4 should have 4 shields
    And P1 handles the end of quest
    And P2 draws quest with 3 stages
    And P2 declines to sponsor
    And P3 decides to sponsor
    And P3 is the sponsor
    And P3 builds stage 1 with cards "F5"
    And P3 builds stage 2 with cards "F5,S10"
    And P3 builds stage 3 with cards "F15,S10"
    And P3 built 3 valid stages with cards "F5,F5,S10,F15,S10"
    And P1 declines to participate
    And P2 decides to participate in stage 1 and trims " "
    And P4 decides to participate in stage 1 and trims " "
    And Check participants are "P2,P4"
    And P2 builds an attack with "D5"
    And P4 builds an attack with "D5"
    And P2 resolves attack for stage 1
    And P4 resolves attack for stage 1
    And Check participants are "P2,P4"
    And P2 decides to participate in stage 2 and trims " "
    And P4 decides to participate in stage 2 and trims " "
    And P2 builds an attack with "B15"
    And P4 builds an attack with "B15"
    And P2 resolves attack for stage 2
    And P4 resolves attack for stage 2
    And Check participants are "P2,P4"
    And P2 decides to participate in stage 3 and trims " "
    And P4 decides to participate in stage 3 and trims " "
    And P2 builds an attack with "E30"
    And P4 builds an attack with "E30"
    And P2 resolves attack for stage 3
    And P4 resolves attack for stage 3
    And P1 should have 0 shields
    And P2 should have 7 shields
    And P3 should have 0 shields
    And P4 should have 7 shields
    And P3 handles the end of quest
    And P2 is a winner
    And P4 is a winner

  Scenario: 1winner_game_with_events
    Given 1winner game with events starts
    When P1 draws quest with 4 stages
    Then P1 decides to sponsor
    And P1 is the sponsor
    And P1 builds stage 1 with cards "F5"
    And P1 builds stage 2 with cards "F5,D5"
    And P1 builds stage 3 with cards "F15,D5"
    And P1 builds stage 4 with cards "F15,H10"
    And P1 built 4 valid stages with cards "F5,F5,D5,F15,D5,F15,H10"
    And P2 decides to participate in stage 1 and trims "F5"
    And P3 decides to participate in stage 1 and trims "F5"
    And P4 decides to participate in stage 1 and trims "F5"
    And Check participants are "P2,P3,P4"
    And P2 builds an attack with "D5"
    And P3 builds an attack with "D5"
    And P4 builds an attack with "D5"
    And P2 resolves attack for stage 1
    And P3 resolves attack for stage 1
    And P4 resolves attack for stage 1
    And Check participants are "P2,P3,P4"
    And P2 decides to participate in stage 2 and trims " "
    And P3 decides to participate in stage 2 and trims " "
    And P4 decides to participate in stage 2 and trims " "
    And P2 builds an attack with "H10"
    And P3 builds an attack with "S10"
    And P4 builds an attack with "H10"
    And P2 resolves attack for stage 2
    And P3 resolves attack for stage 2
    And P4 resolves attack for stage 2
    And Check participants are "P2,P3,P4"
    And P2 decides to participate in stage 3 and trims " "
    And P3 decides to participate in stage 3 and trims " "
    And P4 decides to participate in stage 3 and trims " "
    And P2 builds an attack with "B15"
    And P3 builds an attack with "B15"
    And P4 builds an attack with "B15"
    And P2 resolves attack for stage 3
    And P3 resolves attack for stage 3
    And P4 resolves attack for stage 3
    And Check participants are "P2,P3,P4"
    And P2 decides to participate in stage 4 and trims " "
    And P3 decides to participate in stage 4 and trims " "
    And P4 decides to participate in stage 4 and trims " "
    And P2 builds an attack with "E30"
    And P3 builds an attack with "L20,S10"
    And P4 builds an attack with "L20,H10"
    And P2 resolves attack for stage 4
    And P3 resolves attack for stage 4
    And P4 resolves attack for stage 4
    And P1 should have 0 shields
    And P2 should have 4 shields
    And P3 should have 4 shields
    And P4 should have 4 shields
    And P1 handles the end of quest
    And P2 draws event card
    And P2 should have 2 shields
    And P3 draws prosperity
    And P1 should have hand size 12
    And P2 should have hand size 12
    And P3 should have hand size 12
    And P4 should have hand size 12
    And P4 draws event card
    And P4 should have hand size 12
    And P1 draws quest with 3 stages
    And P1 decides to sponsor
    And P1 is the sponsor
    And P1 builds stage 1 with cards "F10"
    And P1 builds stage 2 with cards "F10,D5"
    And P1 builds stage 3 with cards "F15,D5"
    And P1 built 3 valid stages with cards "F10,F10,D5,F15,D5"
    And P2 decides to participate in stage 1 and trims "F5"
    And P3 decides to participate in stage 1 and trims "F5"
    And P4 decides to participate in stage 1 and trims "F5"
    And Check participants are "P2,P3,P4"
    And P2 builds an attack with "S10"
    And P3 builds an attack with "S10"
    And P4 builds an attack with "D5"
    And P2 resolves attack for stage 1
    And P3 resolves attack for stage 1
    And P4 resolves attack for stage 1
    And Check participants are "P2,P3"
    And P2 decides to participate in stage 2 and trims " "
    And P3 decides to participate in stage 2 and trims " "
    And P2 builds an attack with "S10,D5"
    And P3 builds an attack with "S10,D5"
    And P2 resolves attack for stage 2
    And P3 resolves attack for stage 2
    And Check participants are "P2,P3"
    And P2 decides to participate in stage 2 and trims " "
    And P3 decides to participate in stage 2 and trims " "
    And P2 builds an attack with "L20"
    And P3 builds an attack with "L20"
    And P2 resolves attack for stage 3
    And P3 resolves attack for stage 3
    And Check participants are "P2,P3"
    And P1 should have 0 shields
    And P2 should have 5 shields
    And P3 should have 7 shields
    And P4 should have 4 shields
    And P1 handles the end of quest
    And P3 is a winner

  Scenario: 0_winner_quest
    Given 0 winner quest
    When P1 draws quest with 2 stages
    Then P1 decides to sponsor
    And P1 is the sponsor
    And P1 builds stage 1 with cards "F15,L20"
    And P1 builds stage 2 with cards "F40"
    And P1 built 2 valid stages with cards "F15,L20,F40"
    And P2 decides to participate in stage 1 and trims "F5"
    And P3 decides to participate in stage 1 and trims "F5"
    And P4 decides to participate in stage 1 and trims "F5"
    And Check participants are "P2,P3,P4"
    And P2 builds an attack with "D5"
    And P3 builds an attack with "D5"
    And P4 builds an attack with "D5"
    And P2 resolves attack for stage 1
    And P3 resolves attack for stage 1
    And P4 resolves attack for stage 1
    And Check participants are ""
    And P1 should have 0 shields
    And P2 should have 0 shields
    And P3 should have 0 shields
    And P4 should have 0 shields
    And P1 handles the end of quest
    And P1 should have hand size 12
    And P2 should have hand size 11
    And P3 should have hand size 11
    And P4 should have hand size 11