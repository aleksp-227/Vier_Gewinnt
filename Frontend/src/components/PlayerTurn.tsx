import React, { useContext } from "react";
import { StoreContext } from "../store/store";

function Turn() {
  const { game } = useContext(StoreContext);
  const hasWon = game.message.includes("has won");
  
  let cp: string;

  if (game.currentPlayer.name === "xyz") {
    cp = "Blau";
  } else cp = "Rot";

  return (
    <div>
      <h2 className="turn-display">
        Spieler {cp} {hasWon ? "hat gewonnen" : "ist dran"}!
      </h2>
    </div>
  );
}

export default Turn;
