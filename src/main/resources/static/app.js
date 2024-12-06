const stompClient = new StompJs.Client({
  brokerURL: "ws://localhost:8080/gs-guide-websocket",
});

stompClient.onConnect = (frame) => {
  setConnected(true);
  console.log("Connected: " + frame);
  stompClient.subscribe("/topic/gameInput", (greeting) => {
    showGreeting(JSON.parse(greeting.body).content);
  });
  stompClient.subscribe("/topic/playerUpdates", (message) => {
    const players = JSON.parse(message.body);
    updatePlayersInfo(players);
  });
};

stompClient.onWebSocketError = (error) => {
  console.error("Error with websocket", error);
};

stompClient.onStompError = (frame) => {
  console.error("Broker reported error: " + frame.headers["message"]);
  console.error("Additional details: " + frame.body);
};

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  if (connected) {
    $("#game").show();
  } else {
    $("#game").hide();
  }
  $("#gameInput").html("");
}

function connect() {
  stompClient.activate();
}

function disconnect() {
  stompClient.deactivate();
  setConnected(false);
  console.log("Disconnected");
}

function sendName() {
  const nameInput = $("#name");
  const name = nameInput.val();
  if (name) {
    stompClient.publish({
      destination: "/app/hello",
      body: JSON.stringify({ name: name }),
    });
    nameInput.val('');
  }
}

function showGreeting(message) {
  const gameInput = $("#gameInput");
  gameInput.append("<tr><td>" + message + "</td></tr>");
  gameInput.scrollTop(gameInput[0].scrollHeight);
}

function updatePlayersInfo(players) {
  const playersTable = $("#players");
  playersTable.html(""); // Clear previous entries
  players.forEach(player => {
    playersTable.append("<tr><td>" + player.name + "</td><td>" + player.handSize + "</td><td>" + player.shields + "</td></tr>");
  });
}

// Function to handle Default button click
function sendDefault() {
  console.log("Sending Default Game...");
  stompClient.publish({
    destination: "/app/hello",
    body: JSON.stringify({ name: "default" }),
  });
}

// Function to handle Scenario 1 button click
function sendScenario1() {
  console.log("Sending Scenario 1...");
  stompClient.publish({
    destination: "/app/hello",
    body: JSON.stringify({ name: "scenerio1" }),
  });
}

// Function to handle Scenario 2 button click
function sendScenario2() {
  console.log("Sending Scenario 2...");
  stompClient.publish({
    destination: "/app/hello",
    body: JSON.stringify({ name: "scenerio2" }),
  });
}

// Function to handle Scenario 3 button click
function sendScenario3() {
  console.log("Sending Scenario 3...");
  stompClient.publish({
    destination: "/app/hello",
    body: JSON.stringify({ name: "scenerio3" }),
  });
}

// Function to handle Scenario 4 button click
function sendScenario4() {
  console.log("Sending Scenario 4...");
  stompClient.publish({
    destination: "/app/hello",
    body: JSON.stringify({ name: "scenerio4" }),
  });
}

function fetchPlayerInfo() {
  $.ajax({
    url: "/players",
    method: "GET",
    success: function (data) {
      updatePlayersInfo(data);
    },
    error: function (error) {
      console.error("Error fetching player info", error);
    }
  });
}

$(function () {
  $("form").on("submit", (e) => e.preventDefault());
  $("#connect").click(() => connect());
  $("#disconnect").click(() => disconnect());
  $("#send").click(() => sendName());
  $("#defaultGame").click(() => sendDefault());
  $("#scenario1").click(() => sendScenario1());
  $("#scenario2").click(() => sendScenario2());
  $("#scenario3").click(() => sendScenario3());
  $("#scenario4").click(() => sendScenario4());
});