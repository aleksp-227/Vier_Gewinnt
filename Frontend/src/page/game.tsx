import React from "react";
import Header from "../components/Header";
import Field from "../components/Field";
import Turn from "../components/PlayerTurn";
import NewGame from "../components/NewGame";
import StoreProvider from "../store/store";

function Game() {
  return (
    <StoreProvider>
      <div className="head-container">
        <Header />
      </div>
      <div className="main-container">
        <Field />
      </div>
      <div className="bottom-container">
        <div className="turn-container">
          <Turn />      
        </div>
        <NewGame />
      </div>
    </StoreProvider>
  );
}

export default Game;
